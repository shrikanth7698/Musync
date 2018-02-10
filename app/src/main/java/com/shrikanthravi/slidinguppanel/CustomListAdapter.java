package com.shrikanthravi.slidinguppanel;

/**
 * Created by shrikanthravi on 17/01/18.
 */


import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by adminlap on 8/6/17.
 */

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.MyViewHolder> {
    public List<Message> messagelist;
    public MyViewHolder newHolder;
    public int lastPosition = 0;
    public Context ctx;
    public CustomListAdapter adapter;
    public RecyclerView rv;
    public int lastpostion=0;
    public ArrayList<Message> msg;
    public int flag = 0;
    public String payload = "";
    public String veh = "";
    public String qtitle = "";
    public int pos;
    public String selectedDate;
    public String UserId = "";
    public double lat,lng;
    public int i;
    Date today, tomorrow, two_days, three_days;
    public String itemValue;
    public boolean flag1=true;
    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView lbody;
        public TextView rbody;
        public TextView ltime,rtime,lImageTime,rImageTime,leftImageCaption,rightImageCaption,lVideoTime,rVideoTime,leftVideoCaption,rightVideoCaption;
        public LinearLayout leftLayout,rightLayout,imagetextList,quickList,lImageContainer,rImageContainer,lVideoContainer,rVideoContainer;
        public HorizontalScrollView imagetextcontainer,quickcontainer;
        public ImageView lImage,rImage;
        public VideoView lVideo,rVideo;
        public ProgressBar lImagePg,rImagePg,lVideoPg,rVideoPg;
        public Context context;
        public MyViewHolder(View view) {
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.leftBody);
            rightLayout = (LinearLayout) view.findViewById(R.id.rightBody);
            lbody = (TextView) view.findViewById(R.id.leftText);
            rbody = (TextView) view.findViewById(R.id.rightText);
            ltime = (TextView) view.findViewById(R.id.leftTime);
            rtime = (TextView) view.findViewById(R.id.rightTime);
            imagetextList = (LinearLayout) view.findViewById(R.id.imagetextList);
            quickList = (LinearLayout) view.findViewById(R.id.quickList);
            quickcontainer = (HorizontalScrollView) view.findViewById(R.id.quickContainer);
            imagetextcontainer = (HorizontalScrollView) view.findViewById(R.id.imagetextContainer);
            lImageContainer = view.findViewById(R.id.leftImageContainer);
            rImageContainer = view.findViewById(R.id.rightImageContainer);
            lImage = view.findViewById(R.id.LeftImage);
            rImage = view.findViewById(R.id.RightImage);
            lImageTime = view.findViewById(R.id.LeftImageTime);
            rImageTime = view.findViewById(R.id.RightImageTime);
            lImagePg = view.findViewById(R.id.leftImageProgressbar);
            rImagePg = view.findViewById(R.id.rightImageProgressbar);
            leftImageCaption = view.findViewById(R.id.leftImageCaption);
            rightImageCaption = view.findViewById(R.id.rightImageCaption);
            lVideoTime = view.findViewById(R.id.LeftVideoTime);
            rVideoTime = view.findViewById(R.id.RightVideoTime);
            leftVideoCaption = view.findViewById(R.id.leftVideoCaption);
            rightVideoCaption = view.findViewById(R.id.righVideoCaption);
            lVideo = view.findViewById(R.id.leftVideoView);
            rVideo = view.findViewById(R.id.rightVideoView);
            lVideoContainer = view.findViewById(R.id.leftVideoContainer);
            rVideoContainer = view.findViewById(R.id.rightVideoContainer);
            lVideoPg = view.findViewById(R.id.leftVideoProgressBar);
            rVideoPg = view.findViewById(R.id.rightVideoProgressBar);
            context = view.getContext();
        }
    }

    public CustomListAdapter(List<Message> Message,Context context) {
        this.messagelist = Message;
        this.ctx = context;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Message c = messagelist.get(position);
        //Typeface muli = Typeface.createFromAsset(ctx.getAssets(),  "fonts/muli_regular.ttf");

        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/product_san_regular.ttf");

        switch (c.getType()) {
            case "left": {
                holder.lVideoContainer.setVisibility(View.GONE);
                holder.rVideoContainer.setVisibility(View.GONE);
                holder.rightLayout.setVisibility(View.GONE);
                holder.imagetextcontainer.setVisibility(View.GONE);
                holder.quickcontainer.setVisibility(View.GONE);
                holder.leftLayout.setVisibility(View.VISIBLE);
                holder.lbody.setText(c.getBody());
                holder.ltime.setText(c.getTime());
                holder.lbody.setTypeface(font);
                holder.ltime.setTypeface(font);
                break;
            }
            case "right": {
                holder.lVideoContainer.setVisibility(View.GONE);
                holder.rVideoContainer.setVisibility(View.GONE);
                holder.leftLayout.setVisibility(View.GONE);
                holder.rightLayout.setVisibility(View.VISIBLE);
                holder.imagetextcontainer.setVisibility(View.GONE);
                holder.quickcontainer.setVisibility(View.GONE);
                holder.rbody.setText(c.getBody());
                holder.rtime.setText(c.getTime());
                holder.rbody.setTypeface(font);
                holder.rtime.setTypeface(font);
                System.out.println("right time " + getTime());
                break;
            }
            case "imageT": {/*
                holder.lVideoContainer.setVisibility(View.GONE);
                holder.rVideoContainer.setVisibility(View.GONE);
                holder.leftLayout.setVisibility(View.GONE);
                holder.rightLayout.setVisibility(View.GONE);
                holder.imagetextcontainer.setVisibility(View.VISIBLE);
                holder.quickcontainer.setVisibility(View.GONE);


                for(int i=0;i<c.getImageT().size();i++) {

                    View child1, space;
                    LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    space = inflater.inflate(R.layout.space, null);
                    child1 = inflater.inflate(R.layout.image_text, null);
                    ImageView image1 = child1.findViewById(R.id.imagett);
                    TextView text1 = child1.findViewById(R.id.imagetext);
                    Picasso.with(holder.context).load(c.getImageT().get(i).getUrl()).into(image1);
                    text1.setTypeface(muli);
                    text1.setText(c.getImageT().get(i).getText());
                    holder.imagetextList.addView(child1);
                    holder.imagetextList.addView(space);
                }*/


                break;
            }
            case "quick":{
/*
                holder.lVideoContainer.setVisibility(View.GONE);
                holder.rVideoContainer.setVisibility(View.GONE);
                holder.rightLayout.setVisibility(View.GONE);
                holder.imagetextcontainer.setVisibility(View.GONE);
                holder.leftLayout.setVisibility(View.GONE);
                holder.quickcontainer.setVisibility(View.VISIBLE);
                for(int i=0;i<c.getQuick().size();i++) {

                    View child1, space;
                    LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    space = inflater.inflate(R.layout.space1, null);
                    child1 = inflater.inflate(R.layout.quick, null);
                    TextView text1 = child1.findViewById(R.id.quickText);
                    text1.setTypeface(muli);
                    text1.setText(c.getQuick().get(i).getBody());
                    holder.quickList.addView(child1);
                    holder.quickList.addView(space);
                }*/
                break;
            }
            case "lImage":{

                /*holder.lVideoContainer.setVisibility(View.GONE);
                holder.rVideoContainer.setVisibility(View.GONE);
                holder.rightLayout.setVisibility(View.GONE);
                holder.imagetextcontainer.setVisibility(View.GONE);
                holder.leftLayout.setVisibility(View.GONE);
                holder.quickcontainer.setVisibility(View.GONE);
                holder.lImageContainer.setVisibility(View.VISIBLE);
                holder.rImageContainer.setVisibility(View.GONE);
                holder.lImagePg.setVisibility(View.VISIBLE);
                holder.lImageTime.setText(c.getTime());
                holder.leftImageCaption.setText(c.getImage().get(0).getCaption());
                holder.leftImageCaption.setTypeface(muli);
                holder.lImageTime.setTypeface(muli);
                Picasso.with(holder.context).load(c.getImage().get(0).getUrl()).into(holder.lImage, new Callback(){
                    @Override
                    public void onSuccess() {
                        holder.lImagePg.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
                holder.lImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });*/
                break;
            }

            case "rImage":{
/*
                holder.lVideoContainer.setVisibility(View.GONE);
                holder.rVideoContainer.setVisibility(View.GONE);
                holder.rightLayout.setVisibility(View.GONE);
                holder.imagetextcontainer.setVisibility(View.GONE);
                holder.leftLayout.setVisibility(View.GONE);
                holder.quickcontainer.setVisibility(View.GONE);
                holder.lImageContainer.setVisibility(View.GONE);
                holder.rImageContainer.setVisibility(View.VISIBLE);
                holder.rImagePg.setVisibility(View.VISIBLE);
                holder.rImageTime.setText(c.getTime());
                holder.rightImageCaption.setText(c.getImage().get(0).getCaption());
                holder.rightImageCaption.setTypeface(muli);
                holder.rImageTime.setTypeface(muli);
                Picasso.with(holder.context).load(c.getImage().get(0).getUrl()).into(holder.rImage, new Callback(){
                    @Override
                    public void onSuccess() {
                        holder.rImagePg.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
                holder.rImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });*/
                break;
            }

            case "lVideo":{/*
                holder.rVideoContainer.setVisibility(View.GONE);
                holder.rightLayout.setVisibility(View.GONE);
                holder.imagetextcontainer.setVisibility(View.GONE);
                holder.leftLayout.setVisibility(View.GONE);
                holder.quickcontainer.setVisibility(View.GONE);
                holder.lImageContainer.setVisibility(View.GONE);
                holder.rImageContainer.setVisibility(View.GONE);
                holder.lVideoContainer.setVisibility(View.VISIBLE);
                holder.lVideoPg.setVisibility(View.GONE);
                try {

                    holder.lVideo.setVisibility(View.VISIBLE);
                    Uri uri = Uri.parse("android.resource://"+holder.context.getPackageName()+"/"+R.raw.iphone10);
                    holder.lVideo.setVideoURI(uri);
                    holder.lVideo.start();
                    System.out.println("inside");
                } catch (Exception e) {
                    //handle error
                    System.out.println("video error "+e.getMessage());
                }*/
                break;
            }
        }



    }




    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_row,parent, false);
/*
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/product_san_regular.ttf");
        FontChanger fontChanger = new FontChanger(font);
        fontChanger.replaceFonts((ViewGroup)v);*/
        return new MyViewHolder(v);
    }


    public String getTime(){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String time = mdformat.format(calendar.getTime());
        return time;
    }

}
