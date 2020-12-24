package com.example.livenewsglobe.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livenewsglobe.Interface.InterfaceApi;
import com.example.livenewsglobe.MainActivity;
import com.example.livenewsglobe.R;
import com.example.livenewsglobe.adapter.CityItem;
import com.example.livenewsglobe.adapter.FavouriteItem;
import com.example.livenewsglobe.adapter.NewsItem;
import com.example.livenewsglobe.adapter.SearchItem;
import com.example.livenewsglobe.adapter.StateItem;
import com.example.livenewsglobe.models.Cities;
import com.example.livenewsglobe.models.FavouritesModel;
import com.example.livenewsglobe.models.FeaturedNetworks;
import com.example.livenewsglobe.models.InsertChannelResponse;
import com.example.livenewsglobe.models.ProgressDialog;
import com.example.livenewsglobe.models.SearchNetwork;
import com.example.livenewsglobe.models.States;
import com.example.livenewsglobe.otherClasses.CustomAlertDialog;
import com.example.livenewsglobe.otherClasses.GetStateCityNetwork;
import com.example.livenewsglobe.otherClasses.RecyclerTouchListener;
import com.example.livenewsglobe.otherClasses.RetrofitLab;
import com.example.livenewsglobe.otherClasses.SharedPrefereneceManager;
import com.example.livenewsglobe.otherClasses.SweetAlertDialogGeneral;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class Home extends Fragment {

    View view,viewNewsItem;
    private RecyclerView recyclerView,recyclerViewGrid;
    RecyclerTouchListener recyclerTouchListener;
    ImageView imgLoading;
    Boolean isScrooling=false;
    LinearLayout textViewShowTip;

    InterfaceApi interfaceApiGetFeaturedNetworks;
    Call<List<FeaturedNetworks>> callGetFeaturedNetworks;

    MainActivity mainActivity;
    CustomAlertDialog customAlertDialog;
    CityItem cityItem;

    Call<List<FeaturedNetworks>> call;
    Call<List<SearchNetwork>> callNetworks;
    Call<List<FeaturedNetworks>> callForRelatedNetwork;


    static ArrayList<FeaturedNetworks> networks;
    static ArrayList<SearchNetwork> searchNetworks;
    static ArrayList<Cities> arrayListCity;
    ArrayList<Cities> arrayListCity2;
    ArrayList<Cities> citiesAccordingToState;
    ArrayList<Integer> arrayList;

    ArrayList<States> statesList;

    ArrayList<FeaturedNetworks> arrayListFeaturedNetwork;
    public static ArrayList<FeaturedNetworks> arrayListFeaturedStore;
    String vieMode="null";

    static String  arrrayState[] = new String[19];
    static String  arrrayCity[] = new String[34];

    Boolean checkstatus=false, checkGridStatus=false,checkListStatus=false, checkStatusCitySpinner=false,searchStatus=false;
    Boolean citySpinnner=false,networkSpinner=false;
    boolean visibilityOfTip;

    int currentItems,totalItems,scrollOutItems;

    String checkNetworkOrCity;
    int checkNetworkOrCityy;
    String paramCheck="null";
    String cityName,mainNetworkName;

    public Home(String viewMode,ArrayList<FeaturedNetworks> arrayListFeaturedNetwork)
    {
        this.arrayListFeaturedNetwork = new ArrayList<>();
        this.vieMode=viewMode;
        this.arrayListFeaturedNetwork=arrayListFeaturedNetwork;
    }

    public Home(String checkNetworkOrCity,String paramCheck)
    {
        this.checkNetworkOrCity=checkNetworkOrCity;
        this.paramCheck=paramCheck;
    }
    public Home(int checkNetworkOrCity,String paramCheck)
    {
        this.checkNetworkOrCityy=checkNetworkOrCity;
        this.paramCheck=paramCheck;
    }

    public Home()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Toast.makeText(getActivity(), "On create call", Toast.LENGTH_SHORT).show();



        arrayListFeaturedStore = new ArrayList<>();
        networks = new ArrayList<>();

        arrayListCity = new ArrayList<>();
        arrayListCity2 = new ArrayList<>();
        citiesAccordingToState = new ArrayList<>();
        arrayList = new ArrayList<>();

        statesList = new ArrayList<>();


    }



    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        Toast.makeText(getActivity(), "onCreateView call()", Toast.LENGTH_SHORT).show();
        view=inflater.inflate(R.layout.home,container,false);
        viewNewsItem = inflater.inflate(R.layout.news_item,container,false);

        Context context;
        mainActivity = (MainActivity) getActivity();
        customAlertDialog=new CustomAlertDialog(getActivity());
        cityItem=new CityItem();


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);//this line is used to when keyboard comes up then dwidgets not disturb


        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerViewGrid=view.findViewById(R.id.recycler_view_gridView);


        imgLoading=view.findViewById(R.id.img_loading);
        Glide.with(getContext()).load(R.drawable.loading).into(imgLoading);

        textViewShowTip=view.findViewById(R.id.text_tip_show);
        textViewShowTip.setVisibility(View.GONE);






        // the sharedPreference code for permanently remove tip by clicking on tip the TIP is  add the channel in favouirte
