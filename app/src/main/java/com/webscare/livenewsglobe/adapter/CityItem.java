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
import com.webscare.livenewsglobe.fragments.Home;
import com.webscare.livenewsglobe.models.Cities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CityItem extends RecyclerView.Adapter<CityItem.ItemViewHolder> {

    static View view;
    private MainActivity context;
    String showItem;
    static String cityName;
    ArrayList<Cities> arrayListCity;

    public static int cityID=0;

//    String[] cityList = new String[33];

    public CityItem()
    {

    }
    public CityItem(Context context, ArrayList<Cities> arrayListCity, String showItem)
    {
        this.context=(MainActivity) context;
        this.arrayListCity=arrayListCity;
        this.showItem=showItem;
    }
//public CityItem(Context context, String[] cityList, String showItem)
//{
//    this.context=context;
//    this.cityList=cityList;
//    this.showItem=showItem;
//}

    @NonNull
    @Override
    public CityItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(showItem.equals("list"))
        {
            view = LayoutInflater.from(context).inflate(R.layout.city_item, parent, false);
        }
        else if(showItem.equals("grid")) {
            view = LayoutInflater.from(context).inflate(R.layout.city_item_grid, parent, false);
        }

        return new CityItem.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityItem.ItemViewHolder holder, final int position) {
        holder.linearLayoutItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cityID=arrayListCity.get(position).getTermId();
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame_layout, new Home(arrayListCity.get(position).getName(),"city")).addToBackStack(null)
                        .replace(R.id.frame_layout, new Home(arrayListCity.get(position).getTermId(),"city")).addToBackStack(null)
                        .commit();
            }
        });

//        if(showItem.equals("grid"))
//        {
//            holder.textViewCityNameCome.setText(arrayListCity.get(position).getCityName());
//            holder.cityImageCome.setImageResource(arrayListCity.get(position).getCityImage());
//
//            Animation animation = AnimationUtils.loadAnimation(context,R.anim.move_letter_down_in_grid2);
//            holder.textViewCityName.setVisibility(View.INVISIBLE);
//            holder.textViewCityNameCome.setVisibility(View.VISIBLE);
//            holder.textViewCityNameCome.setAnimation(animation);
//            holder.textViewCityNameCome.setVisibility(View.INVISIBLE);
//            holder.textViewCityName.setVisibility(View.VISIBLE);
//
//            Animation animation2 = AnimationUtils.loadAnimation(context,R.anim.move_image_down_in_grid);
//            holder.cityImage.setVisibility(View.INVISIBLE);
//            holder.cityImageCome.setVisibility(View.VISIBLE);
//            holder.cityImageCome.setAnimation(animation2);
//            holder.cityImage.setVisibility(View.VISIBLE);
//            holder.cityImageCome.setVisibility(View.INVISIBLE);
//        }


        String content = arrayListCity.get(position).getDescription();
        if(content.length() == 0)
        {
//            Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
            Picasso.with(context).load(R.drawable.ic_baseline_image_search_24).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.cityImage);
        }
        else
        {
//            String imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("jpg")+3);
        String imgUrl = content.substring(content.indexOf("src")+5,content.indexOf("alt")-2);
            Picasso.with(context).load(imgUrl).placeholder(R.drawable.ic_baseline_image_search_24).error(R.drawable.ic_baseline_image_search_24).into(holder.cityImage);
        }

        cityName=arrayListCity.get(position).getName();
        holder.textViewCityName.setText(cityName);
//        }






//        holder.textViewCityName.setText(cityList[position]);
//        holder.cityImage.setImageResource(arrayListCity.get(position).getCityImage());
    }

    @Override
    public int getItemCount() {
        return arrayListCity.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewCityName,textViewCityNameCome;
        ImageView cityImage,cityImageCome;
        LinearLayout linearLayoutItemClick;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

//            if (showItem.equals("grid"))
//            {
//                textViewCityNameCome=itemView.findViewById(R.id.text_view_city_name_come);
//                cityImageCome=itemView.findViewById(R.id.image_view_city_come);
//            }

            linearLayoutItemClick=itemView.findViewById(R.id.complete_item_click_city);
            textViewCityName=itemView.findViewById(R.id.text_view_city_name);
            cityImage=itemView.findViewById(R.id.image_view_city);
        }

    }

}

