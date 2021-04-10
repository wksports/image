package com.suyong.myglide.Glide;

import android.content.Context;
import android.graphics.Bitmap;

public class DoubleCache {
    //内存缓存
    private MemoryCache lruCache;
    //磁盘缓存
    private DiskCache diskCache;

    public DoubleCache(Context context){
        diskCache = DiskCache.getInstance(context);
        lruCache = MemoryCache.getInstance();
    }

    public void put(String key, Bitmap bitmap){
        lruCache.put(key,bitmap);
        diskCache.put(key,bitmap);
    }

    public Bitmap get(String key){
        Bitmap bitmap = lruCache.get(key);
        if(bitmap != null) return bitmap;
        return diskCache.get(key);
    }
}