//        Context is an abstract class used to get global information in Android Application resources like strings, values, drawables, assets etc
//        getSharedPreferences(String name,int mode) : function return SharedPreferences object to deal with save and get values from preference file.
//        SharedPreferences is a singleton class means this class will only have a single object throughout the application lifecycle
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE );//Context.MODE_PRIVATE – default value (Not accessible outside of your application)
        visibilityOfTip =prefs.getBoolean("visibilityOfTip", true);  //to read the data

            textViewShowTip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textViewShowTip.setVisibility(View.GONE);

                    SharedPreferences prefs = getActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE );
                    SharedPreferences.Editor editor = prefs.edit(); //to save the save the data
                    editor.putBoolean("visibilityOfTip",false);
                    editor.apply();
                    visibilityOfTip=false;
                }
            });


        recyclerViewGrid.setVisibility(View.INVISIBLE);


        if(paramCheck.equals("city"))
        {

            getRelateableChannels(checkNetworkOrCityy);

        }
        else if(paramCheck.equals("networkname"))
        {

            getRelatedNetworks(checkNetworkOrCity);
        }
        else if(paramCheck.equals("search"))
        {
            searchNetworks(checkNetworkOrCity);
        }
        else if(paramCheck.equals("featuredNetworks"))
        {

            getFeaturedNetworks();

        }
        else if(vieMode.equals("grid"))
        {
            gridView();
        }
        else if(vieMode.equals("list"))
        {
            listView();
        }

        checkListStatus=true;
