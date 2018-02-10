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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SyncPlayFragment extends Fragment {


    public SyncPlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sync_play, container, false);

        Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/product_san_regular.ttf");
        Typeface bold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/product_sans_bold.ttf");
        final FontChanger fontChanger = new FontChanger(regular);
        fontChanger.replaceFonts((ViewGroup)view);

        TextView hostTV,joinTV;
        hostTV = view.findViewById(R.id.hostTV);
        joinTV = view.findViewById(R.id.joinTV);

        hostTV.setTypeface(bold);
        joinTV.setTypeface(bold);

        hostTV.getPaint().setShader(new LinearGradient(0,0,0,hostTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));

        joinTV.getPaint().setShader(new LinearGradient(0,0,0,joinTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));



        ImageView hostIV,joinIV;
        hostIV = view.findViewById(R.id.hostIV);
        joinIV = view.findViewById(R.id.joinIV);



        Bitmap hostBitmap = ((BitmapDrawable)hostIV.getDrawable()).getBitmap();
        Bitmap newHBitmap = addGradient(hostBitmap);
        hostIV.setImageDrawable(new BitmapDrawable(getResources(),newHBitmap));


        Bitmap joinBitmap = ((BitmapDrawable)joinIV.getDrawable()).getBitmap();
        Bitmap newJBitmap = addGradient(joinBitmap);
        joinIV.setImageDrawable(new BitmapDrawable(getResources(),newJBitmap));

        return view;
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
