package com.suyong.myglide.Glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import java.io.File;

//内存缓存
public class MemoryCache {

    private LruCache<String, Bitmap> lruCache;

    private static volatile  MemoryCache instance;

    private static final byte[]lock = new byte[0];

    public static MemoryCache getInstance(){
        if(instance == null){
            synchronized (lock){
                if(null == instance){
                    instance = new MemoryCache();
                }
            }
        }
        return instance;
    }

    private MemoryCache(){
        int maxMemorySize = (int) (Runtime.getRuntime().maxMemory()/16);
        if(maxMemorySize < 0){
            maxMemorySize = 10*1024*1024;
        }
        lruCache = new LruCache<String,Bitmap>(maxMemorySize){
          @Override
          protected int sizeOf(String key,Bitmap value){
              return super.sizeOf(key,value);
          }
        };
    }

    public void put(String key,Bitmap value){
        if(value!=null){
            lruCache.put(key,value);
        }
    }

    public Bitmap get(String key){
        return lruCache.get(key);
    }
}
