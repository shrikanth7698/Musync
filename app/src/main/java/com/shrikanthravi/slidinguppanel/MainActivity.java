package com.shrikanthravi.slidinguppanel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.LinearGradient;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.NO_ID;


import static com.shrikanthravi.slidinguppanel.SongsFragment.songList;
import static com.shrikanthravi.slidinguppanel.SongsFragment.songAdapter;



public class MainActivity extends AppCompatActivity implements MediaController.MediaPlayerControl{

    ImageView slidingPanelImageView;
    CardView slidingPanelCardView;
    SlidingUpPanelLayout slidingUpPanelLayout;
    public TextView songName,artistName,newSongName,newArtistName,leftDuration,rightDuration;
    CardView slcviv;
    int widthHeight ;
    int sWidth;
    public static ViewPager viewPager;
    ImageView playPauseIV,newPlayPauseIV,previousIV,nextIV,heartIV;
    LinearLayout slideLL;
    int previouspos1=0;
    LinearLayout shuffleLL,repeatLL,playsLL;
    ImageView shuffleIV,repeatIV;
    TextView shuffleTV,repeatTV,playsTV;

    public static int currentPage=0;

    public static MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    public static MusicController controller;
    public static boolean paused=false, playbackPaused=false;
    public SeekBar mSeekBar;
    private boolean isCheckedHeart;


    public DBHandler db;

    boolean playBool=true;
    private static final int[] STATE_SET_PLAY =
            {R.attr.state_play, -R.attr.state_pause, -R.attr.state_stop};
    private static final int[] STATE_SET_PAUSE =
            {-R.attr.state_play, R.attr.state_pause, -R.attr.state_stop};

