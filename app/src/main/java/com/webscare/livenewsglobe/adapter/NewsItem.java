package com.webscare.livenewsglobe.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.webscare.livenewsglobe.Interface.InterfaceApi;
import com.webscare.livenewsglobe.MainActivity;
import com.webscare.livenewsglobe.R;
//import com.example.livenewsglobe.activties.ViewDragLayout;
import com.webscare.livenewsglobe.fragments.NewsVideoPlayer;
import com.webscare.livenewsglobe.models.FeaturedNetworks;
import com.webscare.livenewsglobe.models.InsertChannelResponse;
import com.webscare.livenewsglobe.otherClasses.CustomAlertDialog;
import com.webscare.livenewsglobe.otherClasses.RetrofitLab;
import com.webscare.livenewsglobe.otherClasses.SharedPrefereneceManager;
import com.webscare.livenewsglobe.otherClasses.SweetAlertDialogGeneral;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsItem extends RecyclerView.Adapter<NewsItem.ItemViewHolder> implements Animation.AnimationListener{

    static View view,viewDragNewsPlayer;
    private MainActivity context;
    String showItem;
    boolean check=false;
    ArrayList<FeaturedNetworks> arrayListNetwork;

    static String content;
    static String imgUrl;

    CustomAlertDialog customAlertDialog;

    SweetAlertDialogGeneral sweetAlertDialogGeneral;

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
        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(context);

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

        context.post_id = arrayListNetwork.get(position).getId();
        Log.d("post_id","post id " + context.post_id);

        holder.linearLayoutItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                bundle.putString("networkUrl",arrayListNetwork.get(position).getGuid());
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

        holder.textViewChannelName.setText(arrayListNetwork.get(position).getTitle());

        content = arrayListNetwork.get(position).getContent();

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

        int checkLikeStatus = arrayListNetwork.get(position).getMsg();
        if (checkLikeStatus == 1)
        {
            holder.btnHeart.setBackground(context.getResources().getDrawable(R.drawable.like_channel));
//            holder.imageVideoPlayButton.setImageDrawable(context.getResources().getDrawable(R.drawable.like_channel));
        }
        else
        {
//            holder.imageVideoPlayButton.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_icon));
            holder.btnHeart.setBackground(context.getResources().getDrawable(R.drawable.favorite_icon));
        }

        holder.btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavOrUnfav(position,holder);
            }
        });

//        imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("_logo")+9);
//        Log.e("checkT",imgUrl);
//        String url = "https://livenewsglobe.com/wp-content/uploads/2020/07/NBC_Los_Angeles_logo.png";
//        Picasso.with(context).load(imgUrl).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.channelImage);

