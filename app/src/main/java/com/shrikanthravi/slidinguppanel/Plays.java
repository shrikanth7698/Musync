package com.shrikanthravi.slidinguppanel;

/**
 * Created by shrikanthravi on 10/01/18.
 */

public class Plays {
    String songId;
    int playcount;

    public Plays(String songId, int playcount) {
        this.songId = songId;
        this.playcount = playcount;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }
}
