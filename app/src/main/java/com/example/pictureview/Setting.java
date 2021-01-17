package com.example.pictureview;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Setting implements Serializable {
    //private String AppName;
    private int count;
    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return count;
    }
    @Override
    @NonNull
    public String toString(){
        //return String.valueOf(count);
        return "Setting{" +
                "count='" + count + '\'' +
                //", pwd='" + pwd + '\'' +
                '}';
    }

}
