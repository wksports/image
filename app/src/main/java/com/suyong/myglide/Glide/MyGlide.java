package com.suyong.myglide.Glide;

import android.content.Context;
import android.graphics.Bitmap;

public class MyGlide {
    public static BitmapRequest with(Context context){
        return new BitmapRequest(context);
    }
}
