package com.example.livenewsglobe.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livenewsglobe.Interface.InterfaceApi;
import com.example.livenewsglobe.R;
import com.example.livenewsglobe.adapter.StateItem;
import com.example.livenewsglobe.models.States;
import com.example.livenewsglobe.otherClasses.RetrofitLab;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class State extends Fragment{

    View view;
    private RecyclerView recyclerView,recyclerViewGrid;
    static ArrayList<States> arrayListStates;
    static InterfaceApi interfaceApi;
    ImageView imgLoading;
    static ArrayList<States> arrayListStateStore;
    static ArrayList<States> arrayListStateParam;
    String viewMode="null";


    public State()
    {
    }
    public State(String viewMode,ArrayList<States> arrayListState)
    {
        this.arrayListStateParam = new ArrayList<>();
        this.viewMode=viewMode;
        this.arrayListStateParam=arrayListState;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayListStates = new ArrayList<>();
        interfaceApi = RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/Newspaper/v2/");
        arrayListStateStore = new ArrayList<>();
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.state,container,false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);//this line is used to when keyboard comes up then dwidgets not disturb

        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerViewGrid=view.findViewById(R.id.recycler_view_gridView);

        imgLoading=view.findViewById(R.id.img_loading);
        Glide.with(getContext()).load(R.drawable.loading).into(imgLoading);


        recyclerViewGrid.setVisibility(View.INVISIBLE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        StateItem stateItem=new StateItem(getActivity(),arrayListStates,"list");
        recyclerView.setAdapter(stateItem);

        // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();


        if(viewMode.equals("grid"))
        {
            gridView();
        }
        else if(viewMode.equals("list"))
        {
            listView();
        }
        else
        {
            getStates();
        }

        return view;
    }


    public void getStates()
    {

        Call<List<States>> call = interfaceApi.getStates(); //retrofit create implementation for this method

        imgLoading.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<States>>() {
            @Override
            public void onResponse(Call<List<States>> call, Response<List<States>> response) {
                if(!response.isSuccessful())
                {
                    imgLoading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Code"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                imgLoading.setVisibility(View.GONE);
                arrayListStates = (ArrayList<States>) response.body();
                store(arrayListStates);

                arrayListStates.remove(3);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                StateItem stateItem=new StateItem(getActivity(),arrayListStates,"list");
                recyclerView.setAdapter(stateItem);
                recyclerView.scheduleLayoutAnimation();
                recyclerViewGrid.scheduleLayoutAnimation();
            }

            @Override
            public void onFailure(Call<List<States>> call, Throwable t) {
                imgLoading.setVisibility(View.GONE);
            }
        });


    }
    public void store(ArrayList<States> arrayListState) {
        arrayListStateStore=arrayListState;
    }
    public ArrayList<States> get() {
        return  arrayListStateStore;
    }
    private void listView() {

        recyclerViewGrid.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StateItem stateItem = new StateItem(getActivity(), arrayListStateParam, "list");
        recyclerView.setAdapter(stateItem);
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();
    }

    private void gridView() {
        recyclerViewGrid.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        StateItem stateItem = new StateItem(getActivity(), arrayListStateParam, "grid");
        recyclerViewGrid.setAdapter(stateItem);
        // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();
    }

}