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
import com.example.livenewsglobe.MainActivity;
import com.example.livenewsglobe.R;
import com.example.livenewsglobe.adapter.StateItem;
import com.example.livenewsglobe.models.States;
import com.example.livenewsglobe.otherClasses.RetrofitLab;
import com.example.livenewsglobe.otherClasses.SweetAlertDialogGeneral;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class State extends Fragment{

    View view;
    private RecyclerView recyclerView,recyclerViewGrid;
    static ArrayList<States> arrayListStates;
    static ArrayList<States> arrayListStates2;
    static InterfaceApi interfaceApi;
    ImageView imgLoading;
    static ArrayList<States> arrayListStateStore;
    static ArrayList<States> arrayListStateParam;
    String viewMode="null";
    String stateName,description;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;

    MainActivity mainActivity;


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

        mainActivity = (MainActivity) getActivity();
        arrayListStates = new ArrayList<>();
        arrayListStates2 = new ArrayList<>();
        interfaceApi = RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/Newspaper/v2/");
        arrayListStateStore = new ArrayList<>();
    }



    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.state,container,false);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(mainActivity);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);//this line is used to when keyboard comes up then dwidgets not disturb

        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerViewGrid=view.findViewById(R.id.recycler_view_gridView);

        imgLoading=view.findViewById(R.id.img_loading);
        Glide.with(getContext()).load(R.drawable.loading).into(imgLoading);


//        mainActivity.btnBack.setVisibility(View.GONE);

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
                    sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try again later");
                    return;
                }

                imgLoading.setVisibility(View.GONE);
                arrayListStates = (ArrayList<States>) response.body();

                int size = arrayListStates.size();

                for(int i = 0; i < size ; i++) {

                    stateName = arrayListStates.get(i).getName();
                    description = arrayListStates.get(i).getDescription();

                    if (description.length() < 50) {
                        continue;
                    } else {
                        arrayListStates2.add(arrayListStates.get(i));
                    }

//                    if (stateName.equals("Featured") || stateName.equals("Alabama")) {
//                     continue;
//                    } else {
//                        arrayListStates2.add(arrayListStates.get(i));
//                    }
                }

                mainActivity.storeStates=arrayListStates2;
                mainActivity.getStateList=true;

//                arrayListStates.remove(3);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                StateItem stateItem=new StateItem(getActivity(),arrayListStates2,"list");
                recyclerView.setAdapter(stateItem);
                recyclerView.scheduleLayoutAnimation();
                recyclerViewGrid.scheduleLayoutAnimation();

            }

            @Override
            public void onFailure(Call<List<States>> call, Throwable t) {
                imgLoading.setVisibility(View.GONE);
                sweetAlertDialogGeneral.showSweetAlertDialog("error","Please check your internet connection");
            }
        });


    }
    private void listView() {

        recyclerViewGrid.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StateItem stateItem = new StateItem  (getActivity(), arrayListStateParam, "list");
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
