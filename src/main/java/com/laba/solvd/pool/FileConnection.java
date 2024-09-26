package com.laba.solvd.pool;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileConnection {
    private final String fileName;

    public FileConnection(String fileName){
        this.fileName=fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void proccessFile() throws IOException{
        System.out.println(Thread.currentThread().getName()+" is processing file: "+fileName);
        String content= FileUtils.readFileToString(new File(fileName),"UTF-8");
        System.out.println("File content: "+content);
    }
}
