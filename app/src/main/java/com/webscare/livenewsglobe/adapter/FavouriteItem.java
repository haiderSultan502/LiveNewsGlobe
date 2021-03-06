package com.webscare.livenewsglobe.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.webscare.livenewsglobe.Interface.InterfaceApi;
import com.webscare.livenewsglobe.MainActivity;
import com.webscare.livenewsglobe.R;
import com.webscare.livenewsglobe.fragments.NewsVideoPlayer;
import com.webscare.livenewsglobe.models.FavouritesModel;
import com.webscare.livenewsglobe.models.InsertChannelResponse;
import com.webscare.livenewsglobe.otherClasses.RetrofitLab;
import com.webscare.livenewsglobe.otherClasses.SharedPrefereneceManager;
import com.webscare.livenewsglobe.otherClasses.SweetAlertDialogGeneral;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tyrantgit.explosionfield.ExplosionField;

public class FavouriteItem extends RecyclerView.Adapter<FavouriteItem.ItemViewHolder>{

    static View view;
    private MainActivity context;
    String showItem;
    boolean check=false;
    ArrayList<FavouritesModel> arrayListFavouriteNetwork;
    static String content;
    static String imgUrl;
    NewsVideoPlayer newsVideoPlayer=new NewsVideoPlayer();
    SweetAlertDialogGeneral sweetAlertDialogGeneral;
    ExplosionField mExplosionField;

    public FavouriteItem(Context context, ArrayList<FavouritesModel> arrayListFavouriteNetwork, String showItem)
    {
        this.context= (MainActivity) context;
        this.arrayListFavouriteNetwork=arrayListFavouriteNetwork;
        this.showItem=showItem;
    }

    @NonNull
    @Override
    public FavouriteItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mExplosionField = ExplosionField.attach2Window(context);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(context);
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
//        holder.imageFavouriteNetwroks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                Bundle bundle = new Bundle();
//                bundle.putString("networkUrl",arrayListFavouriteNetwork.get(position).getGuid());
//                newsVideoPlayer.setArguments(bundle);
//
////                Toast.makeText(context,arrayListNetwork.get(position).getGuid(), Toast.LENGTH_SHORT).show();
//
////                NewsVideoPlayer newsVideoPlayerObj=new NewsVideoPlayer();
////                newsVideoPlayerObj.captureLinkClick=false;
//                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame_layout_player, newsVideoPlayer).addToBackStack(null)
//                        .commit();
////                progressDialog.progressDialogVar.dismiss();
//
//            }
//        });


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
//            Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
            Picasso.with(context).load(R.drawable.ic_baseline_image_search_24).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.channelImage);
        }
        else
        {
//            String imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("jpg")+3);
//            String imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("alt")-2);
            imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("alt")-2);
            Picasso.with(context).load(imgUrl).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.channelImage);
        }
        holder.imageFavouriteNetwroks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SharedPrefereneceManager sharedPrefereneceManager = new SharedPrefereneceManager(context);

                InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/newspaper/v2/");
                Call<InsertChannelResponse> calls = interfaceApi.deleteFavouriteChannels(sharedPrefereneceManager.getUserId(),Integer.parseInt(arrayListFavouriteNetwork.get(position).getId())); //retrofit create implementation for this method
                calls.enqueue(new Callback<InsertChannelResponse>() {
                    @Override
                    public void onResponse(Call<InsertChannelResponse> call, Response<InsertChannelResponse> response) {
                        if(!response.isSuccessful())
                        {
//                                        Toast.makeText(mainActivity, "response not successfull ", Toast.LENGTH_SHORT).show();
                            sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try again later");
                        }
                        else
                        {
                            InsertChannelResponse insertChannelResponse = response.body();

//                            sweetAlertDialogGeneral.showSweetAlertDialog("success","Successfully remove from favourites");
                            holder.favImageSet.setBackgroundResource(R.drawable.favorite_icon);

//                            mExplosionField.explode(holder.linearLayoutItemClick);

                            arrayListFavouriteNetwork.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,arrayListFavouriteNetwork.size());
                            notifyDataSetChanged();

                            context.favStatus = true;
                            context.getFeaturedList = false;

//                                            Button btn = viewNewsItem.findViewById(R.id.heart);
//                                            btn.setBackground(mainActivity.getResources().getDrawable(R.drawable.like_channel));
                        }
                    }

                    @Override
                    public void onFailure(Call<InsertChannelResponse> call, Throwable t) {
                        sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListFavouriteNetwork.size();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewChannelName;
        ImageView channelImage,favImageSet;
        RelativeLayout imageFavouriteNetwroks;
        LinearLayout linearLayoutItemClick;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);


            linearLayoutItemClick=itemView.findViewById(R.id.complete_item_click);

            imageFavouriteNetwroks=itemView.findViewById(R.id.image_view_favourite_channel);

            textViewChannelName=itemView.findViewById(R.id.text_view_channel_name);

            channelImage=itemView.findViewById(R.id.image_view_networks);

            favImageSet=itemView.findViewById(R.id.image_view_favourite_channel_set);
        }


    }
}

