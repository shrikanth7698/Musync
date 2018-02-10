package com.shrikanthravi.slidinguppanel;

import android.graphics.LinearGradient;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {



    ImageView slidingPanelImageView;
    CardView slidingPanelCardView;
    ImageView playPauseIV,newPlayPauseIV,previousIV,nextIV,heartIV;
    public TextView songName,artistName,newSongName,newArtistName,leftDuration,rightDuration;
    CardView slcviv;
    LinearLayout slideLL;
    int previouspos1=0;
    LinearLayout shuffleLL,repeatLL;
    ImageView shuffleIV,repeatIV;
    TextView shuffleTV,repeatTV;

    int widthHeight ;
    int sWidth;

    public SeekBar mSeekBar;
    RecyclerView albumSongsRV;
    AlbumSongAdapter albumSongAdapter;
    List<Song> albumSongList;
    Album album;
    SlidingUpPanelLayout slidingUpPanelLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_album);

        getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
        Typeface regular = Typeface.createFromAsset(getAssets(), "fonts/product_san_regular.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(),"fonts/product_sans_bold.ttf");
        FontChanger fontChanger = new FontChanger(regular);
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));


        slidingPanelImageView = findViewById(R.id.slidingUpPanelImageView);
        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
        TextView titleTV = findViewById(R.id.titleTV);
        TextView albumTV = findViewById(R.id.albumNameTV);
        TextView artistTV = findViewById(R.id.artistNameTV);
        ImageView albumIV = findViewById(R.id.albumIV);

        slidingPanelCardView = findViewById(R.id.slidingUpPanelCardView);
        slidingUpPanelLayout = findViewById(R.id.sliding_layout);

        slcviv = findViewById(R.id.slcviv);
        playPauseIV = findViewById(R.id.playPauseIV);
        newPlayPauseIV = findViewById(R.id.newPlayPauseIV);
        previousIV = findViewById(R.id.previousIV);
        nextIV = findViewById(R.id.nextIV);
        heartIV = findViewById(R.id.heartIV);
        songName = (TextView)findViewById(R.id.songNameTV1);
        artistName = (TextView)findViewById(R.id.artistNameTV1);
        newSongName = (TextView)findViewById(R.id.newSongName);
        newArtistName = (TextView) findViewById(R.id.newSongArtist);
        leftDuration = (TextView) findViewById(R.id.leftDuration);
        rightDuration = (TextView) findViewById(R.id.rightDuration);
        slideLL = findViewById(R.id.slideLL);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        shuffleLL = findViewById(R.id.shuffleLL);
        repeatLL = findViewById(R.id.repeatLL);
        shuffleIV = findViewById(R.id.shuffleIV);
        repeatIV = findViewById(R.id.repeatIV);
        shuffleTV = findViewById(R.id.shuffleTV);
        repeatTV = findViewById(R.id.repeatTV);


        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        sWidth = point.x;
        System.out.println("Screen Width "+point.x);
        ViewGroup.LayoutParams params = slidingPanelImageView.getLayoutParams();
        widthHeight = params.width;

        albumSongsRV = findViewById(R.id.albumSongs);
        albumSongsRV.setNestedScrollingEnabled(false);
        albumSongList = new ArrayList<>();
        albumSongAdapter = new AlbumSongAdapter(albumSongList,getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        albumSongsRV.setLayoutManager(linearLayoutManager);
        albumSongsRV.setAdapter(albumSongAdapter);

        slidingUpPanelLayout.setEnabled(true);
        slidingUpPanelLayout.setGravity(Gravity.BOTTOM);
        titleTV.getPaint().setShader(new LinearGradient(0,0,0,titleTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));
        titleTV.setTypeface(bold);

        try {


            Album album = AlbumsFragment.albumList.get(Integer.valueOf(getIntent().getStringExtra("position")));
            albumTV.setText(album.getAlbumName());
            artistTV.setText(album.getSongList().get(0).getArtist());
            albumIV.setImageBitmap(album.getSongList().get(0).getSongImage());
            albumSongList.addAll(album.getSongList());
            albumSongAdapter.notifyDataSetChanged();


        }
        catch (Exception e){

        }

        NestedScrollView nestedScrollView = findViewById(R.id.nestedSV);
        findViewById(R.id.albumSongs).setFocusable(false);
        findViewById(R.id.rl).requestFocus();

        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {

                ViewGroup.LayoutParams params = slidingPanelImageView.getLayoutParams();


                int temp = ((int) (widthHeight + (widthHeight * v * sWidth/100)));

                if(temp<sWidth) {
                    params.width = params.height = ((int) (widthHeight + (widthHeight * v * sWidth/100)));
                    System.out.println("radius "+slcviv.getRadius()+" width "+params.width);
                    slidingPanelImageView.setLayoutParams(params);
                }

                slideLL.setAlpha(1-v);
                playPauseIV.setAlpha(1-v);
                if(v<1.0){
                    slideLL.setVisibility(View.VISIBLE);
                    playPauseIV.setVisibility(View.VISIBLE);
                }
                else{
                    if(v==1.0){
                        slideLL.setVisibility(View.GONE);
                        playPauseIV.setVisibility(View.GONE);
                    }
                }


            }

            @Override
            public void onPanelStateChanged(View view, SlidingUpPanelLayout.PanelState panelState, SlidingUpPanelLayout.PanelState panelState1) {
                System.out.println("panel state " +panelState1);
                if(panelState1.equals("EXPANDED")){

                    ViewGroup.LayoutParams params = slidingPanelImageView.getLayoutParams();
                    params.width = params.height = ((int) (widthHeight + (widthHeight * sWidth/100)));
                    slidingPanelImageView.setLayoutParams(params);
                }
                if(panelState.equals("COLLAPSED")){
                    slideLL.setVisibility(View.INVISIBLE);
                    playPauseIV.setVisibility(View.INVISIBLE);
                }
            }
        });

        Picasso.with(getApplicationContext()).load("file:///android_asset/happy.jpg").into(slidingPanelImageView);


    }
}
