package com.suyong.myglide.Glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.suyong.myglide.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 请求图片
 */
public class BitmapDispatcher extends Thread {

    //请求队列
    private LinkedBlockingQueue<BitmapRequest> requestQueue;

    private DoubleCache doubleCache = new DoubleCache(MyApplication.getInstance());

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        super.run();
        while(!this.isInterrupted()) {
            try {
                BitmapRequest request = requestQueue.take();
                showLoadingImage(request);
                Bitmap bitmap = doubleCache.get(request.getMD5_Tag());
                if(bitmap == null){
                    bitmap =  loadBitmap(request);
                    doubleCache.put(request.getMD5_Tag(),bitmap);
                }
                showImageResult(request, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageResult(BitmapRequest request, final Bitmap bitmap) {
        if (bitmap != null && request.getImageView() != null && request.getMD5_Tag().equals(request.getImageView().getTag())) {
            final ImageView imageView = request.getImageView();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }

    private Bitmap loadBitmap(BitmapRequest request) {
        return download(request.getUrl());
    }

    //下载图片
    private Bitmap download(String uri) {
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;


        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            is = con.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//压缩图片
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);   //留下10%，压缩90%
        bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());

        return bitmap;
    }

    private void showLoadingImage(final BitmapRequest request) {
        if (request.getLoadingResId() > 0 && request.getImageView() != null) {
            final ImageView imageView = request.getImageView();
            final int resId = request.getLoadingResId();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resId);
                }
            });
        }

    }


}
