package com.example.pictureview;

public class Picture {
    private String path;
    private String fileName;
    private int fileSize;

    public Picture(String path,String fileName,int fileSize){
        this.path = path;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getPath(){return path;}
    public String getFileName(){return fileName;}
    public int getFileSize(){return fileSize;}
}
