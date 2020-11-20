package com.example.livenewsglobe.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewsglobe.R;
import com.example.livenewsglobe.adapter.FavouriteItem;
import com.example.livenewsglobe.models.Favourites;

import java.util.ArrayList;

public class Favourite extends Fragment {

    View view;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private RecyclerView recyclerView,recyclerViewGrid;

    ArrayList<Favourites> arrayListFavourites;
    String viewMode="null";

    public Favourite()
    {

    }
    public Favourite(String viewMode)
    {
        this.viewMode=viewMode;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayListFavourites = new ArrayList<>();
        arrayListFavourites.add(new Favourites("KPTV", R.drawable.fox4));
        arrayListFavourites.add(new Favourites("KTTV",R.drawable.fox11));
        arrayListFavourites.add(new Favourites("KTBC",R.drawable.fox7));
        arrayListFavourites.add(new Favourites("KNTV",R.drawable.nbc));
        arrayListFavourites.add(new Favourites("KDFW",R.drawable.fox44));
        arrayListFavourites.add(new Favourites("WTTE",R.drawable.fox28));
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.favourite,container,false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);//this line is used to when keyboard comes up then dwidgets not disturb


        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerViewGrid=view.findViewById(R.id.recycler_view_gridView);


        recyclerViewGrid.setVisibility(View.INVISIBLE);

        if (viewMode.equals("grid"))
        {
            gridView();
        }
        else if (viewMode.equals("list"))
        {
            listView();
        }
        else
        {
            getFavourites();
        }



//        gridView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                checkVisibilityGrid=true;
//
//                if(checkVisibilityList==true)
//                {
//                    //apply animation
//                    animation = AnimationUtils.loadAnimation(getActivity(),
//                            R.anim.move_left);
//                    btnMoveBgColorGridBottomList.setVisibility(View.VISIBLE);
//                    btnMoveBgColorGridBottomList.setAnimation(animation);
//                    btnMoveBgColorGridBottomList.setVisibility(View.INVISIBLE);
//
//                    checkVisibilityList=false;
//                }
//
//                gridView.setBackgroundResource(R.drawable.on_grid);
//
//
//                listView.setBackgroundResource(R.drawable.on);
//
//                recyclerViewGrid.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.INVISIBLE);
////                recyclerViewGrid.setHasFixedSize(true);
//
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
//                recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
//
//                FavouriteItem favouriteItem=new FavouriteItem(getActivity(),arrayListFavourites,"gridFavourie");
//                recyclerViewGrid.setAdapter(favouriteItem);
//
//                // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
//                recyclerView.scheduleLayoutAnimation();
//                recyclerViewGrid.scheduleLayoutAnimation();
//
//
//            }
//        });
//
//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                checkVisibilityList=true;
//
//                if(checkVisibilityGrid==true)
//                {
//                    //apply animation
//                    animation = AnimationUtils.loadAnimation(getActivity(),
//                            R.anim.move_right);
//                    btnMoveBgColorListBottomGrid.setVisibility(View.VISIBLE);
//                    btnMoveBgColorListBottomGrid.setAnimation(animation);
//                    btnMoveBgColorListBottomGrid.setVisibility(View.INVISIBLE);
//
//                    checkVisibilityGrid=false;
//                }
//
//
//
//                listView.setBackgroundResource(R.drawable.on_list);
//                gridView.setBackgroundResource(R.drawable.grid);
//
//                recyclerViewGrid.setVisibility(View.INVISIBLE);
//                recyclerView.setVisibility(View.VISIBLE);
//
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                FavouriteItem favouriteItem=new FavouriteItem(getActivity(),arrayListFavourites,"listFavourie");
//                recyclerView.setAdapter(favouriteItem);
//
//                // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
//                recyclerView.scheduleLayoutAnimation();
//                recyclerViewGrid.scheduleLayoutAnimation();
//
//            }
//        });





        return view;
    }

    private void getFavourites()
    {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FavouriteItem favouriteItem=new FavouriteItem(getActivity(),arrayListFavourites,"listFavourie");
        recyclerView.setAdapter(favouriteItem);

        // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();
    }

    private void listView() {

        recyclerViewGrid.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FavouriteItem favouriteItem=new FavouriteItem(getActivity(),arrayListFavourites,"listFavourie");
        recyclerView.setAdapter(favouriteItem);
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();
    }

    private void gridView() {
        recyclerViewGrid.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        FavouriteItem favouriteItem=new FavouriteItem(getActivity(),arrayListFavourites,"gridFavourie");
        recyclerViewGrid.setAdapter(favouriteItem);
        // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();
    }
}

