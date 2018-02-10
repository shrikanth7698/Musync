package com.shrikanthravi.slidinguppanel;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by shrikanthravi on 06/01/18.
 */

public class Song implements Serializable {

    private long id;
    private long albumid;
    private String title;
    private String artist;
    private Bitmap songImage;
    private String duration;
    private boolean isSong;
    private boolean isPlaying;
    private String album;
    private boolean favourite;
    private int playCount;

    public Song(long id, long albumid, String title, String artist, Bitmap songImage, String duration, boolean isSong,  String album, boolean favourite, int playCount) {
        this.id = id;
        this.albumid = albumid;
        this.title = title;
        this.artist = artist;
        this.songImage = songImage;
        this.duration = duration;
        this.isSong = isSong;
        this.isPlaying = isPlaying;
        this.album = album;
        this.favourite = favourite;
        this.playCount = playCount;
    }
/*
    public Song(long id, long albumid, String title, String artist, Bitmap songImage, String duration, boolean isSong, String album, boolean favourite) {
        this.id = id;
        this.albumid = albumid;
        this.title = title;
        this.artist = artist;
        this.songImage = songImage;
        this.duration = duration;
        this.isSong = isSong;
        this.isPlaying = isPlaying;
        this.album = album;
        this.favourite = favourite;
    }*/



    /*public Song(long id, long albumid, String title, String artist, Bitmap songImage, String duration, boolean isSong, String album) {
        this.id = id;
        this.albumid = albumid;
        this.title = title;
        this.artist = artist;
        this.songImage = songImage;
        this.duration = duration;
        this.isSong = isSong;
        this.isPlaying = isPlaying;
        this.album = album;
    }*/
    /*

    public Song(long id, long albumid, String title, String artist, Bitmap songImage, String duration, boolean isSong) {
        this.id = id;
        this.albumid = albumid;
        this.title = title;
        this.artist = artist;
        this.songImage = songImage;
        this.duration = duration;
        this.isSong = isSong;
    }*/

    public boolean isSong() {
        return isSong;
    }

    public void setSong(boolean song) {
        isSong = song;
    }

    public long getAlbumid() {
        return albumid;
    }

    public void setAlbumid(long albumid) {
        this.albumid = albumid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Bitmap getSongImage() {
        return songImage;
    }

    public void setSongImage(Bitmap songImage) {
        this.songImage = songImage;
    }

    public String getDuration() {

        String result="";
        try {
            final long minutes1 = (Integer.parseInt(duration) / 1000) / 60;
            final int seconds1 = (int) ((Integer.parseInt(duration) / 1000) % 60);
            result = minutes1 + "m";
        }
        catch (Exception e){

        }
        return result;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }
}
