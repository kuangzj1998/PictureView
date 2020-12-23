package com.example.pictureview;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView)findViewById(R.id.textview);
        Button button = (Button)findViewById(R.id.button);


        if (PermissionUtil.checkPermission(this, PermissionUtil.PERMISSIONS_STORAGE).length > 0) {

            PermissionUtil.checkAndRequestPermissions(this, PermissionUtil.PERMISSIONS_STORAGE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*int i = PermissionUtil.checkPermission(MainActivity.this, PermissionUtil.PERMISSIONS_STORAGE).length;
                textView.setText(String.valueOf(i));*/
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

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        textView.setText(String.valueOf(23));
                        // 获取图片的路径
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE)) / 1024;
                        String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                        //用于展示相册初始化界面
                        //if(path.contains("/storage/emulated/0/messageBoard/photoImgs")){
                        //    mediaBeen.add(new MediaBean(path,size,displayName));
                        //}
                        Log.d("out", "path: "+path);
                        Log.d("out", "size: "+size+" KB");
                        Log.d("out", "Name: "+displayName);
                    }
                    cursor.close();
                }
            }
        });

    }

}
