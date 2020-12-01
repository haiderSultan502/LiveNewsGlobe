package com.example.livenewsglobe;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenewsglobe.Interface.InterfaceApi;
import com.example.livenewsglobe.fragments.City;
import com.example.livenewsglobe.fragments.Favourite;
import com.example.livenewsglobe.fragments.Home;
import com.example.livenewsglobe.fragments.State;
import com.example.livenewsglobe.models.Cities;
import com.example.livenewsglobe.models.CityNames;
import com.example.livenewsglobe.models.FeaturedNetworks;
import com.example.livenewsglobe.models.ProgressDialog;
import com.example.livenewsglobe.models.SearchNetwork;
import com.example.livenewsglobe.models.States;
import com.example.livenewsglobe.otherClasses.CustomAlertDialog;
import com.example.livenewsglobe.otherClasses.GetStateCityNetwork;
import com.example.livenewsglobe.otherClasses.RetrofitLab;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class MainActivity extends AppCompatActivity {

    EditText search;
    Button searchButton,gridView,listView,filter,navigationDrawer,btnMoveBgColorGridBottomList,btnMoveBgColorfilterBottomList,btnMoveBgColorListBottomGrid,btnMoveBgColorFilterBottomGrid,btnMoveBgColorListBottomFilter,btnMoveBgColorGridBottomFilter;
    Boolean checkVisibilityFilter=false,checkVisibilityGrid=false,checkVisibilityList=true;
    public static RelativeLayout filter_options,btnBack;
    LinearLayout linearLayout;
    DrawerLayout drawerLayout;
    RelativeLayout relativeLayoutCity,relativeLayoutState,relativeLayoutNetwork;
    Spinner spinnerCity,spinnerState,spinnerNetwork;
    String[] cityNames={"City","Los Angeles","San Diego","San Jose","San Francisco","Fresno"};
    String[] networkNames={"Network","ABC","CBS","FOX","Independent","NBC"};
    ArrayList<String> arrayList=new ArrayList<String>();
    int check;
    public static BlurView blurView;

    RecyclerView recyclerView,recyclerViewGrid;

    Animation animation;

    ProgressDialog progressDialog;

    ImageView imgLoading;

    public static Boolean clicklist=false,clickGrid=false,clickFilter=false,getCityList=false,getStateList=false,getFeaturedList=false,gridStatus=false,filterStatus=false;
    public static Boolean homeFrag=false,cityFrag=false,stateFrag=false,regionFrag=false,favFrag=false;
//    stateGridView=false,stateListView=false,favouriteGridView=false,favouritListView=false,ciityGridView=false,cityListView=false,homeGridView=false,homeListView=false;


//    static InterfaceApi interfaceApi;

    Call<List<FeaturedNetworks>> call;
    Call<List<SearchNetwork>> callNetworks;
    Call<List<FeaturedNetworks>> callForRelatedNetwork;
    public static List<String> listCities;
    public static ArrayList<Cities> storeCities;
    public static ArrayList<States> storeStates;
    public static ArrayList<FeaturedNetworks> storeNetworks;


    static ArrayList<FeaturedNetworks> networks;
    static ArrayList<SearchNetwork> searchNetworks;
    static ArrayList<Cities> arrayListCity;
    ArrayList<Cities> arrayListCity2;
    ArrayList<Cities> citiesAccordingToState;


    GetStateCityNetwork getStateCityNetwork;
    ArrayList<States> statesList;

    static ArrayAdapter<String> arrayAdapterState;
    static String  arrrayState[] = new String[19];
    Boolean checkGridStatus=false,checkListStatus=false, checkStatusCitySpinner=false,searchStatus=false, isScrooling=false;

    int currentItems,totalItems,scrollOutItems;


    String checkNetworkOrCity;

    //end


    static int index;
    FrameLayout frameLayout;
    TabLayout tabLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment home = new Home("featuredNetworks","featuredNetworks");
    State state = new State();
    Home homef=new Home();
    Fragment city = new City("allCities");
    City cityList=new City();
    Fragment favourite = new Favourite();

    Cities cities=new Cities();
    public static int cityId=0;
    public static int networkId=0;

    DrawerLayout drawer;
    NavigationView navigationView;
    private Toolbar toolbar;
    private  static final String TAG="MianActivity";






    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressDialog =  new ProgressDialog("Loading...");

        recyclerView=findViewById(R.id.recycler_view);
        recyclerViewGrid=findViewById(R.id.recycler_view_gridView);

        networks = new ArrayList<>();

        arrayListCity = new ArrayList<>();
        arrayListCity2 = new ArrayList<>();
        citiesAccordingToState = new ArrayList<>();
        storeCities = new ArrayList<>();
        storeStates = new ArrayList<>();
        storeNetworks=new ArrayList<>();

        statesList = new ArrayList<>();
        listCities = new ArrayList<String>();


//        animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_left);
//        animation.setAnimationListener(this);

//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);//this line is used to when keyboard comes up then dwidgets not disturb

        navigationView=findViewById(R.id.navigation);
        navigationView.setItemIconTintList(null);

        btnBack=findViewById(R.id.back_btn_relativeLayout);

        navigationDrawer=findViewById(R.id.button_navigation_drawer);
        drawerLayout=findViewById(R.id.drawer_layout);

        gridView = findViewById(R.id.button_gridview);
        listView = findViewById(R.id.button_listview);
        filter=findViewById(R.id.button_filter);
        search=findViewById(R.id.edit_text_search);
        searchButton=findViewById(R.id.button_search);
        search=findViewById(R.id.edit_text_search);

//        imgLoading=findViewById(R.id.img_loading);
//        Glide.with(getApplicationContext()).load(R.drawable.loading).into(imgLoading);


        btnMoveBgColorfilterBottomList=findViewById(R.id.button_filterview_come_bottom_list);
        btnMoveBgColorGridBottomList=findViewById(R.id.button_filterview_come_bottom_list);

        btnMoveBgColorFilterBottomGrid=findViewById(R.id.button_filterview_come_bottom_grid);
        btnMoveBgColorListBottomGrid=findViewById(R.id.button_listview_come_bottom_grid);

        btnMoveBgColorListBottomFilter=findViewById(R.id.button_listview_come_bottom_filter);
        btnMoveBgColorGridBottomFilter=findViewById(R.id.button_gridview_come_bottom_filter);


        filter_options=findViewById(R.id.filter_options);
        linearLayout=findViewById(R.id.linear_layout_recycler_view);
        relativeLayoutCity=findViewById(R.id.relative_layout_city);
        relativeLayoutState=findViewById(R.id.relative_layout_state);
        relativeLayoutNetwork=findViewById(R.id.relative_layout_network);
        spinnerCity=findViewById(R.id.spinner_city);
        spinnerState=findViewById(R.id.spinner_state);
        spinnerNetwork=findViewById(R.id.spinner_network);
        blurView=findViewById(R.id.blurlayout);


        blurbackground();


        listView.setBackgroundResource(R.drawable.on_list);

        InterfaceApi interfaceApii = RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/Newspaper/v2/");
        Call<List<Cities>> callCity = interfaceApii.getCites();
        callCity.enqueue(new Callback<List<Cities>>() {
            @Override
            public void onResponse(Call<List<Cities>> call, Response<List<Cities>> response) {
                if(!response.isSuccessful())
                {
//                    progressDialog.progressDialogVar.dismiss();
                    Toast.makeText(getApplicationContext(), "Code"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

//                progressDialog.progressDialogVar.dismiss();
                arrayListCity2 = (ArrayList<Cities>) response.body();

                arrayListCity2.remove(0);
                arrayListCity2.remove(4);
                arrayListCity2.remove(14);
                arrayListCity2.remove(17);
                arrayListCity2.remove(21);
                //I place the array adapter inside on response because outside of onResponse adapter we receive the null arrraylist, and if we focus in array adpater that
//                we are passing whole object of arrayList but we get only city name in spinner so this was happend when we place the toString overRide function in class that of which we are
//                are making arraylist so this is most imp point that make toString overRide function and return the name in this function that we want to show in spinner.

                ArrayAdapter<Cities> arrayAdapterCity=new ArrayAdapter<Cities>(MainActivity.this,android.R.layout.simple_spinner_item, arrayListCity2);
                arrayAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCity.setAdapter(arrayAdapterCity);
            }

            @Override
            public void onFailure(Call<List<Cities>> call, Throwable t) {

            }
        });


//        listCities.add("Atlanta");
//        listCities.add("Arizona");
        AdapterView.OnItemSelectedListener listenerCity = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)
                {
//                    ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(211, 218, 224));
//                    ((TextView) view).setTextColor(Color.rgb(211, 218, 224));
                    relativeLayoutCity.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spinner_design));
                    spinnerCity.getBackground().setColorFilter(getResources().getColor(R.color.spinnerIconColor), PorterDuff.Mode.SRC_ATOP);

                }
                else
                {
//                    ((TextView) parent.getChildAt(position)).setTextColor(Color.rgb(16, 62, 101));
//                    ((TextView) view).setTextColor(Color.rgb(16, 62, 101));

                    cityId = arrayListCity2.get(position).getTermId();
                    String city = parent.getSelectedItem().toString();

                    spinnerNetwork.setEnabled(true);

                    Fragment home = new Home(cityId,"city");
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction= fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,home);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.addToBackStack(null);
                    fragmentTransaction.commit();

                    relativeLayoutCity.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spinner_item_select_design));
                    spinnerCity.getBackground().setColorFilter(getResources().getColor(R.color.spinnerSelectedIconColor), PorterDuff.Mode.SRC_ATOP);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }



        };

        spinnerCity.setOnItemSelectedListener(listenerCity);
        spinnerCity.setEnabled(false);






        AdapterView.OnItemSelectedListener listenerState = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)
                {
//                    ((TextView) view).setTextColor(Color.rgb(211, 218, 224));

//                    ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(211, 218, 224));
                    relativeLayoutState.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spinner_design));
                    spinnerState.getBackground().setColorFilter(getResources().getColor(R.color.spinnerIconColor), PorterDuff.Mode.SRC_ATOP);

                }
                else
                {
//                    ((TextView) parent.getChildAt(position)).setTextColor(Color.rgb(16, 62, 101));
//                    ((TextView) view).setTextColor(Color.rgb(16, 62, 101));
                    String state = parent.getSelectedItem().toString();
                    spinnerCity.setEnabled(true);
                    Fragment city = new City(state);
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction= fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,city);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.addToBackStack(null);
                    fragmentTransaction.commit();

                    relativeLayoutState.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spinner_item_select_design));
                    spinnerState.getBackground().setColorFilter(getResources().getColor(R.color.spinnerSelectedIconColor), PorterDuff.Mode.SRC_ATOP);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }



        };



        InterfaceApi interfaceApi = RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/Newspaper/v2/");
        Call<List<States>> call = interfaceApi.getStates(); //retrofit create implementation for this method

        call.enqueue(new Callback<List<States>>() {
            @Override
            public void onResponse(Call<List<States>> call, Response<List<States>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Code"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                statesList = (ArrayList<States>) response.body();
                statesList.remove(3);

//                Toast.makeText(getApplicationContext(), " " + statesList.size(), Toast.LENGTH_SHORT).show();

                for (int i = 0; i < statesList.size() ; i++)
                {
                    arrrayState[i] = statesList.get(i).getName();
                }

            }

            @Override
            public void onFailure(Call<List<States>> call, Throwable t) {
//            progressDialog.progressDialogVar.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("CheckMessage",t.getMessage());
            }
        });

        arrayAdapterState=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrrayState);
        arrayAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        spinnerState.setOnItemSelectedListener(listenerState);
        spinnerState.setAdapter(arrayAdapterState);






        AdapterView.OnItemSelectedListener listenerNetwork = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)
                {
//                    ((TextView) view).setTextColor(Color.rgb(211, 218, 224));
//                    ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(211, 218, 224));
                    relativeLayoutNetwork.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spinner_design));
                    spinnerNetwork.getBackground().setColorFilter(getResources().getColor(R.color.spinnerIconColor), PorterDuff.Mode.SRC_ATOP);

                }
                else
                {
//                    ((TextView) parent.getChildAt(position)).setTextColor(Color.rgb(16, 62, 101));
//                    ((TextView) view).setTextColor(Color.rgb(16, 62, 101));

                    String networkname = parent.getSelectedItem().toString();
                    switch (networkname)
                    {
                        case "ABC":
                            networkId=58;
                            break;
                        case "CBS":
                            networkId=62;
                            break;
                        case "FOX":
                            networkId=60;
                            break;
                        case "Independent":
                            networkId=70;
                            break;
                        case "NBC":
                            networkId=59;
                            break;

                    }
                    fragmentTransaction= getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,new Home(networkname,"networkname"));
                    //        ft.addToBackStack(null);
                    fragmentTransaction.commit();
                    relativeLayoutNetwork.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spinner_item_select_design));
                    spinnerNetwork.getBackground().setColorFilter(getResources().getColor(R.color.spinnerSelectedIconColor), PorterDuff.Mode.SRC_ATOP);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }



        };

        ArrayAdapter<String> arrayAdapterNetwork=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,networkNames);
        arrayAdapterNetwork.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerNetwork.setOnItemSelectedListener(listenerNetwork);
        spinnerNetwork.setEnabled(false);
        spinnerNetwork.setAdapter(arrayAdapterNetwork);




        btnBack.setVisibility(View.GONE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getSupportFragmentManager().getBackStackEntryCount() != 0 )
                {
                    getSupportFragmentManager().popBackStackImmediate();
                }

            }
        });




        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String networkName =  search.getText().toString();
                fragmentTransaction= getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,new Home(networkName,"search"));
                //        ft.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

        navigationDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });


        gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gridStatus=true;

                if(cityFrag==true)
                {
                    cityGridViewMode();
                }
                else if(stateFrag==true)
                {
                    stateGridViewMode("grid","state");
                }
                else if(homeFrag==true)
                {
                    homeGridViewMode();
                }
                else if(favFrag==true)
                {
                    favouriteGridViewMode();
                }



                checkVisibilityGrid=true;

                if(checkVisibilityList==true)
                {
                    //apply animation
                    animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.move_left);
                    btnMoveBgColorGridBottomList.setVisibility(View.VISIBLE);
                    btnMoveBgColorGridBottomList.setAnimation(animation);
                    btnMoveBgColorGridBottomList.setVisibility(View.INVISIBLE);

                    checkVisibilityList=false;
                }
                else if(checkVisibilityFilter==true)
                {
                    //apply animation
                    animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.move_right);
                    btnMoveBgColorGridBottomFilter.setVisibility(View.VISIBLE);
                    btnMoveBgColorGridBottomFilter.setAnimation(animation);
                    btnMoveBgColorGridBottomFilter.setVisibility(View.INVISIBLE);

                    checkVisibilityFilter=false;
                }



                filter_options.setVisibility(View.GONE);

                gridView.setBackgroundResource(R.drawable.on_grid);


                listView.setBackgroundResource(R.drawable.on);
                filter.setBackgroundResource(R.drawable.filter);


                LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 6.3f);
                linearLayout.setLayoutParams(params);
            }
        });

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cityFrag==true)
                {
                    cityListViewMode();
                }
                else if(stateFrag==true)
                {

                    stateListViewMode();

                }
                else if(homeFrag==true)
                {
                    homeListViewMode();
                }
                else if(favFrag==true)
                {
                    favouriteListViewMode();
                }
                checkVisibilityList=true;

                if(checkVisibilityGrid==true)
                {
                    //apply animation
                    animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.move_right);
                    btnMoveBgColorListBottomGrid.setVisibility(View.VISIBLE);
                    btnMoveBgColorListBottomGrid.setAnimation(animation);
                    btnMoveBgColorListBottomGrid.setVisibility(View.INVISIBLE);

                    checkVisibilityGrid=false;
                }
                else if(checkVisibilityFilter==true)
                {
                    //apply animation
                    animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.move_double_right);
                    btnMoveBgColorListBottomFilter.setVisibility(View.VISIBLE);
                    btnMoveBgColorListBottomFilter.setAnimation(animation);
                    btnMoveBgColorListBottomFilter.setVisibility(View.INVISIBLE);

                    checkVisibilityFilter=false;
                }



                filter_options.setVisibility(View.GONE);

                listView.setBackgroundResource(R.drawable.on_list);
                gridView.setBackgroundResource(R.drawable.grid);
                filter.setBackgroundResource(R.drawable.filter);



                LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 6.3f);
                linearLayout.setLayoutParams(params);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filterStatus=true;

                checkVisibilityFilter=true;

                if(checkVisibilityList==true)
                {
                    checkListStatus=true;
                    checkGridStatus=false;
                    //apply animation
                    animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.move_double_left);
                    btnMoveBgColorfilterBottomList.setVisibility(View.VISIBLE);
                    btnMoveBgColorfilterBottomList.setAnimation(animation);
                    btnMoveBgColorfilterBottomList.setVisibility(View.INVISIBLE);

                    checkVisibilityList=false;
                }
                else if(checkVisibilityGrid==true)
                {
                    checkListStatus=false;
                    checkGridStatus=true;
                    //apply animation
                    animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.move_left);
                    btnMoveBgColorFilterBottomGrid.setVisibility(View.VISIBLE);
                    btnMoveBgColorFilterBottomGrid.setAnimation(animation);
                    btnMoveBgColorFilterBottomGrid.setVisibility(View.INVISIBLE);

                    checkVisibilityGrid=false;
                }


                filter_options.setVisibility(View.VISIBLE);
                filter.setBackgroundResource(R.drawable.on_filter);

                listView.setBackgroundResource(R.drawable.on);
                gridView.setBackgroundResource(R.drawable.grid);


                filter_options.scheduleLayoutAnimation();

                LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 5.3f);
                linearLayout.setLayoutParams(params);
            }
        });

        //end

        initViews();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        setNavigationDrawer(); // call method


        frameLayout=findViewById(R.id.frame_layout);

        homeFrag=true;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,home);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.addToBackStack(null);
        fragmentTransaction.commit();
