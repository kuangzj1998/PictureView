package com.example.pictureview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {
    private List<String>folderList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView folderName;
        public ViewHolder(View view){
            super(view);
            folderName = (TextView)view.findViewById(R.id.folder_name);
        }
    }
    public FolderAdapter(List<String>List){
        folderList = List;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("position",String.valueOf(position));
        if(folderList.get(position)!=null)         holder.folderName.setText(folderList.get(position));
        else holder.folderName.setText(R.string.placeholder);
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    //TODO 给item加上点击事件和点击动画(按住时背景变灰)
}
