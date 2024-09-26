package com.laba.solvd.pool;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileConnectionPool {
    private final BlockingQueue<FileConnection> connectionPool;
    private static FileConnectionPool instance;

    private FileConnectionPool(int poolSize, String filePath) throws IOException{
        connectionPool= new LinkedBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connectionPool.offer(new FileConnection(filePath));
        }
    }

    public static FileConnectionPool getInstance(int poolSize, String filePath)throws IOException {
        if (instance==null){
            synchronized (FileConnectionPool.class){
                if (instance==null){
                    instance=new FileConnectionPool(poolSize, filePath);
                }
            }
        }
        return instance;
    }

    public FileConnection getConnection() throws InterruptedException{
        return connectionPool.take();
    }

    public void releasedConnection(FileConnection connection){
        connectionPool.offer(connection);
    }
}
