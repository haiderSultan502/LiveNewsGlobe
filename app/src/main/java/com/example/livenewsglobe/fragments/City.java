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
import com.example.livenewsglobe.adapter.CityItem;
import com.example.livenewsglobe.models.Cities;
import com.example.livenewsglobe.otherClasses.RetrofitLab;
import com.example.livenewsglobe.otherClasses.SweetAlertDialogGeneral;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class City extends Fragment {

    MainActivity mainActivity;

    View view;
    private RecyclerView recyclerView,recyclerViewGrid;
    ImageView imgLoading;

    static ArrayList<Cities> arrayListCity;
    static ArrayList<Cities> arrayListCityStore;
    static ArrayList<Cities> arrayListStoreParam;
    ArrayList<Cities> citiesAccordingToState;

    ArrayList<Cities> cityList;


    static InterfaceApi interfaceApi,interfaceApii;
    String checKCityOrState;
    String cityName;

    SweetAlertDialogGeneral sweetAlertDialogGeneral;

    public City()
    {

    }
    public City(String checKCityOrState)
    {
        this.checKCityOrState=checKCityOrState;
    }

    public City(String checKCityOrState,ArrayList<Cities> arrayListCity)
    {
        this.arrayListStoreParam = new ArrayList<>();
        this.checKCityOrState=checKCityOrState;
        this.arrayListStoreParam=arrayListCity;
//        Toast.makeText(getActivity(), "size " + arrayListStoreParam.size(), Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayListCity = new ArrayList<>();
        arrayListCityStore = new ArrayList<>();
        citiesAccordingToState = new ArrayList<>();

        cityList=new ArrayList<>();

        interfaceApi = RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/Newspaper/v2/");


    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.city,container,false);

        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(mainActivity);

        mainActivity= (MainActivity) getActivity();

//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);//this line is used to when keyboard comes up then dwidgets not disturb

        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerViewGrid=view.findViewById(R.id.recycler_view_gridView);

        imgLoading=view.findViewById(R.id.img_loading);


        Glide.with(getContext()).load(R.drawable.loading).into(imgLoading);

        if (checKCityOrState.equals("allCities"))
        {
            getAllCities("allCities");
        }
        else if (checKCityOrState.equals("grid"))
        {
            gridView();
        }
        else if (checKCityOrState.equals("list"))
        {
            listView();
        }
        else
        {
            getCitiesByStatess(checKCityOrState);
        }


        return view;
    }

    private void listView() {

        recyclerViewGrid.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CityItem cityItem = new CityItem(getActivity(), arrayListStoreParam, "list");
        recyclerView.setAdapter(cityItem);
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();
    }

    private void gridView() {
        recyclerViewGrid.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        CityItem cityItem = new CityItem(getActivity(), arrayListStoreParam, "grid");
        recyclerViewGrid.setAdapter(cityItem);
        // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();
    }

    public void  getAllCities(final String checkParam)
    {
        try {
            imgLoading.setVisibility(View.VISIBLE);

            Call<List<Cities>> call = interfaceApi.getCites(); //retrofit create implementation for this method


            call.enqueue(new Callback<List<Cities>>() {
                @Override
                public void onResponse(Call<List<Cities>> call, Response<List<Cities>> response) {
                    if (!response.isSuccessful()) {
                        imgLoading.setVisibility(View.GONE);
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }

                    imgLoading.setVisibility(View.GONE);
                    arrayListCity = (ArrayList<Cities>) response.body();

                    arrayListCity.remove(0);
                    arrayListCity.remove(4);
                    arrayListCity.remove(14);
                    arrayListCity.remove(17);
                    arrayListCity.remove(21);


                    mainActivity.storeCities=arrayListCity;
                    mainActivity.getCityList=true;

                    recyclerViewGrid.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    CityItem cityItem = new CityItem(getActivity(), arrayListCity, "list");
                    recyclerView.setAdapter(cityItem);
                    recyclerView.scheduleLayoutAnimation();
                    recyclerViewGrid.scheduleLayoutAnimation();


                }

                @Override
                public void onFailure(Call<List<Cities>> call, Throwable t) {
                    imgLoading.setVisibility(View.GONE);
                    sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
                }
            });
        }
        catch (Exception e)
        {
//            sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
        }





    }

    public void getCitiesByStatess(final String stateName) {
        try {
            interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/salman/v2/");
            imgLoading.setVisibility(View.VISIBLE);

            Call<List<Cities>> call = interfaceApi.getCitesByStates(stateName); //retrofit create implementation for this method

            call.enqueue(new Callback<List<Cities>>() {
                @Override
                public void onResponse(Call<List<Cities>> call, Response<List<Cities>> response) {
                    if(!response.isSuccessful())
                    {
                        imgLoading.setVisibility(View.GONE);
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning","Please try later");
                        return;
                    }

                    imgLoading.setVisibility(View.GONE);

                    arrayListCity = (ArrayList<Cities>) response.body();

                    HashSet<Cities> hashSet = new HashSet<>(arrayListCity);

                    ArrayList<Cities> arrayList = new ArrayList<>(hashSet);

                    int size = arrayList.size();

                    for(int i = 0; i < size ; i++)
                    {

                        cityName = arrayList.get(i).getName();

                        if (cityName.equals("ABC") || cityName.equals("CBS") || cityName.equals("Fox") || cityName.equals("Independent") || cityName.equals("NBC"))
                        {
//                     break;
                        }
                        else
                        {
                            cityList.add(arrayList.get(i));
                        }

                    }

                    mainActivity.spinnerArrayListCity=cityList;

                    mainActivity.setCitySpinnerListAdapetr();

                    MainActivity mainActivity= (MainActivity) getActivity();

                    if(mainActivity.gridStatus == true)
                    {
                        recyclerViewGrid.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                        recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                        CityItem cityItem=new CityItem(getActivity(),cityList,"grid");
                        recyclerViewGrid.setAdapter(cityItem);
                        // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
                        recyclerView.scheduleLayoutAnimation();
                        recyclerViewGrid.scheduleLayoutAnimation();

                    }
                    else
                    {
                        recyclerViewGrid.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        CityItem cityItem=new CityItem(getActivity(),cityList,"list");
                        recyclerView.setAdapter(cityItem);
                        recyclerView.scheduleLayoutAnimation();
                        recyclerViewGrid.scheduleLayoutAnimation();
                    }


                }

                @Override
                public void onFailure(Call<List<Cities>> call, Throwable t) {
                    imgLoading.setVisibility(View.GONE);
                    sweetAlertDialogGeneral.showSweetAlertDialog("error",t.getMessage());
                }
            });
        }
        catch (Exception e)
        {
            sweetAlertDialogGeneral.showSweetAlertDialog("warning",e.getMessage());
        }




    }
}
