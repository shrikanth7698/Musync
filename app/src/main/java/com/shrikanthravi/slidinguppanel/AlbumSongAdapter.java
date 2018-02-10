package com.shrikanthravi.slidinguppanel;

/**
 * Created by shrikanthravi on 09/01/18.
 */


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import com.taishi.library.Indicator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shrikanthravi on 06/01/18.
 */


public class AlbumSongAdapter extends RecyclerView.Adapter<AlbumSongAdapter.MyViewHolder> {
    private List<Song> SongList;
    Context context;
    int selectedCount=0;
    int playingpos;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView SongName;
        public TextView SongPostion;
        public TextView artistName;
        public Indicator musicIndicator;
        public LinearLayout songLL;
        public TextView totatSongsTV;

        public MyViewHolder(View view) {
            super(view);
            SongName = (TextView) view.findViewById(R.id.songName);
            SongPostion = (TextView) view.findViewById(R.id.postionTV);
            artistName = (TextView) view.findViewById(R.id.artistName);
            musicIndicator = (Indicator) view.findViewById(R.id.MusicIndicator);
            songLL = (LinearLayout) view.findViewById(R.id.songLL);
            totatSongsTV = (TextView) view.findViewById(R.id.totalSongsTextView);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }
    }

    public AlbumSongAdapter(List<Song> verticalList, Context context) {

        this.SongList = verticalList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_song_row_item, parent, false);

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
        FontChanger fontChanger = new FontChanger(font);
        fontChanger.replaceFonts((ViewGroup)itemView);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
        Typeface bold = Typeface.createFromAsset(context.getAssets(), "fonts/product_sans_bold.ttf");



        if(SongList.get(position).isSong()) {

            holder.songLL.setVisibility(View.VISIBLE);
            holder.SongName.setTypeface(font);
            holder.artistName.setTypeface(font);

            holder.SongName.setText(SongList.get(position).getTitle().trim());
            holder.artistName.setText(SongList.get(position).getArtist().toString().trim());
            holder.SongPostion.getPaint().setShader(new LinearGradient(0,0,0,holder.SongPostion.getLineHeight(),context.getResources().getColor(R.color.colorAccent),context.getResources().getColor(R.color.colorAccent1), Shader.TileMode.MIRROR));
            holder.SongPostion.setText(""+(position+1)+"");
            holder.SongPostion.setTypeface(bold);


            if(position==playingpos){
                holder.musicIndicator.setVisibility(View.GONE);
            }
            else{

                holder.musicIndicator.setVisibility(View.GONE);
            }
            try {



            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }
    @Override
    public int getItemCount() {
        return SongList.size();
    }

    public void setPlayingSongPostion(int pos){
        playingpos=pos;
    }
}