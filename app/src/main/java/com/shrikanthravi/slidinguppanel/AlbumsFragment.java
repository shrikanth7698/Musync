package com.shrikanthravi.slidinguppanel;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static com.shrikanthravi.slidinguppanel.SongsFragment.songList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment {


    public AlbumsFragment() {
        // Required empty public constructor
    }

    RecyclerView albumsRV;
    AlbumAdapter albumAdapter;
    public static List<Album> albumList;
    List<Song> songListCopy;
    public static int albumLoad=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albums, container, false);

        albumsRV = view.findViewById(R.id.albumsRV);
        albumList = new ArrayList<>();
        songListCopy = new ArrayList<>();
        albumAdapter = new AlbumAdapter(albumList,getActivity().getApplicationContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        albumsRV.setLayoutManager(gridLayoutManager);
        albumsRV.setAdapter(albumAdapter);
        final Handler handler = new Handler();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(albumLoad==1){
                    albumLoad=0;
                    asyncCallAlbums();
                }
                handler.postDelayed(this,1000);
            }
        });
        albumsRV.addOnItemTouchListener(new MyRecyclerItemClickListener(getActivity().getApplicationContext(), new MyRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent albumIntent = new Intent(getActivity(), AlbumActivity.class);
                try {
                    albumIntent.putExtra("position", String.valueOf(position));
                    startActivity(albumIntent);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }));

        return view;
    }

    public void asyncCallAlbums(){
        AsyncAlbumLoader asyncAlbumLoader = new AsyncAlbumLoader();
        asyncAlbumLoader.execute();
    }

    public void getAlbums(){
        songListCopy.addAll(songList);
        Collections.sort(songListCopy, new Comparator<Song>() {
            public int compare(Song a, Song b) {

                String a1 = String.valueOf(a.getAlbumid());
                String b1 = String.valueOf(b.getAlbumid());
                return a1.compareToIgnoreCase(b1);
            }
        });
        HashMap<Long,List<Song>> hashMap = new HashMap<>();

        for(int i=0;i<songListCopy.size();i++){
            try {

                if(!hashMap.containsKey(songListCopy.get(i).getAlbumid())){
                    List<Song> list = new ArrayList<>();
                    list.add(songListCopy.get(i));
                    System.out.println("inside hash");
                    hashMap.put(songListCopy.get(i).getAlbumid(),list);
                }
                else{
                    hashMap.get(songListCopy.get(i).getAlbumid()).add(songListCopy.get(i));
                }

            }
            catch (Exception e){

            }
        }

        System.out.println("hash map size "+hashMap.size());


        Set albumids = hashMap.keySet();


        Iterator iterator = albumids.iterator();
        while (iterator.hasNext()) {

            try {

                List<Song> albumSongList = new ArrayList<>();
                long albumid = (long)iterator.next();
                albumSongList.addAll(hashMap.get(albumid));
                System.out.println("album id "+albumid+" album size " +albumSongList.size());
                albumList.add(new Album(albumSongList.get(0).getAlbum(),albumSongList.size(),null,true,albumSongList));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collections.sort(albumList, new Comparator<Album>() {
            public int compare(Album a, Album b) {
                return a.getAlbumName().compareToIgnoreCase(b.getAlbumName());
            }
        });

    }

    public class AsyncAlbumLoader extends AsyncTask<String, String, Void> {


        public AsyncAlbumLoader(){
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Void doInBackground(String... args) {
            try {

                getAlbums();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            albumAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibletoUser) {
        if (isVisibletoUser) {
            MainActivity.currentPage=2;
        }
    }

}

