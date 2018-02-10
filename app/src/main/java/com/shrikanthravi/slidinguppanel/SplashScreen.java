package com.shrikanthravi.slidinguppanel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

        getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
        Typeface regular = Typeface.createFromAsset(getAssets(), "fonts/product_san_regular.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(),"fonts/product_sans_bold.ttf");
        FontChanger fontChanger = new FontChanger(regular);
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        ImageView musyncIV = findViewById(R.id.musyncIcon);

        Bitmap musyncIcon = ((BitmapDrawable)musyncIV.getDrawable()).getBitmap();
        Bitmap newBitmap = addGradient(musyncIcon);
        musyncIV.setImageDrawable(new BitmapDrawable(getResources(),newBitmap));
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent main =new Intent(SplashScreen.this,MainActivity.class);
                startActivity(main);
                SplashScreen.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, 1500);

        TextView titleTV = findViewById(R.id.titleTV);
        titleTV.getPaint().setShader(new LinearGradient(0,0,0,titleTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));
        titleTV.setTypeface(bold);

    }

    public Bitmap addGradient(Bitmap originalBitmap){

        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Bitmap updatedBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(updatedBitmap);
        canvas.drawBitmap(originalBitmap,0,0,null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,0,0,height,getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0,0,width,height,paint);

        return updatedBitmap;

    }
}
