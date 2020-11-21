package com.example.livenewsglobe.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livenewsglobe.Interface.InterfaceApi;
import com.example.livenewsglobe.MainActivity;
import com.example.livenewsglobe.R;
import com.example.livenewsglobe.adapter.CityItem;
import com.example.livenewsglobe.adapter.NewsItem;
import com.example.livenewsglobe.adapter.SearchItem;
import com.example.livenewsglobe.adapter.StateItem;
import com.example.livenewsglobe.models.Cities;
import com.example.livenewsglobe.models.FeaturedNetworks;
import com.example.livenewsglobe.models.ProgressDialog;
import com.example.livenewsglobe.models.SearchNetwork;
import com.example.livenewsglobe.models.States;
import com.example.livenewsglobe.otherClasses.GetStateCityNetwork;
import com.example.livenewsglobe.otherClasses.RecyclerTouchListener;
import com.example.livenewsglobe.otherClasses.RetrofitLab;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class Home extends Fragment {

    View view;
    private RecyclerView recyclerView,recyclerViewGrid;
    RecyclerTouchListener recyclerTouchListener;
    ImageView imgLoading;
    ProgressDialog progressDialog,progressDialoge;
    Boolean isScrooling=false;
    LinearLayout textViewShowTip;

    Context context;
    Dialog dialog;
    Button btnLogin,btnSignUp;
    TextView tvLogin,tvSignUp;
    EditText userName;
    Animation animation;
    RelativeLayout dialogTab;
    ColorStateList oldColors;


//    static InterfaceApi interfaceApi;

    MainActivity mainActivity;
    CityItem cityItem;

    Call<List<FeaturedNetworks>> call;
    Call<List<SearchNetwork>> callNetworks;
    Call<List<FeaturedNetworks>> callForRelatedNetwork;


    static ArrayList<FeaturedNetworks> networks;
    static ArrayList<SearchNetwork> searchNetworks;
    static ArrayList<Cities> arrayListCity;
    ArrayList<Cities> arrayListCity2;
    ArrayList<Cities> citiesAccordingToState;


    GetStateCityNetwork getStateCityNetwork;
    ArrayList<States> statesList;

    static ArrayAdapter<String> arrayAdapterState;

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



        arrayListFeaturedStore=new ArrayList<>();
        networks = new ArrayList<>();

        arrayListCity = new ArrayList<>();
        arrayListCity2 = new ArrayList<>();
        citiesAccordingToState = new ArrayList<>();

        statesList = new ArrayList<>();



    }



    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        Toast.makeText(getActivity(), "onCreateView call()", Toast.LENGTH_SHORT).show();
        view=inflater.inflate(R.layout.home,container,false);

        Context context;
        mainActivity = (MainActivity) getActivity();
        cityItem=new CityItem();


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);//this line is used to when keyboard comes up then dwidgets not disturb


        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerViewGrid=view.findViewById(R.id.recycler_view_gridView);


        imgLoading=view.findViewById(R.id.img_loading);
        Glide.with(getContext()).load(R.drawable.loading).into(imgLoading);

        textViewShowTip=view.findViewById(R.id.text_tip_show);
        textViewShowTip.setVisibility(View.GONE);

        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_login_signup_dialog);

        //Set the background of the dialog's root view to transparent, because Android puts your dialog layout within a root view that hides the corners in your custom layout.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvLogin=dialog.findViewById(R.id.login);
        tvSignUp=dialog.findViewById(R.id.signUp);
        btnLogin=dialog.findViewById(R.id.btn_login);
        userName=dialog.findViewById(R.id.username);
        btnSignUp=dialog.findViewById(R.id.btn_signup);
        dialogTab=dialog.findViewById(R.id.dialog_tab);
        oldColors =  tvSignUp.getTextColors();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.blurView.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.blurView.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animation = AnimationUtils.loadAnimation(getActivity(),
                        R.anim.alert_bar_move_right);
                dialogTab.setAnimation(animation);


                tvSignUp.setTextColor(Color.parseColor("#103E65"));
                tvLogin.setTextColor(oldColors);

                userName.setVisibility(View.VISIBLE);
                btnSignUp.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation = AnimationUtils.loadAnimation(getActivity(),
                        R.anim.alert_bar_move_left);
                dialogTab.setAnimation(animation);

                tvLogin.setTextColor(Color.parseColor("#103E65"));
                tvSignUp.setTextColor(oldColors);

                userName.setVisibility(View.GONE);
                btnSignUp.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
            }
        });


        // the sharedPreference code for permanently remove tip by clicking on tip the TIP is  add the channel in favouirte
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE );
        visibilityOfTip =prefs.getBoolean("visibilityOfTip", true);

            textViewShowTip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textViewShowTip.setVisibility(View.GONE);

                    SharedPreferences prefs = getActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE );
                    SharedPreferences.Editor editor = prefs.edit();
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

        //rest api work start from here

        checkListStatus=true;

//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        NewsItem newsItem=new NewsItem(getActivity(), (ArrayList<FeaturedNetworks>) networks,"list");
//        recyclerView.setAdapter(newsItem);

