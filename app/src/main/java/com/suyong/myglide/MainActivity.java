package com.suyong.myglide;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.suyong.myglide.Glide.MyGlide;

public class MainActivity extends AppCompatActivity {


    private ImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        image = findViewById(R.id.image);

    }
//下载图片，设置占位图
    public void loadBitmap(View view) {
        MyGlide.with(this).load("https://bkimg.cdn.bcebos.com/pic/95eef01f3a292df58921b9d3b2315c6035a87311?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2U4MA==,g_7,xp_5,yp_5/format,f_auto").setLoadingResId(R.drawable.ic_launcher_background).loading(R.drawable.icon_placeholder).error(R.drawable.icon_failure).into(image);


    }


}
