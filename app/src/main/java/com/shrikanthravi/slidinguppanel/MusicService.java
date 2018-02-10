package com.shrikanthravi.slidinguppanel;

/**
 * Created by shrikanthravi on 07/01/18.
 */


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener  {
    //media player
    private MediaPlayer player;
    //song list
    private ArrayList<Song> songs;
    //current position
    private int songPosn;
    public String flag;
    private final IBinder musicBind = new MusicBinder();
    private static final int NOTIFY_ID=1;
    private boolean shuffle=false;
    private Random rand;
    private boolean repeat=false;
    private DBHandler dbHandler;
    public MusicService() {
    }

    public void onCreate(){
        //create the service
        super.onCreate();

        dbHandler = new DBHandler(this);
        System.out.println("service tested");
        //initialize position
        songPosn=0;
        //create player
        player = new MediaPlayer();
        initMusicPlayer();
        rand=new Random();
    }
    public void initMusicPlayer(){
        //set player properties
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }
    public void setList(ArrayList<Song> theSongs){
        songs=theSongs;
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }
    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }

    public void setShuffle(){
        if(shuffle) shuffle=false;
        else shuffle=true;
    }

    public boolean getShuffle(){
        return shuffle;
    }

    public void playSong(){
        //play a song
        player.reset();
        //get song

        System.out.println("song position"+songPosn);
        Song playSong = songs.get(songPosn);
        try {
            dbHandler.insert_play(Long.toString(playSong.getId()));
        }
        catch (Exception e){
            System.out.println("error updating play count in db "+e.getMessage());

        }
//get id
        long currSong = playSong.getId();
//set uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);
        try{
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

        player.prepareAsync();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //start playback
        mp.start();
        System.out.println("mp prepared");
        Intent notIntent = new Intent(this, MainActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.sliding_play)
                .setTicker(songs.get(songPosn).getTitle())
                .setOngoing(true)
                .setContentTitle("Playing")
                .setContentText(songs.get(songPosn).getTitle());
        Notification not = builder.build();

        startForeground(NOTIFY_ID, not);
    }
    public void setSong(int songIndex){
        songPosn=songIndex;
    }
    public int getSong(){
        return songPosn;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(player.getCurrentPosition() > 0){
            mp.reset();
            playNext();
        }
    }
    @Override
    public void onDestroy() {
        stopForeground(true);
    }
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();

        return false;
    }
    public int getPosn(){
        return player.getCurrentPosition();
    }

    public int getDur(){
        return player.getDuration();
    }

    public boolean isPng(){
        return player.isPlaying();
    }

    public void pausePlayer(){
        player.pause();
    }

    public void seek(int posn){
        player.seekTo(posn);
    }

    public void go(){
        player.start();
    }
    public void playPrev(){
        if(repeat==false) {
            songPosn--;
            if (songPosn < 0) songPosn = songs.size() - 1;
        }
        playSong();
    }
    //skip to next
    public void playNext(){
        if(shuffle){

            if(repeat==false) {
                int newSong = songPosn;
                while (newSong == songPosn) {
                    newSong = rand.nextInt(songs.size());
                }
                songPosn = newSong;
            }

        }
        else{
            if(repeat==false) {
                songPosn++;
                if (songPosn >= songs.size()) songPosn = 0;
            }

        }
        playSong();
    }

    public void setFavFlag(String fav){
        flag = fav;
    }

    public String getFavFlag(){
        return flag;
    }
    public void play(){
        player.start();
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }
}
