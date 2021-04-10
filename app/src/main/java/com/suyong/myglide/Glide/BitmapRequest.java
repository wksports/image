package com.suyong.myglide.Glide;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

public class BitmapRequest {
    //上下文
    private Context mContext;
    //图片的路径
    private String mUrl;
    //显示图片的控件
    private SoftReference<ImageView> mImageView;
    //占位图片的资源id
    private int mLoadingResId;
    //回调接口
    private RequestCallBack mCallBack;
    //请求标识
    private String MD5_Tag;
    public int loading = 0;//加载占位图资源id
    public int error = 0;//加载失败展位图资源id

    public BitmapRequest(Context mContext) {
        this.mContext = mContext;
    }

    public BitmapRequest load(String url){
        this.mUrl = url;
        this.MD5_Tag = MD5Utils.encode(this.mUrl);
        return this;
    }

    public BitmapRequest setLoadingResId(int mLoadingResId) {
        this.mLoadingResId = mLoadingResId;
        return this;
    }

    public BitmapRequest setCallBack(RequestCallBack mCallBack) {
        this.mCallBack = mCallBack;
        return this;
    }

    public void into(ImageView imageView){
        imageView.setTag(this.MD5_Tag);
        this.mImageView = new SoftReference<>(imageView);
        RequestManager.getInstance().addBitmapRequest(this);
    }

    public String getUrl() {
        return mUrl;
    }

    public ImageView getImageView() {
        return mImageView.get();
    }

    public int getLoadingResId() {
        return mLoadingResId;
    }

    public String getMD5_Tag() {
        return MD5_Tag;
    }

    public BitmapRequest loading(int res) {
        loading = res;
        return this;
    }
    public BitmapRequest error(int res) {
        error = res;
        return this;
    }

}