//         for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();


        return view;
    }


    public void getFeaturedNetworks() {

        SweetAlertDialogGeneral sweetAlertDialogGeneral = new SweetAlertDialogGeneral(mainActivity);
        sweetAlertDialogGeneral.showSweetAlertDialog("success","try later");
//        SweetAlertDialog pDialogs = new SweetAlertDialog(mainActivity,SweetAlertDialog.ERROR_TYPE);
//        pDialogs.setTitleText("Successfully Login");
//        pDialogs.setCustomImage(R.drawable.loading);
//        pDialogs.setCancelable(true);
//        pDialogs.setCanceledOnTouchOutside(true);
//        pDialogs.show();

    imgLoading.setVisibility(View.VISIBLE);


        final SharedPrefereneceManager sharedPrefereneceManager = new SharedPrefereneceManager(mainActivity);
//                            sharedPrefereneceManager.getLoginStatus();
        interfaceApiGetFeaturedNetworks= RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/Newspaper/v2/");
        if(sharedPrefereneceManager.getLoginStatus() == true)
        {
            callGetFeaturedNetworks = interfaceApiGetFeaturedNetworks.getNetworks(sharedPrefereneceManager.getUserId()); //retrofit create implementation for this method
        }
        else
        {
            callGetFeaturedNetworks = interfaceApiGetFeaturedNetworks.getNetworks(); //retrofit create implementation for this method
        }

        callGetFeaturedNetworks.enqueue(new Callback<List<FeaturedNetworks>>() {
        @Override
        public void onResponse(Call<List<FeaturedNetworks>> call, Response<List<FeaturedNetworks>> response) {
            if(!response.isSuccessful())
            {
//                progressDialog.progressDialogVar.dismiss();
                imgLoading.setVisibility(View.GONE);
//                Toast.makeText(getActivity(), "Code"+response.code(), Toast.LENGTH_SHORT).show();
                return;
            }

//            progressDialog.progressDialogVar.dismiss();
            imgLoading.setVisibility(View.GONE);
            networks = (ArrayList<FeaturedNetworks>) response.body();

//            recyclerView.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.heart).setBackground(mainActivity.getResources().getDrawable(R.drawable.like_channel));
//            recyclerView.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.heart).setBackground(mainActivity.getResources().getDrawable(R.drawable.like_channel));

            mainActivity.storeNetworks=networks;
            mainActivity.getFeaturedList=true;

            recyclerView.setHasFixedSize(true);
            final Context context;
            final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);



            final NewsItem newsItem=new NewsItem(getActivity(), networks,"list");
//            newsItem.notifyDataSetChanged();
            recyclerView.setAdapter(newsItem);


//            int h = arrayList.get(0);
//            for (int i = 0 ; i < arrayList.size(); i++)
//            {

//                recyclerView.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.heart).setBackground(mainActivity.getResources().getDrawable(R.drawable.like_channel));
//            }
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                //this method call when scrooling start
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    {
                        isScrooling=true;
                    }
                }

                @Override
                //this method call when scrooled done
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    currentItems=linearLayoutManager.getChildCount();//get the count of currrently visible items in recycler view
                    totalItems=linearLayoutManager.getItemCount(); // get the count of total items in recycler view
                    scrollOutItems=linearLayoutManager.findFirstVisibleItemPosition(); // get the count of items which has been scrooled out in recycler view

                    if(isScrooling && (currentItems+scrollOutItems  ==  totalItems))
                    {
                        isScrooling=false;
                        if(visibilityOfTip)
                        {
                            textViewShowTip.setVisibility(View.VISIBLE);
                        }

                    }
                    else if(isScrooling && (currentItems+scrollOutItems  !=  totalItems))
                    {
                        textViewShowTip.setVisibility(View.GONE);
                    }
                }
            });

            // the below code is for swipe at recycler view item
            recyclerTouchListener = new RecyclerTouchListener(getActivity(), recyclerView);

            recyclerTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
                @Override
                public void onRowClicked(int position) {
                    Toast.makeText(getActivity(), networks.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onIndependentViewClicked(int independentViewID, int position) {
                    Toast.makeText(getActivity(), "ok" + networks.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }
            }).setSwipeOptionViews(R.id.favourite_network).setSwipeable(R.id.complete_item_click, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onSwipeOptionClicked(int viewID, final int position) {
                    switch (viewID) {
                        case R.id.favourite_network:
                            setFavOrUnfav(position);
                    }
                }
            });
            recyclerView.addOnItemTouchListener(recyclerTouchListener);

            recyclerView.scheduleLayoutAnimation();
            recyclerViewGrid.scheduleLayoutAnimation();
        }

        @Override
        public void onFailure(Call<List<FeaturedNetworks>> call, Throwable t) {
            imgLoading.setVisibility(View.GONE);
            Log.d("CheckMessage",t.getMessage());
        }
    });
}
    public void getRelatedNetworks(final String network) {

        imgLoading.setVisibility(View.VISIBLE);


        InterfaceApi interfaceApi = RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/wp/v2/");
        callForRelatedNetwork = interfaceApi.getChannelsAccordingToMianNetworks(mainActivity.cityId,mainActivity.networkId); //retrofit create implementation for this method

        callForRelatedNetwork.enqueue(new Callback<List<FeaturedNetworks>>() {
            @Override
            public void onResponse(Call<List<FeaturedNetworks>> call, Response<List<FeaturedNetworks>> response) {
                if(!response.isSuccessful())
                {
                    imgLoading.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), "Code"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                imgLoading.setVisibility(View.GONE);
                networks = (ArrayList<FeaturedNetworks>) response.body();

//                Toast.makeText(getActivity(), "tital size of related channels is " + networks.size() , Toast.LENGTH_SHORT).show();

                if(mainActivity.gridStatus==true)
                {

                    recyclerViewGrid.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                    recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                    NewsItem newsItem=new NewsItem(getActivity(), networks,"grid");
                    recyclerViewGrid.setAdapter(newsItem);
                    // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
                    recyclerView.scheduleLayoutAnimation();
                    recyclerViewGrid.scheduleLayoutAnimation();

                    mainActivity.gridStatus = false;
                }
                else
                {
                    recyclerView.setHasFixedSize(true);
                    final LinearLayoutManager linearLayoutManagerss=new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManagerss);
                    NewsItem newsItem=new NewsItem(getActivity(), networks,"list");
                    recyclerView.setAdapter(newsItem);

                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        //this method call when scrooling start
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                            {
                                isScrooling=true;
                            }
                        }

                        @Override
                        //this method call when scrooled done
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            currentItems=linearLayoutManagerss.getChildCount();//get the count of currrently visible items in recycler view
                            totalItems=linearLayoutManagerss.getItemCount(); // get the count of total items in recycler view
                            scrollOutItems=linearLayoutManagerss.findFirstVisibleItemPosition(); // get the count of items which has been scrooled out in recycler view

                            if(isScrooling && (currentItems+scrollOutItems  ==  totalItems))
                            {
                                isScrooling=false;
                                if(visibilityOfTip)
                                {
                                    textViewShowTip.setVisibility(View.VISIBLE);
                                }

                            }
                            else if(currentItems<=5)
                            {
                                if(visibilityOfTip)
                                {
                                    textViewShowTip.setVisibility(View.VISIBLE);
                                }
                            }
                            else if(isScrooling && (currentItems+scrollOutItems  !=  totalItems))
                            {
                                textViewShowTip.setVisibility(View.GONE);
                            }
                        }
                    });

                    // the below code is for swap at recycler view item
                    recyclerTouchListener = new RecyclerTouchListener(getActivity(), recyclerView);
                    recyclerTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
                        @Override
                        public void onRowClicked(int position) {
//                        Toast.makeText(getActivity(), networks.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onIndependentViewClicked(int independentViewID, int position) {

                        }
                    }).setSwipeOptionViews(R.id.favourite_network).setSwipeable(R.id.complete_item_click, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                        @Override
                        public void onSwipeOptionClicked(int viewID, int position) {
                            switch (viewID) {
                                case R.id.favourite_network:
                                    setFavOrUnfav(position);
//                                    Toast.makeText(getActivity(), "Add in favourites", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
                    recyclerView.addOnItemTouchListener(recyclerTouchListener);

                    recyclerView.scheduleLayoutAnimation();
                    recyclerViewGrid.scheduleLayoutAnimation();
                }

            }

            @Override
            public void onFailure(Call<List<FeaturedNetworks>> call, Throwable t) {
                imgLoading.setVisibility(View.GONE);
            }
        });

    }
    public void getRelateableChannels(int cityName) {


        InterfaceApi interfaceApi = RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/wp/v2/");
        https://www.livenewsglobe.com/wp-json/wp/v2/city_tags/
        imgLoading.setVisibility(View.VISIBLE);
        call = interfaceApi.getChannelsAccordingToCities(cityName);
        call.enqueue(new Callback<List<FeaturedNetworks>>() {
            @Override
            public void onResponse(Call<List<FeaturedNetworks>> call, Response<List<FeaturedNetworks>> response) {
                if(!response.isSuccessful())
                {
                    imgLoading.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), "Code"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                imgLoading.setVisibility(View.GONE);
                networks = (ArrayList<FeaturedNetworks>) response.body();

                if(mainActivity.gridStatus==true)
                {
                    recyclerViewGrid.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                    recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                    NewsItem newsItem=new NewsItem(getActivity(), networks,"grid");
                    recyclerViewGrid.setAdapter(newsItem);
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
                    final LinearLayoutManager linearLayoutManagers = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManagers);
                    NewsItem newsItem=new NewsItem(getActivity(), networks,"list");
                    recyclerView.setAdapter(newsItem);

                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        //this method call when scrooling start
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                            {
                                isScrooling=true;
                            }
                        }

                        @Override
                        //this method call when scrooled done
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            currentItems=linearLayoutManagers.getChildCount();//get the count of currrently visible items in recycler view
                            totalItems=linearLayoutManagers.getItemCount(); // get the count of total items in recycler view
                            scrollOutItems=linearLayoutManagers.findFirstVisibleItemPosition(); // get the count of items which has been scrooled out in recycler view

                            if(isScrooling && (currentItems+scrollOutItems  ==  totalItems))
                            {
                                isScrooling=false;
                                if(visibilityOfTip)
                                {
                                    textViewShowTip.setVisibility(View.VISIBLE);
                                }
                            }
                            else if(currentItems<=5)
                            {
                                if(visibilityOfTip)
                                {
                                    textViewShowTip.setVisibility(View.VISIBLE);
                                }
                            }
                            else if(isScrooling && (currentItems+scrollOutItems  !=  totalItems))
                            {
                                textViewShowTip.setVisibility(View.GONE);
                            }
                        }
                    });

                    // the below code is for swap at recycler view item
                    recyclerTouchListener = new RecyclerTouchListener(getActivity(), recyclerView);
                    recyclerTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
                        @Override
                        public void onRowClicked(int position) {
//                        Toast.makeText(getActivity(), networks.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onIndependentViewClicked(int independentViewID, int position) {

                        }
                    }).setSwipeOptionViews(R.id.favourite_network).setSwipeable(R.id.complete_item_click, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                        @Override
                        public void onSwipeOptionClicked(int viewID, int position) {
                            switch (viewID) {
                                case R.id.favourite_network:
                                    setFavOrUnfav(position);
                                    break;
                            }
                        }
                    });
                    recyclerView.addOnItemTouchListener(recyclerTouchListener);

                    recyclerView.scheduleLayoutAnimation();
                    recyclerViewGrid.scheduleLayoutAnimation();
                }
            }

            @Override
            public void onFailure(Call<List<FeaturedNetworks>> call, Throwable t) {
                imgLoading.setVisibility(View.GONE);
            }
        });
    }
    public void searchNetworks(String networkName) {
        InterfaceApi interfaceApi = RetrofitLab.connect("http://www.livenewsglobe.com/wp-json/Newspaper/v2/");
        imgLoading.setVisibility(View.VISIBLE);
        callNetworks = interfaceApi.getNetworks(networkName);
        callNetworks.enqueue(new Callback<List<SearchNetwork>>() {
            @Override
            public void onResponse(Call<List<SearchNetwork>> call, Response<List<SearchNetwork>> response) {
                if(!response.isSuccessful())
                {
                    imgLoading.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), "Code"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                imgLoading.setVisibility(View.GONE);
                searchNetworks = (ArrayList<SearchNetwork>) response.body();

                final LinearLayoutManager linearLayoutManagersss=new LinearLayoutManager(getActivity());

                if(mainActivity.gridStatus == true)
                {
                    recyclerViewGrid.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                    recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                    SearchItem searchItem = new SearchItem(getActivity(), searchNetworks,"grid");
                    recyclerViewGrid.setAdapter(searchItem);
                    // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
                    recyclerView.scheduleLayoutAnimation();
                    recyclerViewGrid.scheduleLayoutAnimation();

                }
                else
                {
                    recyclerViewGrid.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(linearLayoutManagersss);
                    SearchItem searchItem = new SearchItem(getActivity(), searchNetworks,"list");
                    recyclerView.setAdapter(searchItem);
                    recyclerView.scheduleLayoutAnimation();
                    recyclerViewGrid.scheduleLayoutAnimation();
                }

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    //this method call when scrooling start
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                        {
                            isScrooling=true;
                        }
                    }

                    @Override
                    //this method call when scrooled done
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        currentItems=linearLayoutManagersss.getChildCount();//get the count of currrently visible items in recycler view
                        totalItems=linearLayoutManagersss.getItemCount(); // get the count of total items in recycler view
                        scrollOutItems=linearLayoutManagersss.findFirstVisibleItemPosition(); // get the count of items which has been scrooled out in recycler view

                        if(isScrooling && (currentItems+scrollOutItems  ==  totalItems))
                        {
                            isScrooling=false;
                            if(visibilityOfTip)
                            {
                                textViewShowTip.setVisibility(View.VISIBLE);
                            }
                        }
                        else if(currentItems<=5)
                        {
                            if(visibilityOfTip)
                            {
                                textViewShowTip.setVisibility(View.VISIBLE);
                            }
                        }
                        else if(isScrooling && (currentItems+scrollOutItems  !=  totalItems))
                        {
                            textViewShowTip.setVisibility(View.GONE);
                        }
                    }
                });

                // the below code is for swap at recycler view item
                recyclerTouchListener = new RecyclerTouchListener(getActivity(), recyclerView);
                recyclerTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
