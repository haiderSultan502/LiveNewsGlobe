package com.example.livenewsglobe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewsglobe.R;
import com.example.livenewsglobe.fragments.NewsVideoPlayer;
import com.example.livenewsglobe.models.Favourites;

import java.util.ArrayList;

public class FavouriteItem extends RecyclerView.Adapter<FavouriteItem.ItemViewHolder>{

    static View view;
    private Context context;
    String showItem;
    boolean check=false;
    ArrayList<Favourites> arrayListFavouriteNetwork;

    public FavouriteItem(Context context, ArrayList<Favourites> arrayListFavouriteNetwork, String showItem)
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
    public void onBindViewHolder(@NonNull final FavouriteItem.ItemViewHolder holder, int position) {

        holder.imageFavouriteNetwroks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check==false)
                {
                    ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .replace(R.id.frame_layout, new NewsVideoPlayer())
                            .commit();
                    check=true;
                }
                else
                {
//                    viewDragLayout = viewDragNewsPlayer.findViewById(R.id.dragLayout);
//                    viewDragLayout.maximize();
                }



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

        holder.textViewChannelName.setText(arrayListFavouriteNetwork.get(position).getNetworkName());
        holder.channelImage.setImageResource(arrayListFavouriteNetwork.get(position).getNetwokImage());
    }

    @Override
    public int getItemCount() {
        return arrayListFavouriteNetwork.size();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewChannelName;
        ImageView channelImage,imageFavouriteNetwroks;
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

