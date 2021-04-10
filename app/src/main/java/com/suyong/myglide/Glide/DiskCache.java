package com.suyong.myglide.Glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//磁盘缓存
public class DiskCache {
    private static volatile DiskCache instance;
    private String cachePath = "Cache";
    private static final byte[] lock = new byte[0];
    private File cacheFile;

    public static DiskCache getInstance(Context context) {
        if (instance == null) {
            synchronized (lock) {
                if (null == instance) {
                    instance = new DiskCache(context);
                }
            }
        }
        return instance;
    }

    private DiskCache(Context context) {
        cacheFile = getImageCacheFile(context, cachePath);
        if (!cacheFile.exists()) cacheFile.mkdirs();
    }


    private File getImageCacheFile(Context context, String cachePath) {
        File file = new File(context.getFilesDir() + "/" + cachePath);
        return file;
    }

    public void put(String key, Bitmap value){
        if(value != null){
            FileOutputStream fos = null;
            try {
                File temp = new File(cacheFile.getAbsolutePath()+ "/"+key+".png");
                if(temp.exists()) temp.delete();
                temp.createNewFile();
                fos = new FileOutputStream(temp);
                value.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public Bitmap get(String key){
        return BitmapFactory.decodeFile(cacheFile.getAbsolutePath()+ "/"+key+".png");
    }
}