//        storeNetworks=homef.get();
//        getFeaturedList=true;


        tabLayout=findViewById(R.id.tabLayout);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentTransaction= getSupportFragmentManager().beginTransaction();
                index = tab.getPosition();
                tabLayout.getTabAt(index).getIcon().setColorFilter(getResources().getColor(R.color.selectedTabIconColor), PorterDuff.Mode.SRC_IN);

                //                 get the current selected tab's position and replace the fragment accordingly
                switch (index) {
                    case 0:
                        //these two lines code is for back from NewsVideoPlayer screen to MianActivity when click on tab
                        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                            getSupportFragmentManager().popBackStackImmediate();}
                        //end
                            LinearLayout.LayoutParams params1 =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 6.3f);
                            linearLayout.setLayoutParams(params1);
                            listView.setBackgroundResource(R.drawable.on_list);
                            gridView.setBackgroundResource(R.drawable.grid);
                            filter.setBackgroundResource(R.drawable.filter);
                            filter_options.setVisibility(View.GONE);
                            homeListViewMode();
                            homeFrag=true;
                            stateFrag=false;
                            cityFrag=false;
                            regionFrag=false;
                            favFrag=false;

                        break;
                    case 1:
                        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                            getSupportFragmentManager().popBackStackImmediate();}
                        if(getStateList==true)
                        {
                            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 6.3f);
                            linearLayout.setLayoutParams(params);

                            listView.setBackgroundResource(R.drawable.on_list);
                            gridView.setBackgroundResource(R.drawable.grid);
                            filter.setBackgroundResource(R.drawable.filter);
                            filter_options.setVisibility(View.GONE);
                            stateListViewMode();
                            stateFrag=true;
                            cityFrag=false;
                            regionFrag=false;
                            favFrag=false;
                            homeFrag=false;
                        }
                        else if(gridStatus==true)
                        {
                            listView.setBackgroundResource(R.drawable.on_list);
                            gridView.setBackgroundResource(R.drawable.grid);
                            stateFrag=true;
                            cityFrag=false;
                            regionFrag=false;
                            favFrag=false;
                            homeFrag=false;
                            fragmentTransaction.replace(R.id.frame_layout,state);
                            //        ft.addToBackStack(null);
                            fragmentTransaction.commit();
                            gridStatus=false;
                        }
                        else if(filterStatus==true)
                        {
                            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 6.3f);
                            linearLayout.setLayoutParams(params);

                            listView.setBackgroundResource(R.drawable.on_list);
                            filter.setBackgroundResource(R.drawable.filter);
                            filter_options.setVisibility(View.GONE);
                            stateFrag=true;
                            cityFrag=false;
                            regionFrag=false;
                            favFrag=false;
                            homeFrag=false;
                            fragmentTransaction.replace(R.id.frame_layout,state);
                            //        ft.addToBackStack(null);
                            fragmentTransaction.commit();
                            filterStatus=false;
                        }
                        else
                        {
                            listView.setBackgroundResource(R.drawable.on_list);
                            gridView.setBackgroundResource(R.drawable.grid);
                            filter.setBackgroundResource(R.drawable.filter);
                            filter_options.setVisibility(View.GONE);
                            stateFrag=true;
                            cityFrag=false;
                            regionFrag=false;
                            favFrag=false;
                            homeFrag=false;
                            fragmentTransaction.replace(R.id.frame_layout,state);
                            //        ft.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        break;

                    case 2:
                        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                            getSupportFragmentManager().popBackStackImmediate();}
                        if(getCityList==true)
                        {
                            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 6.3f);
                            linearLayout.setLayoutParams(params);

                            listView.setBackgroundResource(R.drawable.on_list);
                            gridView.setBackgroundResource(R.drawable.grid);
                            filter.setBackgroundResource(R.drawable.filter);
                            filter_options.setVisibility(View.GONE);
                            cityListViewMode();
                            cityFrag=true;
                            stateFrag=false;
                            regionFrag=false;
                            favFrag=false;
                            homeFrag=false;
                        }
                        else if(gridStatus==true)
                        {
                            listView.setBackgroundResource(R.drawable.on_list);
                            gridView.setBackgroundResource(R.drawable.grid);
                            cityFrag=true;
                            stateFrag=false;
                            regionFrag=false;
                            favFrag=false;
                            homeFrag=false;
                            fragmentTransaction.replace(R.id.frame_layout,city);
                            //        ft.addToBackStack(null);
                            fragmentTransaction.commit();
                            gridStatus=false;
                        }
                        else if(filterStatus==true)
                        {
                            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 6.3f);
                            linearLayout.setLayoutParams(params);

                            listView.setBackgroundResource(R.drawable.on_list);
                            filter.setBackgroundResource(R.drawable.filter);
                            filter_options.setVisibility(View.GONE);
                            cityFrag=true;
                            stateFrag=false;
                            regionFrag=false;
                            favFrag=false;
                            homeFrag=false;
                            fragmentTransaction.replace(R.id.frame_layout,city);
                            //        ft.addToBackStack(null);
                            fragmentTransaction.commit();
                            filterStatus=false;
                        }
                        else
                        {
                            listView.setBackgroundResource(R.drawable.on_list);
                            gridView.setBackgroundResource(R.drawable.grid);
                            filter.setBackgroundResource(R.drawable.filter);
                            filter_options.setVisibility(View.GONE);
                            cityFrag=true;
                            stateFrag=false;
                            regionFrag=false;
                            favFrag=false;
                            homeFrag=false;
                            fragmentTransaction.replace(R.id.frame_layout,city);
                            //        ft.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 3:

                        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                            getSupportFragmentManager().popBackStackImmediate();}
                        if(gridStatus==true)
                        {
                            listView.setBackgroundResource(R.drawable.on_list);
                            gridView.setBackgroundResource(R.drawable.grid);
                            favFrag=true;
                            regionFrag=false;
                            stateFrag=false;
                            cityFrag=false;
                            homeFrag=false;
                            fragmentTransaction.replace(R.id.frame_layout,favourite);
                            //        ft.addToBackStack(null);
                            fragmentTransaction.commit();
                            gridStatus=false;
                        }
                        else if(filterStatus==true)
                        {
                            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 6.3f);
                            linearLayout.setLayoutParams(params);

                            listView.setBackgroundResource(R.drawable.on_list);
                            filter.setBackgroundResource(R.drawable.filter);
                            filter_options.setVisibility(View.GONE);
                            favFrag=true;
                            regionFrag=false;
                            stateFrag=false;
                            cityFrag=false;
                            homeFrag=false;
                            fragmentTransaction.replace(R.id.frame_layout,favourite);
                            //        ft.addToBackStack(null);
                            fragmentTransaction.commit();
                            gridStatus=false;
                            filterStatus=false;
                        }
                        else
                        {
                            filter.setBackgroundResource(R.drawable.filter);
                            filter_options.setVisibility(View.GONE);
                            favFrag=true;
                            regionFrag=false;
                            stateFrag=false;
                            cityFrag=false;
                            homeFrag=false;
                            fragmentTransaction.replace(R.id.frame_layout,favourite);
                            //        ft.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                index = tab.getPosition();
                tabLayout.getTabAt(index).getIcon().setColorFilter(getResources().getColor(R.color.unselectedTabIconColor), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void blurbackground() {
        float radius = 1f;

        View decorView = getWindow().getDecorView();
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        //Set drawable to draw in the beginning of each blurred frame (Optional).
        //Can be used in case your layout has a lot of transparent space and your content
        //gets kinda lost after after blur is applied.
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);
    }


    private void homeGridViewMode() {
        Home home=new Home("grid",storeNetworks);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,home);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void homeListViewMode() {

        Home home=new Home("list",storeNetworks);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,home);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void stateGridViewMode(String checkViewStatus,String currentFrag) {

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();

        switch (currentFrag)
        {
            case "state":
                State state=new State(checkViewStatus,storeStates);
                fragmentTransaction.replace(R.id.frame_layout,state);
                break;
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
    private void stateListViewMode() {
        State state=new State("list",storeStates);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,state);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }


    private void cityListViewMode() {

        City cityListParam=new City("list",storeCities);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,cityListParam);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void cityGridViewMode() {

            City cityList=new City("grid",storeCities);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,cityList);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.addToBackStack(null);
            fragmentTransaction.commit();
    }

    private void favouriteListViewMode() {

        Favourite favourite=new Favourite("list");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,favourite);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void favouriteGridViewMode() {

        Favourite favourite=new Favourite("grid");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,favourite);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void initViews() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation);
        navigationView.setItemIconTintList(null);
        toolbar=findViewById(R.id.toolbar);
    }

    private void setNavigationDrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                Fragment fragment=null;
                int itemId = menuItem.getItemId();
                // check selected menu item's id and replace a Fragment Accordingly
                if (itemId == R.id.home) {
                    Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.profile) {
                    Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.setting) {
                    Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT).show();
                }  else if (itemId == R.id.favourite) {
                    Toast.makeText(getApplicationContext(), "Favourite", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.about_us) {
                    Toast.makeText(getApplicationContext(), "About_us", Toast.LENGTH_SHORT).show();
                }
                drawer.closeDrawers();
                return false;
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//
//        CustomAlertDialog customAlertDialog=new CustomAlertDialog(this);
//        if(customAlertDialog.alertDialogState)
//        {
//            blurView.setVisibility(View.GONE);
//        }
////        int count = getSupportFragmentManager().getBackStackEntryCount();
////
////        if (count == 0 ) {
////            super.onBackPressed();
//
////        }
//    }
}