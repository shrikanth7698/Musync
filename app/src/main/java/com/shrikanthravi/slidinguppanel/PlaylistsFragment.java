package com.shrikanthravi.slidinguppanel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistsFragment extends Fragment {


    public PlaylistsFragment() {
        // Required empty public constructor
    }

    RecyclerView playListRV;
    List<Playlist> playlists;
    PlaylistAdapter playlistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlists, container, false);

        playListRV = view.findViewById(R.id.playlistRV);
        playlists = new ArrayList<>();
        playlistAdapter = new PlaylistAdapter(playlists,getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        playListRV.setLayoutManager(linearLayoutManager);
        playListRV.setAdapter(playlistAdapter);
        playlists.add(new Playlist("New Playlist","",""));
        playlists.add(new Playlist("Favourites Mix","",""));
        playlists.add(new Playlist("Most Played Mix","",""));
        playlistAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibletoUser) {
        if (isVisibletoUser) {
            MainActivity.currentPage=4;
        }
    }

}