//                        Toast.makeText(getActivity(), networks.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                }).setSwipeOptionViews(R.id.favourite_network).setSwipeable(R.id.complete_item_click, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch (viewID) {
                            case R.id.favourite_network:
                                setFavOrUnfav(position);
                                break;
                        }
                    }
                });
                recyclerView.addOnItemTouchListener(recyclerTouchListener);

                recyclerView.scheduleLayoutAnimation();
                recyclerViewGrid.scheduleLayoutAnimation();

            }

            @Override
            public void onFailure(Call<List<SearchNetwork>> call, Throwable t) {

                imgLoading.setVisibility(View.GONE);

            }
        });
    }
    private void listView() {

        recyclerViewGrid.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        NewsItem newsItem = new NewsItem(getActivity(), arrayListFeaturedNetwork, "list");
        recyclerView.setAdapter(newsItem);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            //this method call when scrooling start
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrooling=true;
                }
            }

            @Override
            //this method call when scrooled done
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems=linearLayoutManager.getChildCount();//get the count of currrently visible items in recycler view
                totalItems=linearLayoutManager.getItemCount(); // get the count of total items in recycler view
                scrollOutItems=linearLayoutManager.findFirstVisibleItemPosition(); // get the count of items which has been scrooled out in recycler view

                if(isScrooling && (currentItems+scrollOutItems  ==  totalItems))
                {
                    isScrooling=false;
                    if(visibilityOfTip)
                    {
                        textViewShowTip.setVisibility(View.VISIBLE);
                    }
                }
                else if(isScrooling && (currentItems+scrollOutItems  !=  totalItems))
                {
                    textViewShowTip.setVisibility(View.GONE);
                }
            }
        });

        // the below code is for swap at recycler view item
        recyclerTouchListener = new RecyclerTouchListener(getActivity(), recyclerView);
        recyclerTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {
//                    Toast.makeText(getActivity(), networks.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {

            }
        }).setSwipeOptionViews(R.id.favourite_network).setSwipeable(R.id.complete_item_click, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
            @Override
            public void onSwipeOptionClicked(int viewID, final int position) {
                switch (viewID) {
                    case R.id.favourite_network:

                        setFavOrUnfav(position);
                }
            }
        });
        recyclerView.addOnItemTouchListener(recyclerTouchListener);

        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();
    }
    private void gridView() {
        recyclerViewGrid.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerViewGrid.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        NewsItem newsItem = new NewsItem(getActivity(), arrayListFeaturedNetwork, "grid");
        recyclerViewGrid.setAdapter(newsItem);

        // for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();
    }
    public void setFavOrUnfav(final int position) {
        final SharedPrefereneceManager sharedPrefereneceManager = new SharedPrefereneceManager(mainActivity);
//                            sharedPrefereneceManager.getLoginStatus();
        if(sharedPrefereneceManager.getLoginStatus() == true)
        {
            Log.d("position_data","" + networks.get(position).getId());
            InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/newspaper/v2/");
            Call<InsertChannelResponse> call = interfaceApi.checkIsFavouriteOrNot(sharedPrefereneceManager.getUserId(),networks.get(position).getId()); //retrofit create implementation for this method
            call.enqueue(new Callback<InsertChannelResponse>() {
                @Override
                public void onResponse(Call<InsertChannelResponse> call, Response<InsertChannelResponse> response) {
                    if(!response.isSuccessful())
                    {
//                        Toast.makeText(mainActivity, "response not successfull ", Toast.LENGTH_SHORT).show();
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mainActivity,SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Response not successfull");
                        sweetAlertDialog.show();
                        Button btn = sweetAlertDialog.findViewById(R.id.confirm_button);
//                        btn.setBackgroundColor(getResources().getColor(R.color.blueColor));
                        btn.setBackgroundResource(R.drawable.btn_bg);
                    }
                    else
                    {
                        InsertChannelResponse insertChannelResponse = response.body();
                        int status = insertChannelResponse.getStatus();
                        if(status==0)
                        {
                            Log.d("check value", "values" +mainActivity.user_id + mainActivity.userEmail + mainActivity.post_id );
                            InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/newspaper/v2/");
                            Call<InsertChannelResponse> calls = interfaceApi.insertFavouriteChannels(sharedPrefereneceManager.getUserId(),sharedPrefereneceManager.getUserEmail(),networks.get(position).getId()); //retrofit create implementation for this method
                            calls.enqueue(new Callback<InsertChannelResponse>() {
                                @Override
                                public void onResponse(Call<InsertChannelResponse> call, Response<InsertChannelResponse> response) {
                                    if(!response.isSuccessful())
                                    {
                                        Toast.makeText(mainActivity, "response not successfull ", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        InsertChannelResponse insertChannelResponse = response.body();
                                        //  int pos = position;

                                        recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.heart).setBackground(mainActivity.getResources().getDrawable(R.drawable.like_channel));


                                         Toast.makeText(mainActivity, "Successfully added in favourite List ", Toast.LENGTH_SHORT).show();
//                                            Button btn = viewNewsItem.findViewById(R.id.heart);
//                                            btn.setBackground(mainActivity.getResources().getDrawable(R.drawable.like_channel));
                                    }
                                }

                                @Override
                                public void onFailure(Call<InsertChannelResponse> call, Throwable t) {
                                    Toast.makeText(mainActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else if(status==1)
                        {
                            Log.d("check value", "values" +mainActivity.user_id + mainActivity.userEmail + mainActivity.post_id );
                            InterfaceApi interfaceApi = RetrofitLab.connect("https://livenewsglobe.com/wp-json/newspaper/v2/");
                            Call<InsertChannelResponse> calls = interfaceApi.deleteFavouriteChannels(sharedPrefereneceManager.getUserId(),networks.get(position).getId()); //retrofit create implementation for this method
                            calls.enqueue(new Callback<InsertChannelResponse>() {
                                @Override
                                public void onResponse(Call<InsertChannelResponse> call, Response<InsertChannelResponse> response) {
                                    if(!response.isSuccessful())
                                    {
                                        Toast.makeText(mainActivity, "response not successfull ", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        InsertChannelResponse insertChannelResponse = response.body();

                                        recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.heart).setBackground(mainActivity.getResources().getDrawable(R.drawable.favorite_icon));

                                        Toast.makeText(mainActivity, "Successfully remove in favourite List ", Toast.LENGTH_SHORT).show();
//                                            Button btn = viewNewsItem.findViewById(R.id.heart);
//                                            btn.setBackground(mainActivity.getResources().getDrawable(R.drawable.like_channel));
                                    }
                                }

                                @Override
                                public void onFailure(Call<InsertChannelResponse> call, Throwable t) {
                                    Toast.makeText(mainActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<InsertChannelResponse> call, Throwable t) {
                    Toast.makeText(mainActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });


        }
        else
        {
            customAlertDialog.showDialog();
        }
    }


}
