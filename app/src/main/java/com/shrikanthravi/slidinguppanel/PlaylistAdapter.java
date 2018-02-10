package com.shrikanthravi.slidinguppanel;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taishi.library.Indicator;

import java.util.List;

/**
 * Created by shrikanthravi on 10/01/18.
 */


public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.MyViewHolder> {
    private List<Playlist> PlaylistList;
    Context context;
    int selectedCount=0;
    int playingpos;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView playlistTV;
        public TextView totalSongsTV;
        public ImageView playlistIV;
        public RelativeLayout customRL;
        public TextView fmTV;

        public MyViewHolder(View view) {
            super(view);

            playlistTV = (TextView) view.findViewById(R.id.playlistTV);
            totalSongsTV = (TextView) view.findViewById(R.id.totalSongsTV);
            playlistIV = (ImageView) view.findViewById(R.id.playlistIV);
            customRL = (RelativeLayout) view.findViewById(R.id.customRL);
            fmTV = (TextView) view.findViewById(R.id.fmTV);


            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }


    }

    public PlaylistAdapter(List<Playlist> verticalList, Context context) {

        this.PlaylistList = verticalList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlist_row_item, parent, false);

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
        FontChanger fontChanger = new FontChanger(font);
        fontChanger.replaceFonts((ViewGroup)itemView);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
        Typeface bold = Typeface.createFromAsset(context.getAssets(), "fonts/product_sans_bold.ttf");

        holder.playlistTV.setText(PlaylistList.get(position).getPlayListName());
        holder.totalSongsTV.setText(PlaylistList.get(position).getNoofTracks());
        holder.fmTV.setTypeface(bold);
        holder.customRL.setVisibility(View.GONE);
        holder.playlistIV.setVisibility(View.GONE);

            try {
                if(position==0){
                    holder.playlistIV.setVisibility(View.VISIBLE);
                    holder.customRL.setVisibility(View.GONE);
                    holder.playlistIV.setImageDrawable(context.getDrawable(R.drawable.playlist1));
                    holder.playlistTV.setText("Create New Playlist");
                }
                else{
                    if(position==1){

                        holder.playlistIV.setVisibility(View.GONE);
                        holder.customRL.setVisibility(View.VISIBLE);
                        holder.fmTV.setText("Favourites Mix");
                        holder.playlistTV.setText("Favourites Mix");
                    }
                    else{
                        if(position==2){

                            holder.playlistIV.setVisibility(View.GONE);
                            holder.customRL.setVisibility(View.VISIBLE);
                            holder.fmTV.setText("Most Played Mix");
                            holder.playlistTV.setText("Most Played Mix");
                        }
                        else{

                            holder.playlistIV.setVisibility(View.VISIBLE);
                            holder.customRL.setVisibility(View.GONE);
                            holder.playlistTV.setText(PlaylistList.get(position).getPlayListName());
                            holder.totalSongsTV.setText(PlaylistList.get(position).getNoofTracks());
                            holder.playlistIV.setImageDrawable(context.getDrawable(R.drawable.create_playlist));}
                    }
                }


            } catch (Exception e) {
                System.out.println("error in playlistAdapter");
                e.printStackTrace();
            }




    }
    @Override
    public int getItemCount() {
        return PlaylistList.size();
    }

    public void setPlayingPlaylistPostion(int pos){
        playingpos=pos;
    }
}