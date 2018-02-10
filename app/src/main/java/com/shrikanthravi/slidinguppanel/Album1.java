package com.shrikanthravi.slidinguppanel;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by shrikanthravi on 09/01/18.
 */

public class Album1 {

    public String albumName;
    public int totalSongs;
    public int position;
    public boolean isAlbum;
    public List<Song> songList;

    public Album1(String albumName, int totalSongs, int position, boolean isAlbum, List<Song> songList) {
        this.albumName = albumName;
        this.totalSongs = totalSongs;
        this.position = position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
}
