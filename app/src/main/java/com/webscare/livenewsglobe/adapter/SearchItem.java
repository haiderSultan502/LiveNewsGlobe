package com.webscare.livenewsglobe.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.webscare.livenewsglobe.R;
import com.webscare.livenewsglobe.fragments.NewsVideoPlayer;
import com.webscare.livenewsglobe.models.SearchNetwork;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchItem extends RecyclerView.Adapter<SearchItem.ItemViewHolder> {

    static View view;
    private Context context;
    String showItem;
    ArrayList<SearchNetwork> arrayListSearchNetwor;
    NewsVideoPlayer newsVideoPlayer=new NewsVideoPlayer();

    public SearchItem(Context context, ArrayList<SearchNetwork> arrayListSearchNetwor, String showItem)
    {
        this.context=context;
        this.arrayListSearchNetwor=arrayListSearchNetwor;
        this.showItem=showItem;
    }

    @NonNull
    @Override
    public SearchItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(showItem.equals("list"))
        {
            view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        }
        else if(showItem.equals("grid")) {
            view = LayoutInflater.from(context).inflate(R.layout.news_item_grid, parent, false);
        }

        return new SearchItem.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItem.ItemViewHolder holder, final int position) {


        holder.linearLayoutItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("networkUrl",arrayListSearchNetwor.get(position).getGuid());
                newsVideoPlayer.setArguments(bundle);

//                Toast.makeText(context,arrayListSearchNetwor.get(position).getGuid(), Toast.LENGTH_SHORT).show();

                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.linear_layout_webview, newsVideoPlayer).addToBackStack(null)
                        .commit();
            }
        });




        holder.textViewNetworkName.setText(arrayListSearchNetwor.get(position).getTitle());
        String content = arrayListSearchNetwor.get(position).getContent();
        String imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("alt")-2);
//        String imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("jpg")+3);
//        Toast.makeText(context, imgUrl, Toast.LENGTH_SHORT).show();
//        String url = "https://livenewsglobe.com/wp-content/uploads/2020/07/NBC_Los_Angeles_logo.png";
        Picasso.with(context).load(imgUrl).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.networkImage);
//        holder.stateImage.setImageResource(arrayListState.get(position).getStateImage());
    }

    @Override
    public int getItemCount() {
        return arrayListSearchNetwor.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewNetworkName,textViewStateNameCome;
        ImageView networkImage,stateImageCome;
        LinearLayout linearLayoutItemClick;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

//            if(showItem.equals("grid"))
//            {
//                textViewStateNameCome=itemView.findViewById(R.id.text_view_state_name_come);
//                stateImageCome=itemView.findViewById(R.id.image_view_state_come);
//            }
            linearLayoutItemClick=itemView.findViewById(R.id.complete_item_click);
            textViewNetworkName=itemView.findViewById(R.id.text_view_channel_name);
            networkImage=itemView.findViewById(R.id.image_view_networks);
        }

    }

}
