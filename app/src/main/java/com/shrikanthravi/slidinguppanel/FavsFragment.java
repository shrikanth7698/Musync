package com.shrikanthravi.slidinguppanel;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static com.shrikanthravi.slidinguppanel.SongsFragment.songList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavsFragment extends Fragment {


    public FavsFragment() {
        // Required empty public constructor
    }

    RecyclerView favsRV;
    List<Song> favsList;
    SongAdapter favsAdapter;
    public static int stop=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favs, container, false);

        favsRV = view.findViewById(R.id.favsRV);
        favsList = new ArrayList<>();
        favsAdapter = new SongAdapter(favsList,getActivity().getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        favsRV.setLayoutManager(layoutManager);
        favsRV.setAdapter(favsAdapter);
        final Handler handler = new Handler();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(stop==1){
                    stop=0;
                    AsyncFavLoader asyncFavLoader = new AsyncFavLoader();
                    asyncFavLoader.execute();
                }
                handler.postDelayed(this,1000);
            }
        });
        return view;
    }

    public void getFavs(){
        for(int i=0;i<songList.size();i++){
            if(songList.get(i).isFavourite()){
                favsList.add(songList.get(i));
            }
        }
    }

    public class AsyncFavLoader extends AsyncTask<String, String, Void> {


        public AsyncFavLoader(){
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Void doInBackground(String... args) {
            try {

                getFavs();

            } catch (Exception e) {
                e.getMessage();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            favsAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibletoUser) {
        if (isVisibletoUser) {
            MainActivity.currentPage = 3;
        }
    }
}
