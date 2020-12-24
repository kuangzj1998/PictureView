package com.example.pictureview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;
/*
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private List<Picture> myPictureList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView picture;
        TextView picture_name;
        public ViewHolder(View view){
            super(view);
            picture = (ImageView)view.findViewById(R.id.picture);
            picture_name = (TextView)view.findViewById(R.id.picture_name);
        }
    }
    public PictureAdapter(List<Picture>pictureList){
        myPictureList = pictureList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picture picture = myPictureList.get(position);
        File file = new File(picture.getPath());
        if(file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(picture.getPath());
            holder.picture.setImageBitmap(bitmap);
        }
        holder.picture_name.setText(picture.getFileName());
    }

    @Override
    public int getItemCount() {
        return myPictureList.size();
    }
}*/
