package com.example.livenewsglobe.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewsglobe.R;
import com.example.livenewsglobe.fragments.NewsVideoPlayer;
import com.example.livenewsglobe.models.Favourites;
import com.example.livenewsglobe.models.FavouritesModel;
import com.example.livenewsglobe.otherClasses.CustomAlertDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavouriteItem extends RecyclerView.Adapter<FavouriteItem.ItemViewHolder>{

    static View view;
    private Context context;
    String showItem;
    boolean check=false;
    ArrayList<FavouritesModel> arrayListFavouriteNetwork;
    static String content;
    static String imgUrl;
    NewsVideoPlayer newsVideoPlayer=new NewsVideoPlayer();

    public FavouriteItem(Context context, ArrayList<FavouritesModel> arrayListFavouriteNetwork, String showItem)
    {
        this.context=context;
        this.arrayListFavouriteNetwork=arrayListFavouriteNetwork;
        this.showItem=showItem;
    }

    @NonNull
    @Override
    public FavouriteItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(showItem.equals("listFavourie"))
        {
            view = LayoutInflater.from(context).inflate(R.layout.favourite_item, parent, false);
        }
        else if(showItem.equals("gridFavourie")) {
            view = LayoutInflater.from(context).inflate(R.layout.favourite_item_grid, parent, false);
        }

        return new FavouriteItem.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavouriteItem.ItemViewHolder holder, final int position) {

        holder.linearLayoutItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("networkUrl",arrayListFavouriteNetwork.get(position).getGuid());
                newsVideoPlayer.setArguments(bundle);

//                Toast.makeText(context,arrayListNetwork.get(position).getGuid(), Toast.LENGTH_SHORT).show();

//                NewsVideoPlayer newsVideoPlayerObj=new NewsVideoPlayer();
//                newsVideoPlayerObj.captureLinkClick=false;
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_player, newsVideoPlayer).addToBackStack(null)
                        .commit();
//                progressDialog.progressDialogVar.dismiss();
            }
        });
        holder.imageFavouriteNetwroks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                bundle.putString("networkUrl",arrayListFavouriteNetwork.get(position).getGuid());
                newsVideoPlayer.setArguments(bundle);

//                Toast.makeText(context,arrayListNetwork.get(position).getGuid(), Toast.LENGTH_SHORT).show();

//                NewsVideoPlayer newsVideoPlayerObj=new NewsVideoPlayer();
//                newsVideoPlayerObj.captureLinkClick=false;
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_player, newsVideoPlayer).addToBackStack(null)
                        .commit();
//                progressDialog.progressDialogVar.dismiss();

            }
        });


//        holder.linearLayoutItemClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame_layout, new NewsVideoPlayer()).addToBackStack(null)
//                        .commit();
//
//            }
//        });

        holder.textViewChannelName.setText(arrayListFavouriteNetwork.get(position).getPostTitle());
        content = arrayListFavouriteNetwork.get(position).getPostContent();

        if(content.length() == 0)
        {
            Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
            Picasso.with(context).load(R.drawable.ic_baseline_image_search_24).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.channelImage);
        }
        else
        {
//            String imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("jpg")+3);
//            String imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("alt")-2);
            imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("alt")-2);
            Picasso.with(context).load(imgUrl).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.channelImage);
        }
    }

    @Override
    public int getItemCount() {
        return arrayListFavouriteNetwork.size();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewChannelName;
        ImageView channelImage,imageFavouriteNetwroks,imageFavouriteNetwroksLike;
        LinearLayout linearLayoutItemClick;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);


            linearLayoutItemClick=itemView.findViewById(R.id.complete_item_click);
            imageFavouriteNetwroks=itemView.findViewById(R.id.image_view_favourite_channel);

            textViewChannelName=itemView.findViewById(R.id.text_view_channel_name);

            channelImage=itemView.findViewById(R.id.image_view_networks);
        }


    }
}

