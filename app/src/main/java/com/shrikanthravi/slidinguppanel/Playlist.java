package com.shrikanthravi.slidinguppanel;

/**
 * Created by shrikanthravi on 08/01/18.
 */


public class Playlist {

    String PlayListName;
    String NoofTracks;
    String TotalDuration;

    public Playlist(String pn,String nt,String td){
        this.PlayListName = pn;
        this.NoofTracks = nt;
        this.TotalDuration = td;
    }
    public String getPlayListName(){
        return this.PlayListName;
    }
    public String getNoofTracks(){
        return this.NoofTracks;
    }
    public String getTotalDuration(){
        return this.TotalDuration;
    }

    public void setPlayListName(String pl){
        this.PlayListName = pl;
    }
    public void setNoofTracks(String nt){
        this.NoofTracks = nt;
    }
    public void setTotalDuration(String td){
        this.TotalDuration = td;
    }


}