//        Toast.makeText(context, "String is  go  " + imgUrl, Toast.LENGTH_SHORT).show();
//        holder.channelImage.setImageResource(arrayListNetwork.get(position).getNetwokImage());
//        holder.btnHeart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                customAlertDialog.showDialog();
//                final SharedPrefereneceManager sharedPrefereneceManager = new SharedPrefereneceManager(context);
////                            sharedPrefereneceManager.getLoginStatus();
//                if(sharedPrefereneceManager.getLoginStatus() == true)
//                {
//                    InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/newspaper/v2/");
//                    Call<InsertChannelResponse> call = interfaceApi.checkIsFavouriteOrNot(sharedPrefereneceManager.getUserId(),arrayListNetwork.get(position).getId()); //retrofit create implementation for this method
//                    call.enqueue(new Callback<InsertChannelResponse>() {
//                        @Override
//                        public void onResponse(Call<InsertChannelResponse> call, Response<InsertChannelResponse> response) {
//                            if(!response.isSuccessful())
//                            {
//                                Toast.makeText(context, "response not successfull ", Toast.LENGTH_SHORT).show();
//                            }
//                            else
//                            {
//                                InsertChannelResponse insertChannelResponse = response.body();
//                                int status = insertChannelResponse.getStatus();
//                                if(status==0)
//                                {
////                                    Log.d("check value", "values" +mainActivity.user_id + mainActivity.userEmail + mainActivity.post_id );
//                                    InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/newspaper/v2/");
//                                    Call<InsertChannelResponse> calls = interfaceApi.insertFavouriteChannels(sharedPrefereneceManager.getUserId(),sharedPrefereneceManager.getUserEmail(),arrayListNetwork.get(position).getId()); //retrofit create implementation for this method
//                                    calls.enqueue(new Callback<InsertChannelResponse>() {
//                                        @Override
//                                        public void onResponse(Call<InsertChannelResponse> call, Response<InsertChannelResponse> response) {
//                                            if(!response.isSuccessful())
//                                            {
//                                                Toast.makeText(context, "response not successfull ", Toast.LENGTH_SHORT).show();
//                                            }
//                                            else
//                                            {
//                                                InsertChannelResponse insertChannelResponse = response.body();
////                                                            int pos = position;
//
//                                                holder.btnHeart.setBackground(context.getResources().getDrawable(R.drawable.like_channel));
//
//
//                                                Toast.makeText(context, "Successfully added in favourite List ", Toast.LENGTH_SHORT).show();
////                                            Button btn = viewNewsItem.findViewById(R.id.heart);
////                                            btn.setBackground(mainActivity.getResources().getDrawable(R.drawable.like_channel));
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<InsertChannelResponse> call, Throwable t) {
//                                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                                else if(status==1)
//                                {
////                                    Log.d("check value", "values" +mainActivity.user_id + mainActivity.userEmail + mainActivity.post_id );
//                                    InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/newspaper/v2/");
//                                    Call<InsertChannelResponse> calls = interfaceApi.deleteFavouriteChannels(sharedPrefereneceManager.getUserId(),arrayListNetwork.get(position).getId()); //retrofit create implementation for this method
//                                    calls.enqueue(new Callback<InsertChannelResponse>() {
//                                        @Override
//                                        public void onResponse(Call<InsertChannelResponse> call, Response<InsertChannelResponse> response) {
//                                            if(!response.isSuccessful())
//                                            {
//                                                Toast.makeText(context, "response not successfull ", Toast.LENGTH_SHORT).show();
//                                            }
//                                            else
//                                            {
//                                                InsertChannelResponse insertChannelResponse = response.body();
//
//                                                holder.btnHeart.setBackground(context.getResources().getDrawable(R.drawable.like_channel));
//
//                                                Toast.makeText(context, "Successfully remove in favourite List ", Toast.LENGTH_SHORT).show();
////                                            Button btn = viewNewsItem.findViewById(R.id.heart);
////                                            btn.setBackground(mainActivity.getResources().getDrawable(R.drawable.like_channel));
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<InsertChannelResponse> call, Throwable t) {
//                                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<InsertChannelResponse> call, Throwable t) {
//                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//                }
//                else
//                {
//                    customAlertDialog.showDialog();
//                }
//            }
//        });
    }

    public void setFavOrUnfav(final int position, final ItemViewHolder holder) {
        try {
            final SharedPrefereneceManager sharedPrefereneceManager = new SharedPrefereneceManager(context);
//                            sharedPrefereneceManager.getLoginStatus();
            if(sharedPrefereneceManager.getLoginStatus() == true)
            {
//            Toast.makeText(mainActivity, "", Toast.LENGTH_SHORT).show();
                InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/newspaper/v2/");
                Call<InsertChannelResponse> call = interfaceApi.checkIsFavouriteOrNot(sharedPrefereneceManager.getUserId(),context.storeNetworks.get(position).getId()); //retrofit create implementation for this method
                call.enqueue(new Callback<InsertChannelResponse>() {
                    @Override
                    public void onResponse(Call<InsertChannelResponse> call, Response<InsertChannelResponse> response) {
                        if(!response.isSuccessful())
                        {
                            sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        }
                        else
                        {
                            context.favStatus = true;
                            context.getFeaturedList = false;
                            InsertChannelResponse insertChannelResponse = response.body();
                            int status = insertChannelResponse.getStatus();
                            if(status==0)
                            {
                                holder.btnHeart.setBackgroundResource(R.drawable.like_channel);

                                Log.d("check value", "values" +context.user_id + context.userEmail + context.post_id );
                                InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/newspaper/v2/");
                                Call<InsertChannelResponse> calls = interfaceApi.insertFavouriteChannels(sharedPrefereneceManager.getUserId(),sharedPrefereneceManager.getUserEmail(),context.storeNetworks.get(position).getId()); //retrofit create implementation for this method
                                calls.enqueue(new Callback<InsertChannelResponse>() {
                                    @Override
                                    public void onResponse(Call<InsertChannelResponse> call, Response<InsertChannelResponse> response) {
                                        if(!response.isSuccessful())
                                        {
                                            sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                                        }
                                        else
                                        {
                                            InsertChannelResponse insertChannelResponse = response.body();
                                            //  int pos = position;

//                                            holder.btnHeart.setBackgroundResource(R.drawable.like_channel);
//                                            sweetAlertDialogGeneral.showSweetAlertDialog("success","Successfully added in favourites");

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<InsertChannelResponse> call, Throwable t) {
//                                    Toast.makeText(mainActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else if(status==1)
                            {
                                holder.btnHeart.setBackgroundResource(R.drawable.favorite_icon);

                                InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/newspaper/v2/");
                                Call<InsertChannelResponse> calls = interfaceApi.deleteFavouriteChannels(sharedPrefereneceManager.getUserId(),context.storeNetworks.get(position).getId()); //retrofit create implementation for this method
                                calls.enqueue(new Callback<InsertChannelResponse>() {
                                    @Override
                                    public void onResponse(Call<InsertChannelResponse> call, Response<InsertChannelResponse> response) {
                                        if(!response.isSuccessful())
                                        {
//                                        Toast.makeText(mainActivity, "response not successfull ", Toast.LENGTH_SHORT).show();
                                            sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                                        }
                                        else
                                        {
                                            InsertChannelResponse insertChannelResponse = response.body();

//                                            holder.btnHeart.setBackgroundResource(R.drawable.like_channel);
//
//                                            sweetAlertDialogGeneral.showSweetAlertDialog("success","Successfully remove from favourites");

//                                        networks.remove(position);

//                                        recyclerView.notifyItemRemoved(position);
//                                        notifyItemRangeChanged(position,arrayListFavouriteNetwork.size());
//                                        notifyDataSetChanged();

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
                        }
                    }

                    @Override
                    public void onFailure(Call<InsertChannelResponse> call, Throwable t) {
                        sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
                    }

                });


            }
            else
            {
                customAlertDialog.showDialog();
            }
        }
        catch (Exception e)
        {
//            sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
        }


    }
    @Override
    public int getItemCount() {
        return arrayListNetwork.size();
    }

    public void setFav(int position)
    {

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
        ImageView channelImage,imageViewNetworksCome;
        LinearLayout linearLayoutItemClick;
        Button btnHeart;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayoutItemClick=itemView.findViewById(R.id.complete_item_click);

            textViewChannelName=itemView.findViewById(R.id.text_view_channel_name);

            channelImage=itemView.findViewById(R.id.image_view_networks);

            btnHeart = itemView.findViewById(R.id.heart);
        }

    }
}
