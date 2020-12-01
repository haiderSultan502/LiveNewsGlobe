package com.example.livenewsglobe.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewsglobe.MainActivity;
import com.example.livenewsglobe.R;
//import com.example.livenewsglobe.activties.ViewDragLayout;
import com.example.livenewsglobe.fragments.NewsVideoPlayer;
import com.example.livenewsglobe.models.FeaturedNetworks;
import com.example.livenewsglobe.otherClasses.CustomAlertDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsItem extends RecyclerView.Adapter<NewsItem.ItemViewHolder> implements Animation.AnimationListener{

    static View view,viewDragNewsPlayer;
    private MainActivity context;
    String showItem;
    boolean check=false;
    ArrayList<FeaturedNetworks> arrayListNetwork;

    static String content;
    static String imgUrl;

    CustomAlertDialog customAlertDialog;

//    String content;

    NewsVideoPlayer newsVideoPlayer=new NewsVideoPlayer();

//    ProgressDialog progressDialog=new ProgressDialog("Loading...");

    public NewsItem()
    {
    }
    public NewsItem(Context context, ArrayList<FeaturedNetworks> arrayListNetwork, String showItem)
    {
        this.context=(MainActivity) context;
        this.arrayListNetwork=arrayListNetwork;
        this.showItem=showItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        viewDragNewsPlayer=LayoutInflater.from(context).inflate(R.layout.news_video_player, parent, false);
        customAlertDialog=new CustomAlertDialog(context);

        if(showItem.equals("list"))
        {
            view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        }
        else if(showItem.equals("grid")) {
            view = LayoutInflater.from(context).inflate(R.layout.news_item_grid, parent, false);
        }
        if(showItem.equals("listFavourie"))
        {
            view = LayoutInflater.from(context).inflate(R.layout.favourite_item, parent, false);
        }
        else if(showItem.equals("gridFavourie")) {
            view = LayoutInflater.from(context).inflate(R.layout.favourite_item_grid, parent, false);
        }

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

//        holder.imageVideoPlayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(check==false)
//                {
//                    ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
//                            .replace(R.id.linear_layout, new NewsVideoPlayer())
//                            .commit();
//                    check=true;
//                }
//                else
//                {
////                    viewDragLayout = viewDragNewsPlayer.findViewById(R.id.dragLayout);
////                    viewDragLayout.maximize();
//                }
//
//
//
//            }
//        });



        holder.linearLayoutItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                bundle.putString("networkUrl",arrayListNetwork.get(position).getGuid());
                newsVideoPlayer.setArguments(bundle);

//                Toast.makeText(context,arrayListNetwork.get(position).getGuid(), Toast.LENGTH_SHORT).show();

                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_player, newsVideoPlayer).addToBackStack(null)
                        .commit();
//                progressDialog.progressDialogVar.dismiss();

            }
        });

        holder.textViewChannelName.setText(arrayListNetwork.get(position).getTitle());

        content = arrayListNetwork.get(position).getContent();
        imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("alt")-2);
//        imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("_logo")+9);
//        Log.e("checkT",imgUrl);
//        String url = "https://livenewsglobe.com/wp-content/uploads/2020/07/NBC_Los_Angeles_logo.png";
        Picasso.with(context).load(imgUrl).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.channelImage);

//        Toast.makeText(context, "String is  go  " + imgUrl, Toast.LENGTH_SHORT).show();
//        holder.channelImage.setImageResource(arrayListNetwork.get(position).getNetwokImage());
//        holder.imageVideoPlayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return arrayListNetwork.size();
    }

    @Override
    public void onAnimationStart(Animation animation) {
//        Toast.makeText(context, "animation start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
//        Toast.makeText(context, "animation end", Toast.LENGTH_SHORT).show();
//        if(showItem.equals("grid"))
//        {
//            view.findViewById(R.id.text_view_channel_name).setVisibility(View.VISIBLE);
////        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewChannelName,textViewChannelNameCome;
        ImageView channelImage,imageViewNetworksCome,imageVideoPlayButton;
        LinearLayout linearLayoutItemClick;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayoutItemClick=itemView.findViewById(R.id.complete_item_click);
            imageVideoPlayButton=itemView.findViewById(R.id.image_view_play_button);

            textViewChannelName=itemView.findViewById(R.id.text_view_channel_name);

            channelImage=itemView.findViewById(R.id.image_view_networks);
        }

    }
}
