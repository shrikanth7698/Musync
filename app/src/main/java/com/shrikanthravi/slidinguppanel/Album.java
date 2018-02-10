package com.shrikanthravi.slidinguppanel;

import android.graphics.Bitmap;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shrikanthravi on 08/01/18.
 */

public class Album implements Serializable{



    public String albumName;
    public int totalSongs;
    public Bitmap albumArt;
    public int position;
    public boolean isAlbum;
    public List<Song> songList;


    /*public Album(){

    }*/

   /* public Album(String albumName, int totalSongs, Bitmap albumArt, boolean isAlbum) {
        this.albumName = albumName;
        this.totalSongs = totalSongs;
        this.albumArt = albumArt;
        this.isAlbum = isAlbum;
    }*/

    public Album(String albumName, int totalSongs, Bitmap albumArt, boolean isAlbum, List<Song> songList) {
        this.albumName = albumName;
        this.totalSongs = totalSongs;
        this.albumArt = albumArt;
        this.isAlbum = isAlbum;
        this.songList = songList;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getTotalSongs() {
        return totalSongs;
    }

    public void setTotalSongs(int totalSongs) {
        this.totalSongs = totalSongs;
    }

    public Bitmap getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(Bitmap albumArt) {
        this.albumArt = albumArt;
    }

    public boolean isAlbum() {
        return isAlbum;
    }

    public void setAlbum(boolean album) {
        isAlbum = album;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
