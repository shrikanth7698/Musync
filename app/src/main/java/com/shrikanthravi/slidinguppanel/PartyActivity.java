package com.shrikanthravi.slidinguppanel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PartyActivity extends AppCompatActivity {

    ImageView sendBTN;
    EditText messageET;
    RecyclerView messageRV;
    List<Message> messageList;
    CustomListAdapter customListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_party);

        getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
        Typeface regular = Typeface.createFromAsset(getAssets(), "fonts/product_san_regular.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(),"fonts/product_sans_bold.ttf");
        FontChanger fontChanger = new FontChanger(regular);
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));


        final ImageView cld1 = (ImageView)findViewById(R.id.cld1);
        final ImageView cld2 = (ImageView)findViewById(R.id.cld2);
        final ImageView cld3 = (ImageView)findViewById(R.id.cld3);
        final ImageView cld4 = findViewById(R.id.cld4);
        sendBTN = findViewById(R.id.sendBTN);
        messageET = findViewById(R.id.messageET);
        messageRV = findViewById(R.id.messageRV);
        messageList = new ArrayList<>();
        customListAdapter = new CustomListAdapter(messageList,getApplicationContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);

        messageRV.setLayoutManager(linearLayoutManager);
        messageRV.setAdapter(customListAdapter);

        Bitmap oldSB = ((BitmapDrawable)sendBTN.getDrawable()).getBitmap();

        sendBTN.setImageBitmap(addGradient(oldSB));

        Bitmap oldCld1= ((BitmapDrawable)cld1.getDrawable()).getBitmap();
        Bitmap newCld1 = addGradient(oldCld1);
        cld1.setImageDrawable(new BitmapDrawable(getResources(),newCld1));


        Bitmap oldCld2= ((BitmapDrawable)cld2.getDrawable()).getBitmap();
        Bitmap newCld2 = addGradient(oldCld2);
        cld2.setImageDrawable(new BitmapDrawable(getResources(),newCld2));


        Bitmap oldCld3= ((BitmapDrawable)cld3.getDrawable()).getBitmap();
        Bitmap newCld3 = addGradient(oldCld3);
        cld3.setImageDrawable(new BitmapDrawable(getResources(),newCld3));


        Bitmap oldCld4= ((BitmapDrawable)cld4.getDrawable()).getBitmap();
        Bitmap newCld4 = addGradient(oldCld4);
        cld4.setImageDrawable(new BitmapDrawable(getResources(),newCld4));




        final Animation animSlide1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide);
        animSlide1.setRepeatCount(-1);
        animSlide1.setRepeatMode(Animation.RESTART);
        final Animation animSlide2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide2);
        animSlide2.setRepeatCount(-1);
        animSlide2.setRepeatMode(Animation.RESTART);
        final Animation animSlide3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide3);
        animSlide3.setRepeatCount(-1);
        animSlide3.setRepeatMode(Animation.RESTART);
        final Animation animSlide4 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide4);
        animSlide4.setRepeatCount(-1);
        animSlide4.setRepeatMode(Animation.RESTART);

        cld1.startAnimation(animSlide1);
        cld2.startAnimation(animSlide2);
        cld3.startAnimation(animSlide3);
        cld4.startAnimation(animSlide4);

        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageET.getText().toString().trim().length()!=0){
                    send(messageET.getText().toString());
                }
            }
        });


    }


    public void send(String text){
        messageList.add(new Message("right",getTime(),text,null,null,null));
        customListAdapter.notifyDataSetChanged();
        messageList.add(new Message("left",getTime(),"Wow",null,null,null));
        customListAdapter.notifyDataSetChanged();
    }

    public String getTime(){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String time = mdformat.format(calendar.getTime());
        return time;
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
