package com.shrikanthravi.slidinguppanel;


import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.taishi.library.Indicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.shrikanthravi.slidinguppanel.MainActivity.isSlidePanelOpen;
import static com.shrikanthravi.slidinguppanel.MainActivity.musicSrv;
import static com.shrikanthravi.slidinguppanel.MainActivity.playbackPaused;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongsFragment extends Fragment {


    public SongsFragment() {
        // Required empty public constructor
    }

    RecyclerView songsRV;
    public static ArrayList<Song> songList;
    public static SongAdapter songAdapter;

    public ImageView splaypause,newplaypause,newprev,newnext;


    private static final int[] STATE_SET_PLAY =
            {R.attr.state_play, -R.attr.state_pause, -R.attr.state_stop};
    private static final int[] STATE_SET_PAUSE =
            {-R.attr.state_play, R.attr.state_pause, -R.attr.state_stop};

    public static int previouspos;

    public static Context activityContext;

    public DBHandler db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_songs, container, false);

        activityContext = getContext();

        db = new DBHandler(getActivity().getApplicationContext());
/*
        Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/product_san_regular.ttf");
        FontChanger fontChanger = new FontChanger(regular);
        fontChanger.replaceFonts((ViewGroup)view);*/

        splaypause = getActivity().findViewById(R.id.playPauseIV);
        newplaypause = getActivity().findViewById(R.id.newPlayPauseIV);
        songsRV = view.findViewById(R.id.songsRV);
        songList = new ArrayList<>();
        songAdapter = new SongAdapter(songList,getActivity().getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        songsRV.setLayoutManager(layoutManager);
        songsRV.setAdapter(songAdapter);
        //getSongs();
        //songList.add(new Song(0,0,"0",null,null,null,false,null));
        songAdapter.notifyDataSetChanged();
        songsRV.addOnItemTouchListener(new MyRecyclerItemClickListener(getActivity().getApplicationContext(), new MyRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(songList.get(position).isSong()) {
                    musicSrv.setList(songList);
                    musicSrv.setFavFlag("def");
                    musicSrv.setSong(position);
                    musicSrv.playSong();/*
                songList.get(previouspos).setPlaying(false);
                previouspos = position;
                songList.get(position).setPlaying(true);*/
                    songAdapter.notifyDataSetChanged();
                    splaypause.setImageState(STATE_SET_PAUSE, true);
                    newplaypause.setImageState(STATE_SET_PAUSE, true);
                    if (playbackPaused) {
                        playbackPaused = false;
                    }

                    isSlidePanelOpen=true;
                }
            }
        }));


        AsyncSongLoader asyncSongLoader = new AsyncSongLoader();
        asyncSongLoader.execute();
        return view;
    }

    public void getSongs(){

        ArrayList<String> favList = new ArrayList<>();
        favList.addAll(db.getFavList());
        ArrayList<Plays> playsList = new ArrayList<>();
        playsList.addAll(db.getPlay());

        ContentResolver musicResolver = getActivity().getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int albumIdColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int durationColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int column_index = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);



            //add songs to list
            do {

                long thisAlbumId = musicCursor.getLong(albumIdColumn);
                long thisId = musicCursor.getLong(idColumn);
                String pathId = musicCursor.getString(column_index);
                String duration = musicCursor.getString(durationColumn);
                Log.d(this.getClass().getName(), "path id=" + pathId);
                MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
                metaRetriver.setDataSource(pathId);

                int playCount=0;
                //System.out.println("plays size "+playsList.size());

                if(playsList.size()!=0) {
                    for (int i = 0; i < playsList.size(); i++) {
                        if (playsList.get(i).getSongId().equals(Long.toString(thisId))) {
                            playCount = playsList.get(i).getPlaycount();
                            //System.out.println("plays count "+playsList.get(i).getPlaycount());
                            break;
                        }
                    }
                }
                try {
                    byte[] art = metaRetriver.getEmbeddedPicture();
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inSampleSize = 2;
                    Bitmap songImage = BitmapFactory .decodeByteArray(art, 0, art.length,opt);
                    String thisTitle = musicCursor.getString(titleColumn);
                    String thisArtist = musicCursor.getString(artistColumn);
                    String album = musicCursor.getString(albumColumn);
                    if(album==null){
                        album="#";
                    }
                    if(favList.contains(Long.toString(thisId))) {
                        songList.add(new Song(thisId, thisAlbumId, thisTitle, thisArtist, songImage, duration, true, album,true,playCount));
                    }
                    else{
                        songList.add(new Song(thisId, thisAlbumId, thisTitle, thisArtist, songImage, duration, true, album,false,playCount));
                    }
                   // songAdapter.notifyDataSetChanged();
                }
                catch (Exception e)
                {

                    String thisTitle = musicCursor.getString(titleColumn);
                    String thisArtist = musicCursor.getString(artistColumn);
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inSampleSize = 2;
                    Bitmap songImage = BitmapFactory.decodeResource(getResources(),(R.drawable.slidingpanelimage),opt);
                    String album = musicCursor.getString(albumColumn);
                    if(album==null){
                        album="#";
                    }
                    if(favList.contains(Long.toString(thisId))) {
                        songList.add(new Song(thisId, thisAlbumId, thisTitle, thisArtist, songImage, duration, true, album,true,playCount));
                    }
                    else{
                        songList.add(new Song(thisId, thisAlbumId, thisTitle, thisArtist, songImage, duration, true, album,false,playCount));
                    }
                    System.out.println("error message "+e.getMessage());
                }

            }
            while (musicCursor.moveToNext());
        }

        Collections.sort(songList, new Comparator<Song>() {
            public int compare(Song a, Song b) {

                return a.getTitle().compareToIgnoreCase(b.getTitle());
            }
        });
    }

    public class AsyncSongLoader extends AsyncTask<String, String, Void> {


        public AsyncSongLoader(){
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Void doInBackground(String... args) {
            try {

                getSongs();

            } catch (Exception e) {
                e.getMessage();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            //Below code is added to avoid Fragment not attached to activity error
            HomeFragment.stop=1;
            AlbumsFragment.albumLoad=1;
            FavsFragment.stop=1;
            songAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibletoUser) {
        if (isVisibletoUser) {
            MainActivity.currentPage=1;
        }
    }

}
