package com.example.livenewsglobe.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livenewsglobe.Interface.InterfaceApi;
import com.example.livenewsglobe.MainActivity;
import com.example.livenewsglobe.R;
import com.example.livenewsglobe.adapter.CityItem;
import com.example.livenewsglobe.adapter.FavouriteItem;
import com.example.livenewsglobe.models.Cities;
import com.example.livenewsglobe.models.Favourites;
import com.example.livenewsglobe.models.FavouritesModel;
import com.example.livenewsglobe.models.InsertChannelResponse;
import com.example.livenewsglobe.otherClasses.RetrofitLab;
import com.example.livenewsglobe.otherClasses.SharedPrefereneceManager;
import com.example.livenewsglobe.otherClasses.SweetAlertDialogGeneral;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favourite extends Fragment {

    View view;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private RecyclerView recyclerView,recyclerViewGrid;

    ArrayList<FavouritesModel> arrayListFavourites;
    ArrayList<FavouritesModel> storeArrayListFavourites;
    String viewMode="null";
    MainActivity mainActivity;
    ImageView imgLoading;

    SweetAlertDialogGeneral sweetAlertDialogGeneral;

    public Favourite()
    {

    }
    public Favourite(String viewMode,ArrayList<FavouritesModel> storeArrayListFavourites)
    {
        this.viewMode=viewMode;
        this.storeArrayListFavourites = new ArrayList<>();
        this.storeArrayListFavourites = storeArrayListFavourites;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayListFavourites = new ArrayList<>();
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.favourite,container,false);

        mainActivity = (MainActivity) getActivity();

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(mainActivity);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);//this line is used to when keyboard comes up then dwidgets not disturb



        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerViewGrid=view.findViewById(R.id.recycler_view_gridView);


        recyclerViewGrid.setVisibility(View.INVISIBLE);

        imgLoading=view.findViewById(R.id.img_loading);

        Glide.with(getContext()).load(R.drawable.loading).into(imgLoading);

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

        return view;
    }


    private void getFavourites(){

        final SharedPrefereneceManager sharedPrefereneceManager = new SharedPrefereneceManager(mainActivity);
//                            sharedPrefereneceManager.getLoginStatus();
        if(sharedPrefereneceManager.getLoginStatus() == true)
        {
            imgLoading.setVisibility(View.VISIBLE);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            int id = mainActivity.user_id;
            InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/newspaper/v2/");
            Call<List<FavouritesModel>> call = interfaceApi.getFavouritesChannels(sharedPrefereneceManager.getUserId());
            call.enqueue(new Callback<List<FavouritesModel>>() {
                @Override
                public void onResponse(Call<List<FavouritesModel>> call, Response<List<FavouritesModel>> response) {
                    if(!response.isSuccessful())
                    {
                        imgLoading.setVisibility(View.GONE);
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try again later");
                        return;
                    }
                    else
                    {
                        imgLoading.setVisibility(View.GONE);
                        arrayListFavourites = (ArrayList<FavouritesModel>) response.body();
                        mainActivity.storeFavouriteNetworks = arrayListFavourites;
                        mainActivity.getFavouritList=true;
                        FavouriteItem favouriteItem=new FavouriteItem(getActivity(),arrayListFavourites,"listFavourie");
                        recyclerView.setAdapter(favouriteItem);
                        // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
                        recyclerView.scheduleLayoutAnimation();
                        recyclerViewGrid.scheduleLayoutAnimation();
                    }
                }

                @Override
                public void onFailure(Call<List<FavouritesModel>> call, Throwable t) {
                    imgLoading.setVisibility(View.GONE);
                    sweetAlertDialogGeneral.showSweetAlertDialog("error","Please check your internet connection");
                }
            });
        }
        else
        {
            sweetAlertDialogGeneral.showSweetAlertDialog("warning","First login please");
        }





    }

    private void listView() {

        recyclerViewGrid.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FavouriteItem favouriteItem=new FavouriteItem(getActivity(),storeArrayListFavourites,"listFavourie");
        recyclerView.setAdapter(favouriteItem);
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();
    }

    private void gridView() {
        recyclerViewGrid.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        FavouriteItem favouriteItem=new FavouriteItem(getActivity(),storeArrayListFavourites,"gridFavourie");
        recyclerViewGrid.setAdapter(favouriteItem);
        // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();
    }
}

