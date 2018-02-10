package com.shrikanthravi.slidinguppanel;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cielyang.android.clearableedittext.ClearableEditText;

import java.util.ArrayList;
import java.util.List;

import static com.shrikanthravi.slidinguppanel.AlbumsFragment.albumList;
import static com.shrikanthravi.slidinguppanel.SongsFragment.songList;


public class SearchActivity extends AppCompatActivity {

    ClearableEditText searchET;
    List<Song> songListCopy;
    List<Album> albumListCopy;
    RecyclerView songSearchRV,albumSearchRV;
    SongAdapter songAdapter;
    AlbumAdapter albumAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search);

        getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
        Typeface regular = Typeface.createFromAsset(getAssets(), "fonts/product_san_regular.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(),"fonts/product_sans_bold.ttf");
        FontChanger fontChanger = new FontChanger(regular);
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        TextView songSearchTV = findViewById(R.id.songSearchTV);
        TextView albumSearchTV = findViewById(R.id.albumSearchTV);
        songSearchTV.getPaint().setShader(new LinearGradient(0,0,0,songSearchTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));
        albumSearchTV.getPaint().setShader(new LinearGradient(0,0,0,songSearchTV.getLineHeight(),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));
        songSearchTV.setTypeface(bold);
        albumSearchTV.setTypeface(bold);
        songListCopy = new ArrayList<>();
        albumListCopy = new ArrayList<>();

        songListCopy.clear();
        albumListCopy.clear();
/*
        songListCopy.addAll(songList);
        albumListCopy.addAll(albumList);*/

        songSearchRV = findViewById(R.id.songSearchRV);
        albumSearchRV = findViewById(R.id.albumSearchRV);

        songAdapter = new SongAdapter(songListCopy,getApplicationContext());
        albumAdapter = new AlbumAdapter(albumListCopy,getApplicationContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);

        songSearchRV.setLayoutManager(layoutManager);
        albumSearchRV.setLayoutManager(gridLayoutManager);

        songSearchRV.setNestedScrollingEnabled(false);
        albumSearchRV.setNestedScrollingEnabled(false);
        songSearchRV.setHasFixedSize(true);
        albumSearchRV.setHasFixedSize(true);
        songSearchRV.setAdapter(songAdapter);
        albumSearchRV.setAdapter(albumAdapter);

        searchET = findViewById(R.id.searchET);

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()!=0){
                    filter(s.toString().trim());
                }
                else {
                    songListCopy.clear();
                    albumListCopy.clear();
                    songAdapter.notifyDataSetChanged();
                    albumAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void filter(String s){
        songListCopy.clear();
        albumListCopy.clear();
        for(int i=0;i<songList.size();i++){
            if(songList.get(i).getTitle().toLowerCase().contains(s.toLowerCase())){

                songListCopy.add(songList.get(i));
                songAdapter.notifyDataSetChanged();

            }
        }

        for(int j=0;j<albumList.size();j++){
            if(albumList.get(j).getAlbumName().toLowerCase().contains(s.toLowerCase())){
                albumListCopy.add(albumList.get(j));
                albumAdapter.notifyDataSetChanged();
            }
        }

    }


}
