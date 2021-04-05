package com.webscare.livenewsglobe.adapter;

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

import com.webscare.livenewsglobe.MainActivity;
import com.webscare.livenewsglobe.R;
import com.webscare.livenewsglobe.fragments.City;
import com.webscare.livenewsglobe.models.States;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StateItem extends RecyclerView.Adapter<StateItem.ItemViewHolder> {

    static View view;
    private MainActivity context;
    String showItem;
    ArrayList<States> arrayListState;

    public StateItem(Context context, ArrayList<States> arrayListState, String showItem)
        {
            this.context=(MainActivity) context;
            this.arrayListState=arrayListState;
            this.showItem=showItem;
        }

    @NonNull
    @Override
    public StateItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(showItem.equals("list"))
        {
            view = LayoutInflater.from(context).inflate(R.layout.state_item, parent, false);
        }
        else if(showItem.equals("grid")) {
            view = LayoutInflater.from(context).inflate(R.layout.state_item_grid, parent, false);
        }

        return new StateItem.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateItem.ItemViewHolder holder, final int position) {

        holder.linearLayoutItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                context.btnBack.setVisibility(View.VISIBLE);

                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new City(arrayListState.get(position).getName())).addToBackStack(null)
                        .commit();
            }
        });

//        if(showItem.equals("grid"))
//        {
//            holder.textViewStateNameCome.setText(arrayListState.get(position).getStateName());
//            holder.stateImageCome.setImageResource(arrayListState.get(position).getStateImage());
//
//
//            Animation animation = AnimationUtils.loadAnimation(context,
//                    R.anim.move_letter_down_in_grid2);
//            holder.textViewStateName.setVisibility(View.INVISIBLE);
//            holder.textViewStateNameCome.setVisibility(View.VISIBLE);
//            holder.textViewStateNameCome.setAnimation(animation);
//            holder.textViewStateNameCome.setVisibility(View.INVISIBLE);
//            holder.textViewStateName.setVisibility(View.VISIBLE);
//
//            Animation animation2 = AnimationUtils.loadAnimation(context,
//                    R.anim.move_image_down_in_grid);
//            holder.stateImage.setVisibility(View.INVISIBLE);
//            holder.stateImageCome.setVisibility(View.VISIBLE);
//            holder.stateImageCome.setAnimation(animation2);
//            holder.stateImage.setVisibility(View.VISIBLE);
//            holder.stateImageCome.setVisibility(View.INVISIBLE);
//        }


        holder.textViewStateName.setText(arrayListState.get(position).getName());
        String content = arrayListState.get(position).getDescription();
        String imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("jpg")+3);
//        Toast.makeText(context, imgUrl, Toast.LENGTH_SHORT).show();
//        String url = "https://livenewsglobe.com/wp-content/uploads/2020/07/NBC_Los_Angeles_logo.png";
        Picasso.with(context).load(imgUrl).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.stateImage);
//        holder.stateImage.setImageResource(arrayListState.get(position).getStateImage());
    }

    @Override
    public int getItemCount() {
        return arrayListState.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewStateName,textViewStateNameCome;
        ImageView stateImage,stateImageCome;
        LinearLayout linearLayoutItemClick;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

//            if(showItem.equals("grid"))
//            {
//                textViewStateNameCome=itemView.findViewById(R.id.text_view_state_name_come);
//                stateImageCome=itemView.findViewById(R.id.image_view_state_come);
//            }
            linearLayoutItemClick=itemView.findViewById(R.id.complete_item_click_state);
            textViewStateName=itemView.findViewById(R.id.text_view_state_name);
            stateImage=itemView.findViewById(R.id.image_view_state);
        }

    }

    }
