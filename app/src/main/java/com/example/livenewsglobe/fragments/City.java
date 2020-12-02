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

        mainActivity= (MainActivity) getActivity();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);//this line is used to when keyboard comes up then dwidgets not disturb

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
            imgLoading.setVisibility(View.VISIBLE);

            Call<List<Cities>> call = interfaceApi.getCites(); //retrofit create implementation for this method


            call.enqueue(new Callback<List<Cities>>() {
                @Override
                public void onResponse(Call<List<Cities>> call, Response<List<Cities>> response) {
                    if (!response.isSuccessful()) {
                        imgLoading.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Code" + response.code(), Toast.LENGTH_SHORT).show();
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

                }
            });



    }

    public void getCitiesByStatess(final String stateName) {

        interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/salman/v2/");
        imgLoading.setVisibility(View.VISIBLE);

        Call<List<Cities>> call = interfaceApi.getCitesByStates(stateName); //retrofit create implementation for this method

        call.enqueue(new Callback<List<Cities>>() {
            @Override
            public void onResponse(Call<List<Cities>> call, Response<List<Cities>> response) {
                if(!response.isSuccessful())
                {
                    imgLoading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Code"+response.code(), Toast.LENGTH_SHORT).show();
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
//
//                     switch (cityName)
//                    {
//                        case "ABC":
////                            arrayList.remove(i);
////                            size = size-1;
//                            break;
//                        case "CBS":
////                            arrayList.remove(i);
////                            size = size-1;
//                            break;
//                        case "Fox":
////                            arrayList.remove(i);
////                            size = size-1;
//                            break;
//                        case "Independent":
////                            arrayList.remove(i);
////                            size = size-1;
//                            break;
//                        case "NBC":
////                            arrayList.remove(i);
////                            size = size-1;
//                            break;
//                    }


                }

                MainActivity mainActivity= (MainActivity) getActivity();

                if(mainActivity.gridStatus == true)
                {
                    recyclerViewGrid.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                    recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                    CityItem cityItem=new CityItem(getActivity(),arrayListCity,"grid");
                    recyclerViewGrid.setAdapter(cityItem);
                    // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
                    recyclerView.scheduleLayoutAnimation();
                    recyclerViewGrid.scheduleLayoutAnimation();
                    mainActivity.gridStatus = false;

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

            }
        });

    }
    public void getCitiesByStates(final String stateName) {

        imgLoading.setVisibility(View.VISIBLE);

        Call<List<Cities>> call = interfaceApi.getCites(); //retrofit create implementation for this method

        call.enqueue(new Callback<List<Cities>>() {
            @Override
            public void onResponse(Call<List<Cities>> call, Response<List<Cities>> response) {
                if(!response.isSuccessful())
                {
                    imgLoading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Code"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                imgLoading.setVisibility(View.GONE);
                arrayListCity = (ArrayList<Cities>) response.body();

                citiesAccordingToState=new ArrayList<>();
                switch (stateName)
                {
                    case "Arizona":
                        citiesAccordingToState.add(arrayListCity.get(28));
                        break;
                    case "California":
                        citiesAccordingToState.add(arrayListCity.get(32));
                        citiesAccordingToState.add(arrayListCity.get(23));
                        citiesAccordingToState.add(arrayListCity.get(33));
                        citiesAccordingToState.add(arrayListCity.get(26));
                        break;
                    case "Colorado":
                        citiesAccordingToState.add(arrayListCity.get(13));
                        break;
                    case "Florida":
                        citiesAccordingToState.add(arrayListCity.get(21));
                        citiesAccordingToState.add(arrayListCity.get(24));
                        citiesAccordingToState.add(arrayListCity.get(35));
                        citiesAccordingToState.add(arrayListCity.get(36));
                        citiesAccordingToState.add(arrayListCity.get(27));
                        break;
                    case "Georgia":
                        citiesAccordingToState.add(arrayListCity.get(1));
                        break;
                    case "Illinois":
                        citiesAccordingToState.add(arrayListCity.get(7));
                        citiesAccordingToState.add(arrayListCity.get(12));
                        break;
                    case "Massachusetts":
                        citiesAccordingToState.add(arrayListCity.get(4));
                        break;
                    case "Michigan":
                        citiesAccordingToState.add(arrayListCity.get(14));
                        break;
                    case "Missouri":
                        citiesAccordingToState.add(arrayListCity.get(9));
                        break;
                    case "Nevada":
                        citiesAccordingToState.add(arrayListCity.get(22));
                        break;
                    case "New Hampshire":
                        citiesAccordingToState.add(arrayListCity.get(4));
                        break;
                    case "Ohio":
                        citiesAccordingToState.add(arrayListCity.get(8));
                        citiesAccordingToState.add(arrayListCity.get(10));
                        break;
                    case "Oregon":
                        citiesAccordingToState.add(arrayListCity.get(29));
                        break;
                    case "Pennsylvania":
                        citiesAccordingToState.add(arrayListCity.get(18));
                        break;
                    case "South Carolina":
                        citiesAccordingToState.add(arrayListCity.get(6));
                        break;
                    case "Texas":
                        citiesAccordingToState.add(arrayListCity.get(2));
                        citiesAccordingToState.add(arrayListCity.get(11));
                        citiesAccordingToState.add(arrayListCity.get(15));
                        citiesAccordingToState.add(arrayListCity.get(19));
                        break;

                    case "Virginia":
                        citiesAccordingToState.add(arrayListCity.get(30));
                        break;
                    case "Washington":
                        citiesAccordingToState.add(arrayListCity.get(34));
                        citiesAccordingToState.add(arrayListCity.get(37));
                        break;

                }

                arrayListCity = citiesAccordingToState;
                MainActivity mainActivity= (MainActivity) getActivity();

                if(mainActivity.gridStatus == true)
                {
                    recyclerViewGrid.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                    recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                    CityItem cityItem=new CityItem(getActivity(),arrayListCity,"grid");
                    recyclerViewGrid.setAdapter(cityItem);
                    // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
                    recyclerView.scheduleLayoutAnimation();
                    recyclerViewGrid.scheduleLayoutAnimation();
                    mainActivity.gridStatus = false;

                }
                else
                {
                    recyclerViewGrid.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    CityItem cityItem=new CityItem(getActivity(),arrayListCity,"list");
                    recyclerView.setAdapter(cityItem);
                    recyclerView.scheduleLayoutAnimation();
                    recyclerViewGrid.scheduleLayoutAnimation();
                }


            }

            @Override
            public void onFailure(Call<List<Cities>> call, Throwable t) {

            }
        });
    }
}