//         for apply animation at receycler view items every time when show recycelr view ->using below line and add animation using attribute property android:layoutAnimation="@anim/layout_animation" in the XML of recycler view
        recyclerView.scheduleLayoutAnimation();
        recyclerViewGrid.scheduleLayoutAnimation();

        return view;
    }



    public void getFeaturedNetworks()
    {

//    progressDialog.startDialog(getActivity());
//    Toast.makeText(getActivity(), "fuctuon call", Toast.LENGTH_SHORT).show();
    imgLoading.setVisibility(View.VISIBLE);



    InterfaceApi interfaceApi = RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/Newspaper/v2/");
    Call<List<FeaturedNetworks>> call = interfaceApi.getNetworks(); //retrofit create implementation for this method

    call.enqueue(new Callback<List<FeaturedNetworks>>() {
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
            store(networks);


            recyclerView.setHasFixedSize(true);
            Context context;
            final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
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
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onSwipeOptionClicked(int viewID, int position) {
                    switch (viewID) {
                        case R.id.favourite_network:

                            mainActivity.blurView.setVisibility(View.VISIBLE);
                            dialog.show();
                            Toast.makeText(getActivity(), "Add in favourites", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
            recyclerView.addOnItemTouchListener(recyclerTouchListener);

            recyclerView.scheduleLayoutAnimation();
            recyclerViewGrid.scheduleLayoutAnimation();
        }

        @Override
        public void onFailure(Call<List<FeaturedNetworks>> call, Throwable t) {
//            progressDialog.progressDialogVar.dismiss();
            imgLoading.setVisibility(View.GONE);
//            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("CheckMessage",t.getMessage());
        }
    });
}
    public void getRelatedNetworks(final String network)
    {

        imgLoading.setVisibility(View.VISIBLE);


        InterfaceApi interfaceApi = RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/wp/v2/");
        callForRelatedNetwork = interfaceApi.getChannelsAccordingToMianNetworks(mainActivity.cityId,mainActivity.networkId); //retrofit create implementation for this method

//        switch (network)
//        {
//            case "ABC":
////                callForRelatedNetwork = interfaceApi.getRelatedAbcNetworks(); //retrofit create implementation for this method
//                callForRelatedNetwork = interfaceApi.getChannelsAccordingToMianNetworks(73,62); //retrofit create implementation for this method
//                break;
////            case "CBS":
////                callForRelatedNetwork = interfaceApi.getRelatedCbsNetworks(); //retrofit create implementation for this method
////                break;
////            case "FOX":
////                callForRelatedNetwork = interfaceApi.getRelatedFoxNetworks(); //retrofit create implementation for this method
////                break;
////            case "Independent":
////                callForRelatedNetwork = interfaceApi.getRelatedIndependentNetworks(); //retrofit create implementation for this method
////                break;
////            case "NBC":
////                callForRelatedNetwork = interfaceApi.getRelatedNbcNetworks(); //retrofit create implementation for this method
////                break;
//        }

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

//        callForRelatedNetwork.enqueue(new Callback<List<FeaturedNetworks>>() {
//            @Override
//            public void onResponse(Call<List<FeaturedNetworks>> call, Response<List<FeaturedNetworks>> response) {
//                if(!response.isSuccessful())
//                {
//                    imgLoading.setVisibility(View.GONE);
////                    Toast.makeText(getActivity(), "Code"+response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                imgLoading.setVisibility(View.GONE);
//                networks = (ArrayList<FeaturedNetworks>) response.body();
//
////                Toast.makeText(getActivity(), "tital size of related channels is " + networks.size() , Toast.LENGTH_SHORT).show();
//
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                NewsItem newsItem=new NewsItem(getActivity(), networks,"list");
//                recyclerView.setAdapter(newsItem);
//                recyclerView.scheduleLayoutAnimation();
//                recyclerViewGrid.scheduleLayoutAnimation();
//            }
//
//            @Override
//            public void onFailure(Call<List<FeaturedNetworks>> call, Throwable t) {
//                imgLoading.setVisibility(View.GONE);
////                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
////                Log.d("CheckMessage",t.getMessage());
//            }
//        });

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
                                    mainActivity.blurView.setVisibility(View.VISIBLE);
                                    dialog.show();
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

    public void searchNetworks(String networkName)
    {
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

                recyclerView.setHasFixedSize(true);
                Context context;
                final LinearLayoutManager linearLayoutManagersss=new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManagersss);

                SearchItem searchItem = new SearchItem(getActivity(), searchNetworks,"list");
                recyclerView.setAdapter(searchItem);

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
                                mainActivity.blurView.setVisibility(View.VISIBLE);
                                dialog.show();
//                                Toast.makeText(getActivity(), "Add in favourites", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                recyclerView.addOnItemTouchListener(recyclerTouchListener);

                recyclerView.scheduleLayoutAnimation();
                recyclerViewGrid.scheduleLayoutAnimation();

//                        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                            @Override
//                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                                super.onScrollStateChanged(recyclerView, newState);
//                                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
//                                {
//                                    isScrooling=true;
//                                }
//                            }
//
//                            @Override
//                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                                super.onScrolled(recyclerView, dx, dy);
//
//                                currentItems= linearLayoutManager.getChildCount();
//                                totalItems=linearLayoutManager.getItemCount();
//                                scrollOutItems=linearLayoutManager.findFirstVisibleItemPosition();
//
//                                if(isScrooling && (currentItems + scrollOutItems == totalItems))
//                                {
//                                    isScrooling=false;
//                                    fetchadata();
//                                }
//                            }
//                        });
            }

            @Override
            public void onFailure(Call<List<SearchNetwork>> call, Throwable t) {

                imgLoading.setVisibility(View.GONE);
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
//                Log.d("CheckMessage",t.getMessage());

            }
        });
    }


    public void store(ArrayList<FeaturedNetworks> arrayListFeaturedNetwork) {
        arrayListFeaturedStore=arrayListFeaturedNetwork;
    }
    public ArrayList<FeaturedNetworks> get() {
        return  arrayListFeaturedStore;
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
            public void onSwipeOptionClicked(int viewID, int position) {
                switch (viewID) {
                    case R.id.favourite_network:
                        mainActivity.blurView.setVisibility(View.VISIBLE);
                        dialog.show();
//                        Toast.makeText(getActivity(), "Add in favourites", Toast.LENGTH_SHORT).show();
                        break;
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

}
