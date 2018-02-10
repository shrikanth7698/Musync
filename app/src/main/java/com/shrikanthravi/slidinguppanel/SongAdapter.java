package com.shrikanthravi.slidinguppanel;

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

/**
 * Created by shrikanthravi on 06/01/18.
 */


public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {
    private List<Song> SongList;
    Context context;
    int selectedCount=0;
    int playingpos;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView SongName;
        public ImageView SongImage;
        public TextView artistName;
        public Indicator musicIndicator;
        public LinearLayout songLL;
        public FlexboxLayout flexboxLayout;
        public TextView totatSongsTV;

        public MyViewHolder(View view) {
            super(view);
            SongName = (TextView) view.findViewById(R.id.songName);
            SongImage = (ImageView) view.findViewById(R.id.songImage);
            artistName = (TextView) view.findViewById(R.id.artistName);
            musicIndicator = (Indicator) view.findViewById(R.id.MusicIndicator);
            songLL = (LinearLayout) view.findViewById(R.id.songLL);
            flexboxLayout = (FlexboxLayout) view.findViewById(R.id.flexLayout);
            totatSongsTV = (TextView) view.findViewById(R.id.totalSongsTextView);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();
                    if(SongList.get(pos).isSong()) {
                        final Dialog dialog = new Dialog(SongsFragment.activityContext);
                        Typeface regular = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.song_longpress_dialog);
                        dialog.setCancelable(true);
                        ImageView songDIV = dialog.findViewById(R.id.songIV);
                        TextView songnameDTV = dialog.findViewById(R.id.songNameTV);
                        TextView artistnameDTV = dialog.findViewById(R.id.artistNameTV);
                        TextView playlistDTV = dialog.findViewById(R.id.playlistTV);
                        TextView queueDTV = dialog.findViewById(R.id.queueTV);
                        TextView playNextDTV = dialog.findViewById(R.id.playNextTV);
                        TextView shareDTV = dialog.findViewById(R.id.shareTV);
                        TextView lyricsDTV = dialog.findViewById(R.id.lyricsTV);
                        TextView loveDTV = dialog.findViewById(R.id.loveTV);
                        TextView durationDTV = dialog.findViewById(R.id.durationTV);
                        TextView playsTV = dialog.findViewById(R.id.playsTV);

                        songnameDTV.setTypeface(regular);
                        artistnameDTV.setTypeface(regular);
                        playlistDTV.setTypeface(regular);
                        queueDTV.setTypeface(regular);
                        playNextDTV.setTypeface(regular);
                        shareDTV.setTypeface(regular);
                        lyricsDTV.setTypeface(regular);
                        loveDTV.setTypeface(regular);
                        durationDTV.setTypeface(regular);
                        playsTV.setTypeface(regular);

                        songDIV.setImageBitmap(SongList.get(pos).getSongImage());
                        songnameDTV.setText(SongList.get(pos).getTitle());
                        artistnameDTV.setText(SongList.get(pos).getArtist());
                        durationDTV.setText(SongList.get(pos).getDuration());
                        playsTV.setText(SongList.get(pos).getPlayCount()+" plays");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.show();
                    }
                    return true;
                }
            });
        }
    }

    public SongAdapter(List<Song> verticalList, Context context) {

        this.SongList = verticalList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_row_item, parent, false);

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
        FontChanger fontChanger = new FontChanger(font);
        fontChanger.replaceFonts((ViewGroup)itemView);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");


        if(SongList.get(position).isSong()) {

            holder.songLL.setVisibility(View.VISIBLE);
            holder.flexboxLayout.setVisibility(View.GONE);
            holder.SongName.setTypeface(font);
            holder.artistName.setTypeface(font);

            holder.SongName.setText(SongList.get(position).getTitle().trim());
            holder.artistName.setText(SongList.get(position).getArtist().toString().trim());

            if(position==playingpos){
                holder.musicIndicator.setVisibility(View.VISIBLE);
            }
            else{

                holder.musicIndicator.setVisibility(View.GONE);
            }
            try {

                holder.SongImage.setImageBitmap(SongList.get(position).getSongImage());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            holder.songLL.setVisibility(View.GONE);
            holder.flexboxLayout.setVisibility(View.VISIBLE);
            holder.totatSongsTV.setText(SongList.size()-1+" songs");
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