package com.shrikanthravi.slidinguppanel;

/**
 * Created by shrikanthravi on 08/01/18.
 */


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {
    private List<Album> AlbumList;
    Context context;
    int playingpos;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView AlbumName;
        public ImageView AlbumImage;
        public TextView totalSongsName;

        public MyViewHolder(View view) {
            super(view);
            AlbumName = (TextView) view.findViewById(R.id.albumTV);
            AlbumImage = (ImageView) view.findViewById(R.id.albumIV);
            totalSongsName = (TextView) view.findViewById(R.id.totalSongsTV);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();
                    return true;
                }
            });
        }
    }

    public AlbumAdapter(List<Album> verticalList, Context context) {

        this.AlbumList = verticalList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_row_item, parent, false);

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
        FontChanger fontChanger = new FontChanger(font);
        fontChanger.replaceFonts((ViewGroup)itemView);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");



        try {
            holder.AlbumName.setText(AlbumList.get(position).getAlbumName());
            holder.AlbumImage.setImageBitmap(AlbumList.get(position).getSongList().get(0).getSongImage());
            holder.totalSongsName.setText(AlbumList.get(position).getSongList().size() + " songs");
        }catch (Exception e){

        }


    }
    @Override
    public int getItemCount() {
        return AlbumList.size();
    }

    public void setPlayingAlbumPostion(int pos){
        playingpos=pos;
    }
}