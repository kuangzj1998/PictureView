package com.example.pictureview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private List<Picture> pictureList = new ArrayList<>();
    private List<String> folderList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionCheck();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_main,menu);
        return true;//true显示popup菜单，false隐藏popup菜单
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionbar_sync:
                Toast.makeText(this,"重新搜索本地图片",Toast.LENGTH_SHORT).show();
                LoadLocalPicture();
                break;
            default:
                break;
        }
        return true;//true代表截断事件往下传播
    }

    public static void exitApp(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
        for (ActivityManager.AppTask appTask : appTaskList) {
            appTask.finishAndRemoveTask();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    void LoadLocalPicture(){
        Uri ImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projImage = {MediaStore.Images.Media._ID
                , MediaStore.Images.Media.DATA
                , MediaStore.Images.Media.SIZE
                , MediaStore.Images.Media.DISPLAY_NAME};
        final Cursor cursor = getContentResolver().query(ImageUri,
                projImage,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED + " desc");

        if(folderList.size()>0) folderList.clear();
        HashSet<String>set = new HashSet<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 获取图片的路径
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE)) / 1024;
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                //用于展示相册初始化界面
                //if(path.contains("/storage/emulated/0/messageBoard/photoImgs")){
                //    mediaBeen.add(new MediaBean(path,size,displayName));
                //}
                String parent = new File(path).getParent();
                set.add(parent);
                Log.d("out", "path: "+path);
                Log.d("out", "size: "+size+" KB");
                Log.d("out", "Name: "+displayName);
                Picture hold = new Picture(path,displayName,size);
                pictureList.add(hold);

            }
            cursor.close();
        }
        for(String s : set){
            if(s!=null){
                folderList.add(s);
                /*int n=s.lastIndexOf(File.separator);
                if(n==-1) folderList.add(s);
                else folderList.add(s.substring(n+1));*/
            }

        }
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FolderAdapter adapter = new FolderAdapter(folderList);
        recyclerView.setAdapter(adapter);
    }

    void PermissionCheck(){
        Permissions permissions = Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE);
        SoulPermission.getInstance().checkAndRequestPermissions(permissions, new CheckRequestPermissionsListener() {
            @Override
            public void onAllPermissionOk(Permission[] allPermissions) {
                LoadLocalPicture();
            }

            @Override
            public void onPermissionDenied(Permission[] refusedPermissions) {
                if(refusedPermissions[0].shouldRationale()){
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("提示")
                            .setMessage("如果你拒绝了权限，你将无法运行本应用")
                            .setPositiveButton("重新授权", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    PermissionCheck();
                                }
                            })
                            .setNegativeButton("退出应用", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    exitApp(MainActivity.this);
                                }
                            })
                            .create();
                    alertDialog.show();
                }else{
                    //此时请求权限会直接报未授予，需要用户手动去权限设置页，所以弹框引导用户跳转去设置页
                    String permissionDesc = refusedPermissions[0].getPermissionNameDesc();
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("提示")
                            .setMessage(permissionDesc + "异常，请前往设置－>权限管理，打开" + permissionDesc + "。")
                            .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //去设置页
                                    SoulPermission.getInstance().goPermissionSettings();
                                    exitApp(MainActivity.this);
                                }
                            }).create().show();
                }

            }
        });
    }
}
