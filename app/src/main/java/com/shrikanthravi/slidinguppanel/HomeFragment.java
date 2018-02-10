package com.shrikanthravi.slidinguppanel;


import android.app.Dialog;
import android.content.Context;
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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    public static int stop=0;
    //LinearLayout recentLL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/product_san_regular.ttf");
        final Typeface bold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/product_sans_bold.ttf");
        final FontChanger fontChanger = new FontChanger(regular);
        fontChanger.replaceFonts((ViewGroup)view);
        TextView fmTV,mpmTV;
        fmTV = view.findViewById(R.id.fmTV);
        mpmTV = view.findViewById(R.id.mpmTV);
        //recentLL = view.findViewById(R.id.recentlyPlayedLL);
        fmTV.setTypeface(bold);
        mpmTV.setTypeface(bold);
        TextView recentlyPlayedTV;
        //recentlyPlayedTV = view.findViewById(R.id.recentlyPlayedTV);
        //recentlyPlayedTV.setTypeface(bold);
        //recentlyPlayedTV.getPaint().setShader(new LinearGradient(0,0,0,recentlyPlayedTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));
        TextView playlistTV,albumsTV,songsTV,artistsTV;
        playlistTV = view.findViewById(R.id.playlistButton);
        albumsTV = view.findViewById(R.id.albumsButton);
        songsTV = view.findViewById(R.id.songsButton);
        artistsTV = view.findViewById(R.id.artistsButton);

        playlistTV.setTypeface(bold);
        albumsTV.setTypeface(bold);
        songsTV.setTypeface(bold);
        artistsTV.setTypeface(bold);

        playlistTV.getPaint().setShader(new LinearGradient(0,0,0,playlistTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));
        albumsTV.getPaint().setShader(new LinearGradient(0,0,0,albumsTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));
        songsTV.getPaint().setShader(new LinearGradient(0,0,0,songsTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));
        artistsTV.getPaint().setShader(new LinearGradient(0,0,0,artistsTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));

        playlistTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(4,true);
            }
        });

        albumsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.viewPager.setCurrentItem(2,true);
            }
        });

        songsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.viewPager.setCurrentItem(1,true);
            }
        });

        final Handler handler = new Handler();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(stop==1){
                    stop=0;


                   /* for(int i=1;i<5;i++) {
                        View child1, space;
                        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        space = inflater.inflate(R.layout.space, null);
                        child1 = inflater.inflate(R.layout.recent_song_row_item, null);
                        TextView songNameTV = child1.findViewById(R.id.recentSongNameTV);
                        TextView artistNameTV = child1.findViewById(R.id.recentArtistNameTV);
                        ImageView songIV = child1.findViewById(R.id.recentSongIV);
                        fontChanger.replaceFonts((ViewGroup)child1);
                        songIV.setImageBitmap(SongsFragment.songList.get(i).getSongImage());
                        songNameTV.setText(SongsFragment.songList.get(i).getTitle());
                        artistNameTV.setText(SongsFragment.songList.get(i).getArtist());
                        recentLL.addView(space);
                        recentLL.addView(child1);
                    }*/

                }
                handler.postDelayed(this,1000);
            }
        });



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
        final Bitmap newHBitmap = addGradient(hostBitmap);
        hostIV.setImageDrawable(new BitmapDrawable(getResources(),newHBitmap));


        Bitmap joinBitmap = ((BitmapDrawable)joinIV.getDrawable()).getBitmap();
        Bitmap newJBitmap = addGradient(joinBitmap);
        joinIV.setImageDrawable(new BitmapDrawable(getResources(),newJBitmap));

        hostIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/product_san_regular.ttf");
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.host_create_dialog);
                dialog.setCancelable(true);
                ImageView hostDIV = dialog.findViewById(R.id.hostIV);
                Bitmap oldB = ((BitmapDrawable)hostDIV.getDrawable()).getBitmap();
                Bitmap newB = addGradient(oldB);
                hostDIV.setImageDrawable(new BitmapDrawable(getResources(),newB));
                TextView hostET = dialog.findViewById(R.id.hostET);
                TextView ch1 = dialog.findViewById(R.id.changeHTV);
                TextView ch2 = dialog.findViewById(R.id.chooseHITV);
                Button hostBTN = dialog.findViewById(R.id.hostBTN);
                hostET.setTypeface(regular);
                ch1.setTypeface(regular);
                ch2.setTypeface(regular);
                hostBTN.setTypeface(regular);
                hostBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(),PartyActivity.class));
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibletoUser) {
        if (isVisibletoUser) {
            MainActivity.currentPage=0;
        }
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