    public static boolean isSlidePanelOpen=false;
    public static boolean isSlidePanelOpen1=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
        Typeface regular = Typeface.createFromAsset(getAssets(), "fonts/product_san_regular.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(),"fonts/product_sans_bold.ttf");
        FontChanger fontChanger = new FontChanger(regular);
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        slidingPanelImageView = findViewById(R.id.slidingUpPanelImageView);
        slidingPanelCardView = findViewById(R.id.slidingUpPanelCardView);
        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
        slcviv = findViewById(R.id.slcviv);
        viewPager = findViewById(R.id.viewPager);
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
        playsTV = findViewById(R.id.playsTV);


        TextView titleTV = findViewById(R.id.titleTV);
        titleTV.getPaint().setShader(new LinearGradient(0,0,0,titleTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));
        titleTV.setTypeface(bold);

        ImageView searchIV = findViewById(R.id.searchIV);
        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);

            }
        });

        slidingUpPanelLayout.setEnabled(true);
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        sWidth = point.x;
        System.out.println("Screen Width "+point.x);
        ViewGroup.LayoutParams params = slidingPanelImageView.getLayoutParams();
        widthHeight = params.width;
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
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
                        isSlidePanelOpen1=true;
                        slideLL.setVisibility(View.GONE);
                        playPauseIV.setVisibility(View.GONE);
                    }
                }


            }

            @Override
            public void onPanelStateChanged(View view, SlidingUpPanelLayout.PanelState panelState, SlidingUpPanelLayout.PanelState panelState1) {
                System.out.println("panel state " +panelState1);
                if(panelState.equals("EXPANDED")){

                    isSlidePanelOpen1=true;
                    ViewGroup.LayoutParams params = slidingPanelImageView.getLayoutParams();
                    params.width = params.height = ((int) (widthHeight + (widthHeight * sWidth/100)));
                    slidingPanelImageView.setLayoutParams(params);
                }
                if(panelState.equals("COLLAPSED")){
                    isSlidePanelOpen1=false;
                    slideLL.setVisibility(View.INVISIBLE);
                    playPauseIV.setVisibility(View.INVISIBLE);
                }
            }
        });

        setController();

        Picasso.with(getApplicationContext()).load("file:///android_asset/happy.jpg").into(slidingPanelImageView);
        setupViewPager(viewPager);

        playPauseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicSrv.isPng()){

                    musicSrv.pausePlayer();
                    playPauseIV.setImageState(STATE_SET_PAUSE,true);
                    newPlayPauseIV.setImageState(STATE_SET_PAUSE,true);
                    playBool=false;
                }
                else{

                    musicSrv.play();
                    playPauseIV.setImageState(STATE_SET_PLAY,true);
                    newPlayPauseIV.setImageState(STATE_SET_PLAY,true);
                    playBool=true;
                }
            }
        });

        newPlayPauseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicSrv.isPng()){
                    musicSrv.pausePlayer();
                    playPauseIV.setImageState(STATE_SET_PAUSE,true);
                    newPlayPauseIV.setImageState(STATE_SET_PAUSE,true);
                    playBool=false;
                }
                else{
                    musicSrv.play();
                    playPauseIV.setImageState(STATE_SET_PLAY,true);
                    newPlayPauseIV.setImageState(STATE_SET_PLAY,true);
                    playBool=true;
                }
            }
        });

        previousIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSrv.playPrev();
                songName.setText(songList.get(musicSrv.getSong()).getTitle());
                newSongName.setText(songList.get(musicSrv.getSong()).getTitle());
                artistName.setText(songList.get(musicSrv.getSong()).getArtist());
                newArtistName.setText(songList.get(musicSrv.getSong()).getArtist());
                ((Animatable) previousIV.getDrawable()).start();
            }
        });

        nextIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                musicSrv.playNext();

                songName.setText(songList.get(musicSrv.getSong()).getTitle());
                newSongName.setText(songList.get(musicSrv.getSong()).getTitle());
                artistName.setText(songList.get(musicSrv.getSong()).getArtist());
                newArtistName.setText(songList.get(musicSrv.getSong()).getArtist());

                ((Animatable) nextIV.getDrawable()).start();
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(musicSrv != null && fromUser){
                    musicSrv.seek(progress*1000);
                }
            }
        });

        final Handler mHandler = new Handler();
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(musicSrv != null){
                    try {
                        if(musicSrv.isPng()) {


                            if(isSlidePanelOpen==true){
                                isSlidePanelOpen = false;
                                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                            }
                            playsTV.setText(songList.get(musicSrv.getSong()).getPlayCount()+" plays");

                            songAdapter.setPlayingSongPostion(musicSrv.getSong());
                            if(previouspos1!=musicSrv.getSong()) {
                                songAdapter.notifyDataSetChanged();
                            }
                            previouspos1 = musicSrv.getSong();
                            /*
                            previouspos1 = musicSrv.getSong();
                            songList.get(previouspos).setPlaying(false);
                            songList.get(musicSrv.getSong()).setPlaying(true);*/
                            playPauseIV.setImageState(STATE_SET_PAUSE,true);
                            newPlayPauseIV.setImageState(STATE_SET_PAUSE,true);
                        }
                        else{

                            playPauseIV.setImageState(STATE_SET_PLAY,true);
                            newPlayPauseIV.setImageState(STATE_SET_PLAY,true);
                        }

                        if(songList.get(musicSrv.getSong()).isFavourite()){

                            final int[] stateSet = {android.R.attr.state_checked * (1)};
                            heartIV.setImageState(stateSet,true);

                        }
                        else{

                            final int[] stateSet = {android.R.attr.state_checked * (-1)};
                            heartIV.setImageState(stateSet,true);
                        }
                        int mCurrentPosition = musicSrv.getPosn() / 1000;

                        mSeekBar.setProgress(mCurrentPosition);
                        final long minutes = (mCurrentPosition) / 60;
                        int min = (int) minutes;
                        final int seconds = (int) ((mCurrentPosition) % 60);
                        boolean isTwoDigit3 = Integer.toString(Math.abs(min)).trim().length() == 2;
                        boolean isTwoDigit4 = Integer.toString(Math.abs(seconds)).trim().length() == 2;
                        if (!isTwoDigit3 && !isTwoDigit4) {

                            leftDuration.setText("0" + minutes + ":" + "0" + seconds);
                        } else {
                            if (!isTwoDigit3) {
                                leftDuration.setText("0" + minutes + ":" + seconds);
                            } else {
                                if (!isTwoDigit4) {

                                    leftDuration.setText(minutes + ":" + "0" + seconds);
                                }
                            }
                        }
                        final long minutes1 = (musicSrv.getDur() / 1000) / 60;
                        final int seconds1 = (int) ((musicSrv.getDur() / 1000) % 60);
                        int newminutes = (int) (minutes1 - minutes);
                        int newseconds = (seconds1 - seconds);

                        if (newseconds < 0) {
                            newminutes = newminutes - 1;
                            newseconds = 60 + newseconds;
                        }

                        boolean isTwoDigit1 = Integer.toString(Math.abs(newminutes)).trim().length() == 2;
                        boolean isTwoDigit2 = Integer.toString(Math.abs(newseconds)).trim().length() == 2;

                        if (!isTwoDigit1 && !isTwoDigit2) {
                            rightDuration.setText("-" + "0" + newminutes + ":" + "0" + newseconds);
                        } else {
                            if (!isTwoDigit1) {

                                rightDuration.setText("-" + "0" + newminutes + ":" + newseconds);
                            } else {
                                if (!isTwoDigit2) {

                                    rightDuration.setText("-" + newminutes + ":" + "0" + newseconds);
                                }
                            }
                        }
                        if (mCurrentPosition == 0 && musicSrv.isPng()) {
                            //ArrayList<String> favlist = new ArrayList<String>();
                            //favlist.addAll(db.getFavList());


                            System.out.println("song id in main activity " + songList.get(musicSrv.getSong()).getId());
                            System.out.println("duration " + musicSrv.getDur());
                            mSeekBar.setMax(musicSrv.getDur() / 1000);
                            if(musicSrv.getFavFlag().equals("fav")){
                                /*if (favlist.contains(Long.toString(favsongs.get(musicSrv.getSong()).getID()))) {
                                    heart.setImageResource(R.drawable.hearts);
                                } else {
                                    heart.setImageResource(R.drawable.heart);
                                }*//*
                                SongImage.setImageBitmap(favsongs.get(musicSrv.getSong()).getSongImage());
                                slidinglayImage.setImageBitmap(favsongs.get(musicSrv.getSong()).getSongImage());
                                songName.setText(favsongs.get(musicSrv.getSong()).getTitle());
                                newSongName.setText(favsongs.get(musicSrv.getSong()).getTitle());
                                artistName.setText(favsongs.get(musicSrv.getSong()).getArtist());
                                newArtistName.setText(favsongs.get(musicSrv.getSong()).getArtist());
                                spBackgroundImage.setImageBitmap(favsongs.get(musicSrv.getSong()).getSongImage());*/
                            }
                            else {/*
                                if (favlist.contains(Long.toString(songList.get(musicSrv.getSong()).getID()))) {
                                    heart.setImageResource(R.drawable.hearts);
                                } else {
                                    heart.setImageResource(R.drawable.heart);
                                }*/

                                slidingPanelImageView.setImageBitmap(songList.get(musicSrv.getSong()).getSongImage());
                                songName.setText(songList.get(musicSrv.getSong()).getTitle());
                                newSongName.setText(songList.get(musicSrv.getSong()).getTitle());
                                artistName.setText(songList.get(musicSrv.getSong()).getArtist());
                                newArtistName.setText(songList.get(musicSrv.getSong()).getArtist());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                mHandler.postDelayed(this, 1000);
            }
        });


        shuffleLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicSrv.getShuffle()){
                    musicSrv.setShuffle();
                    shuffleLL.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                    shuffleTV.setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
                    shuffleIV.setColorFilter(getResources().getColor(R.color.colorAccent));
                }
                else{
                    musicSrv.setShuffle();
                    shuffleLL.setBackground(getResources().getDrawable(R.drawable.rounded_corners1));
                    shuffleTV.setTextColor(getResources().getColor(android.R.color.white));
                    shuffleIV.setColorFilter(getResources().getColor(android.R.color.white));
                }
            }
        });

        repeatLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(musicSrv.isRepeat()){
                    musicSrv.setRepeat(false);
                    repeatLL.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                    repeatTV.setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
                    repeatIV.setColorFilter(getResources().getColor(R.color.colorAccent));
                }
                else{
                    musicSrv.setRepeat(true);
                    repeatLL.setBackground(getResources().getDrawable(R.drawable.rounded_corners1));
                    repeatTV.setTextColor(getResources().getColor(android.R.color.white));
                    repeatIV.setColorFilter(getResources().getColor(android.R.color.white));
                }
            }
        });

        heartIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (songList.get(musicSrv.getSong()).isFavourite()) {


                        db.delete(Long.toString(songList.get(musicSrv.getSong()).getId()));
                        final int[] stateSet = {android.R.attr.state_checked * (-1)};
                        heartIV.setImageState(stateSet, true);

                        songList.get(musicSrv.getSong()).setFavourite(false);

                    } else {


                        db.insert_all(Long.toString(songList.get(musicSrv.getSong()).getId()));
                        final int[] stateSet = {android.R.attr.state_checked * (1)};
                        heartIV.setImageState(stateSet, true);

                        songList.get(musicSrv.getSong()).setFavourite(true);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(),"Home");
        adapter.addFragment(new SongsFragment(), "Songs");
        adapter.addFragment(new AlbumsFragment(),"Albums");
        adapter.addFragment(new FavsFragment(), "Favourites");
        adapter.addFragment(new PlaylistsFragment(), "Playlist");
        adapter.addFragment(new SyncPlayFragment(), "Sync n Play");
        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        SimpleTabProvider tabProvider = new SimpleTabProvider(getApplicationContext(),R.layout.custom_tab,R.id.CustomTabText);
        viewPagerTab.setCustomTabView(tabProvider);
        viewPagerTab.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private static class SimpleTabProvider implements SmartTabLayout.TabProvider {

        private final LayoutInflater inflater;
        private final int tabViewLayoutId;
        private final int tabViewTextViewId;
        TextView tabTitleView = null;
        private final Typeface font;
        private SimpleTabProvider(Context context, int layoutResId, int textViewId) {
            inflater = LayoutInflater.from(context);
            tabViewLayoutId = layoutResId;
            tabViewTextViewId = textViewId;
            font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
        }

        @Override
        public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
            View tabView = null;
            if (tabViewLayoutId != NO_ID) {
                tabView = inflater.inflate(tabViewLayoutId, container, false);
            }

            if (tabViewTextViewId != NO_ID && tabView != null) {
                tabTitleView = (TextView) tabView.findViewById(tabViewTextViewId);
            }

            if (tabTitleView == null && TextView.class.isInstance(tabView) && ImageView.class.isInstance(tabView)) {
                tabTitleView = (TextView) tabView;
            }

            if (tabTitleView != null) {
                tabTitleView.setText(adapter.getPageTitle(position));
                tabTitleView.setTypeface(font);
            }

            return tabView;
        }
    }


    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        paused=true;
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused=false;
        }
    }
    @Override
    protected void onStop() {
        controller.hide();

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(musicConnection);
    }

    private void playNext(){
        musicSrv.playNext();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    private void playPrev(){
        musicSrv.playPrev();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }
    private void setController(){
        //set the controller up
        controller = new MusicController(this);
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });
        controller.setMediaPlayer(this);
        //controller.setAnchorView(findViewById(R.id.slideContent));
        controller.setEnabled(true);
    }
    @Override
    public boolean canPause() {
        return true;
    }
    @Override
    public boolean canSeekBackward() {
        return true;
    }
    @Override
    public boolean canSeekForward() {
        return true;
    }
    @Override
    public int getCurrentPosition() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
            return musicSrv.getPosn();
        else return 0;
    }
    @Override
    public int getBufferPercentage(){
        return 0;
    }
    @Override
    public int getAudioSessionId(){
        return 0;
    }
    @Override
    public int getDuration() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
            return musicSrv.getDur();
        else return 0;
    }
    @Override
    public boolean isPlaying() {
        if(musicSrv!=null && musicBound)
            return musicSrv.isPng();
        return false;
    }
    @Override
    public void pause() {
        playbackPaused=true;
        musicSrv.pausePlayer();
    }

    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
    }

    @Override
    public void start() {
        musicSrv.go();
    }

    @Override
    public void onBackPressed(){
        if(isSlidePanelOpen1==true){
            System.out.println("slidepanel back");
            isSlidePanelOpen1=false;
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
        else{
            if(currentPage==0){

            }
            else{
                viewPager.setCurrentItem(0,true);
            }
        }
    }

}
