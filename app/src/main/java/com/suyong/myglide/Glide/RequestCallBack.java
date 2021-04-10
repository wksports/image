package com.suyong.myglide.Glide;

import android.graphics.Bitmap;

public interface RequestCallBack {
    //请求成功
    void onSucess(Bitmap bitmap);
    //请求失败
    void onFailed();
}
