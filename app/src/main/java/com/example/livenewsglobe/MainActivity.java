package com.example.livenewsglobe;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.example.livenewsglobe.models.FavouritesModel;
import com.example.livenewsglobe.models.FeaturedNetworks;
import com.example.livenewsglobe.models.ProgressDialog;
import com.example.livenewsglobe.models.SearchNetwork;
import com.example.livenewsglobe.models.States;
import com.example.livenewsglobe.models.UserProfile;
import com.example.livenewsglobe.otherClasses.CustomAlertDialog;
import com.example.livenewsglobe.otherClasses.GetStateCityNetwork;
import com.example.livenewsglobe.otherClasses.RetrofitLab;
import com.example.livenewsglobe.otherClasses.SharedPrefereneceManager;
import com.example.livenewsglobe.otherClasses.SweetAlertDialogGeneral;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import info.androidhive.fontawesome.FontDrawable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Boolean checkLoginStatus=false;
    public static int user_id,post_id;
    public static String userEmail;
    EditText search;
    Button searchButton,gridView,listView,filter,removeFilter,navigationDrawer,navigationDrawer2,btnMoveBgColorGridBottomList,btnMoveBgColorfilterBottomList,btnMoveBgColorListBottomGrid,btnMoveBgColorFilterBottomGrid,btnMoveBgColorListBottomFilter,btnMoveBgColorGridBottomFilter;
    Boolean checkVisibilityFilter=false,checkVisibilityGrid=false,checkVisibilityList=true;
    public static RelativeLayout filter_options,btnBack;
    LinearLayout linearLayout;
    DrawerLayout drawerLayout;
    RelativeLayout relativeLayoutCity,relativeLayoutState,relativeLayoutNetwork;
    static Spinner spinnerCity,spinnerState,spinnerNetwork;
    String[] cityList={"City","Los Angeles","San Diego","San Jose","San Francisco","Fresno"};
    String[] networkNames={"Network","ABC","CBS","FOX","Independent","NBC"};
    String stateName,cityName;
    public static BlurView blurView;

    RecyclerView recyclerView,recyclerViewGrid;

    Animation animation;
    ProgressDialog progressDialog;
    ImageView imgLoading;

    public static Boolean clicklist=false,clickGrid=false,clickFilter=false,getCityList=false,getStateList=false,getFeaturedList=false,gridStatus=false,filterStatus=false,getFavouritList=false;
    public static Boolean homeFrag=false,cityFrag=false,stateFrag=false,favFrag=false;

//    static InterfaceApi interfaceApi;
    public static List<String> listCities;
    public static ArrayList<Cities> storeCities;
    public static ArrayList<States> storeStates;
    public static ArrayList<FeaturedNetworks> storeNetworks;
    public static ArrayList<FavouritesModel> storeFavouriteNetworks;


    static ArrayList<FeaturedNetworks> networks;
    public static ArrayList<Cities> spinnerArrayListCity;
    public static ArrayList<String> spinnerArrayListCityString;
    ArrayList<Cities> arrayListCity2;
    ArrayList<Cities> citiesAccordingToState;

    ArrayList<States> statesList;
    ArrayList<String> statesList2;

    Boolean checkGridStatus=false,checkListStatus=false;

    NavigationView navigationViews;
    View headerView;
    TextView userName;
    ImageView imageViewUser,imagePicker;
    static Bitmap bitmaps;
    Uri path;
    String imagePath;
    private  static final int IMAGE = 0;
    String status,userProfileURL;

    //end


    static int index;
    FrameLayout frameLayout;
    TabLayout tabLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SweetAlertDialogGeneral sweetAlertDialogGeneral;
    CustomAlertDialog customAlertDialog;
    Home home;
    State state = new State();
    Fragment city = new City("allCities");
    Fragment favourite = new Favourite();

    public static int cityId=0;
    public static int networkId=0;

    DrawerLayout drawer;
    NavigationView navigationView;
    private Toolbar toolbar;
    private  static final String TAG="MianActivity";
    RelativeLayout userLogout;

    TextView aboutUs;
    View include;
    ImageView imTwitter,imFb,imPintrest,imInsta;






    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        include = findViewById(R.id.view_about_us);
        imTwitter= findViewById(R.id.twitter);
        imFb=findViewById(R.id.fb);
        imInsta=findViewById(R.id.insta);
        imPintrest=findViewById(R.id.pintrest);

        navigationView = (NavigationView) findViewById(R.id.navigation);
        headerView = navigationView.getHeaderView(0);

        userName = (TextView) headerView.findViewById(R.id.tv_username);
        imageViewUser = headerView.findViewById(R.id.user_image);
        imagePicker = headerView.findViewById(R.id.image_picker);


        progressDialog =  new ProgressDialog("Loading...");
        sweetAlertDialogGeneral = new SweetAlertDialogGeneral(this);
        customAlertDialog=new CustomAlertDialog(this);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerViewGrid=findViewById(R.id.recycler_view_gridView);

        networks = new ArrayList<>();

        spinnerArrayListCity = new ArrayList<>();
        spinnerArrayListCityString = new ArrayList<String>();
        arrayListCity2 = new ArrayList<>();
        citiesAccordingToState = new ArrayList<>();
        storeCities = new ArrayList<>();
        storeStates = new ArrayList<>();
        storeNetworks=new ArrayList<>();
        storeFavouriteNetworks = new ArrayList<>();

        statesList = new ArrayList<>();
        statesList2 = new ArrayList<String>();
        listCities = new ArrayList<String>();

        navigationView=findViewById(R.id.navigation);
        navigationView.setItemIconTintList(null);

        btnBack=findViewById(R.id.back_btn_relativeLayout);

        navigationDrawer=findViewById(R.id.button_navigation_drawer);
        navigationDrawer2=findViewById(R.id.button_navigation_drawer2);

//        // using paper plane icon for FAB
//        FontDrawable drawable = new FontDrawable(this, R.string.fa_list_solid, true, false);
//
//// white color to icon
//        drawable.setTextColor(ContextCompat.getColor(this, android.R.color.white));
//        drawable.setTextSize(25);
//        navigationDrawer.setBackground(drawable);


        drawerLayout=findViewById(R.id.drawer_layout);

        gridView = findViewById(R.id.button_gridview);
        listView = findViewById(R.id.button_listview);
        filter=findViewById(R.id.button_filter);
        removeFilter=findViewById(R.id.button_remove_filter);
        searchButton=findViewById(R.id.button_search);
        search=findViewById(R.id.edit_text_search);

        userName=findViewById(R.id.username);
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
        userLogout = findViewById(R.id.logout);
        spinnerCity=findViewById(R.id.spinner_city);
        spinnerState=findViewById(R.id.spinner_state);
        spinnerNetwork=findViewById(R.id.spinner_network);
        blurView=findViewById(R.id.blurlayout);



        final SharedPrefereneceManager sharedPrefereneceManager = new SharedPrefereneceManager(this);
//                            sharedPrefereneceManager.getLoginStatus();
        if (sharedPrefereneceManager.getLoginStatus() == true) {
                if (sharedPrefereneceManager.getUserProfileUrl().length() != 0)
                {
                    userName = (TextView) headerView.findViewById(R.id.tv_username);
                    userName.setText(sharedPrefereneceManager.getUserName());

                    imageViewUser = headerView.findViewById(R.id.user_image);
                    String url = sharedPrefereneceManager.getUserProfileUrl();
                    Picasso.with(this).load(url).placeholder(R.drawable.user_profile).error(R.drawable.user_profile).into(imageViewUser);
                }
        }

        blurbackground();

        imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                Toast.makeText(MainActivity.this, "Select image", Toast.LENGTH_SHORT).show();
            }
        });




        listView.setBackgroundResource(R.drawable.on_list);

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH)
                {
                    String networkName =  search.getText().toString();
                    fragmentTransaction= getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,new Home(networkName,"search"));
                    //        ft.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                return false;
            }
        });

        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefereneceManager sharedPrefereneceManager= new SharedPrefereneceManager(getApplicationContext());
                sharedPrefereneceManager.setLoginStatus(false);
                drawer.closeDrawers();
                replaceFragment();
            }
        });


        spinnerArrayListCityString.add("Cities");
        ArrayAdapter<String> arrayAdapterCity=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,spinnerArrayListCityString);
        arrayAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(arrayAdapterCity);

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

                    cityId = spinnerArrayListCity.get(position-1).getTermId();
                    String city = parent.getSelectedItem().toString();

                    spinnerNetwork.setEnabled(true);

                    Fragment home = new Home(cityId,"city");
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction= fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,home);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    //      ft.addToBackStack(null);
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

                int size = statesList.size();

                String stateList3[] = new String[size+1];

                stateList3[0] = "States" ;

                for(int i = 0; i < size ; i++) {

                     stateName = statesList.get(i).getName();
                         stateList3[i+1] = stateName;
                 }

                int sizee=stateList3.length;

                for(int i = 0; i < sizee ; i++) {

                    if (stateList3[i].equals("Featured")) {
                        continue;
                     } else {
                        statesList2.add(stateList3[i]);
                     }
                }

                ArrayAdapter<String> arrayAdapterState  = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,statesList2);
                arrayAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerState.setAdapter(arrayAdapterState);
            }

            @Override
            public void onFailure(Call<List<States>> call, Throwable t) {
//            progressDialog.progressDialogVar.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("CheckMessage",t.getMessage());
            }
        });
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
                removeFilter.setVisibility(View.VISIBLE);
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

        spinnerState.setOnItemSelectedListener(listenerState);


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

                if (networkName.length() == 0)
                {
                    sweetAlertDialogGeneral.showSweetAlertDialog("warning","Type some keyword to search");
                }
                else {

                    fragmentTransaction= getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,new Home(networkName,"search"));
                    //        ft.addToBackStack(null);
                    fragmentTransaction.commit();
                }


            }
        });

        navigationDrawer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });
        navigationDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });
        removeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    removeFilter.setVisibility(View.INVISIBLE);
                ArrayAdapter<String> arrayAdapterState  = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,statesList2);
                arrayAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerState.setEnabled(true);
                spinnerState.setAdapter(arrayAdapterState);


                spinnerArrayListCityString.add("Cities");
                ArrayAdapter<String> arrayAdapterCity=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,spinnerArrayListCityString);
                arrayAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCity.setEnabled(false);
                spinnerCity.setAdapter(arrayAdapterCity);

                ArrayAdapter<String> arrayAdapterNetwork=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,networkNames);
                arrayAdapterNetwork.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnerNetwork.setEnabled(false);
                spinnerNetwork.setAdapter(arrayAdapterNetwork);
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


                LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 7.3f);
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



                LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 7.3f);
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

                LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 6.3f);
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
        Home homee = new Home("featuredNetworks","featuredNetworks");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,homee);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();


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
                            checkBackStackPopFrag();
                            setBgResource();
                            homeListViewMode();
                            setFragStatus("homeFrag");
                        break;
                    case 1:
                        checkBackStackPopFrag();
                        if(getStateList==true)
                        {
                            setBgResource();
                            stateListViewMode();
                            setFragStatus("stateFrag");
                        }
                        else if(gridStatus==true)
                        {
                            setFragStatus("stateFrag");
                            fragmentTransaction.replace(R.id.frame_layout,state);
                            fragmentTransaction.commit();
                            gridStatus=false;
                        }
                        else if(filterStatus==true)
                        {
                            setLinearLayoutparam();
                            setBgResourceListFilter();
                            setFragStatus("stateFrag");
                            fragmentTransaction.replace(R.id.frame_layout,state);
                            fragmentTransaction.commit();
                            filterStatus=false;
                        }
                        else
                        {
                            setBgResource();
                            setFragStatus("stateFrag");
                            fragmentTransaction.replace(R.id.frame_layout,state);
                            //        ft.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        break;

                    case 2:
                        checkBackStackPopFrag();
                        if(getCityList==true)
                        {
                            setBgResource();
                            cityListViewMode();
                            setFragStatus("cityFrag");
                        }
                        else if(gridStatus==true)
                        {
                            setBgResourceListGrid();
                            setFragStatus("cityFrag");
                            fragmentTransaction.replace(R.id.frame_layout,city);
                            fragmentTransaction.commit();
                            gridStatus=false;
                        }
                        else if(filterStatus==true)
                        {
                            setLinearLayoutparam();
                            setBgResourceListFilter();

                            setFragStatus("cityFrag");
                            fragmentTransaction.replace(R.id.frame_layout,city);
                            fragmentTransaction.commit();
                            filterStatus=false;
                        }
                        else
                        {
                            setBgResource();
                            setFragStatus("cityFrag");
                            fragmentTransaction.replace(R.id.frame_layout,city);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 3:

                        checkBackStackPopFrag();
                        if(getFavouritList==true)
                        {
                            filter.setBackgroundResource(R.drawable.filter);
                            filter_options.setVisibility(View.GONE);
                            favouriteListViewMode();
                            setFragStatus("favFrag");
                        }
                        else if(gridStatus==true)
                        {
                            setBgResourceListGrid();
                            setFragStatus("favFrag");
//                            Fragment favourites = new Favourite("grid",storeFavouriteNetworks);
                            fragmentTransaction.replace(R.id.frame_layout,favourite);
                            fragmentTransaction.commit();
                            gridStatus=false;
                        }
                        else if(filterStatus==true)
                        {
                            setLinearLayoutparam();
                            setBgResourceListFilter();
                            setFragStatus("favFrag");
                            fragmentTransaction.replace(R.id.frame_layout,favourite);
                            fragmentTransaction.commit();
                            gridStatus=false;
                            filterStatus=false;
                        }
                        else
                        {
                            filter.setBackgroundResource(R.drawable.filter);
                            filter_options.setVisibility(View.GONE);
                            setFragStatus("favFrag");
                            fragmentTransaction.replace(R.id.frame_layout,favourite);
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
    public void setFragStatus(String value) {
        switch (value)
        {
            case "homeFrag":
                homeFrag=true;
                stateFrag=false;
                cityFrag=false;
                favFrag=false;
                break;
            case "cityFrag":
                cityFrag=true;
                stateFrag=false;
                homeFrag=false;
                favFrag=false;
                break;
            case "stateFrag":
                stateFrag=true;
                homeFrag=false;
                cityFrag=false;
                favFrag=false;
                break;
            case "favFrag":
                favFrag=true;
                stateFrag=false;
                homeFrag=false;
                cityFrag=false;
        }

    }
    public void setBgResource() {
        LinearLayout.LayoutParams params1 =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 7.3f);
        linearLayout.setLayoutParams(params1);
        listView.setBackgroundResource(R.drawable.on_list);
        gridView.setBackgroundResource(R.drawable.grid);
        filter.setBackgroundResource(R.drawable.filter);
        filter_options.setVisibility(View.GONE);
    }
    public void setBgResourceListGrid() {
        listView.setBackgroundResource(R.drawable.on_list);
        gridView.setBackgroundResource(R.drawable.grid);
    }
    public void setBgResourceListFilter() {
        listView.setBackgroundResource(R.drawable.on_list);
        filter.setBackgroundResource(R.drawable.filter);
        filter_options.setVisibility(View.GONE);
    }
    public void setLinearLayoutparam() {
        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(MATCH_PARENT, 0, 7.3f);
        linearLayout.setLayoutParams(params);
    }
    public void checkBackStackPopFrag() {
        //these two lines code is for back from NewsVideoPlayer screen to MianActivity when click on tab
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStackImmediate();}
        //end
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

    public void setCitySpinnerListAdapetr()
    {
        int size = spinnerArrayListCity.size();

        String CityList[] = new String[size+1];

        CityList[0] = "Cities" ;

        for(int i = 0; i < size ; i++) {

            cityName = spinnerArrayListCity.get(i).getName();
            CityList[i+1] = cityName;
        }

        ArrayAdapter<String> arrayAdapterCity=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,CityList);

        arrayAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCity.setAdapter(arrayAdapterCity);

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

//        if(getFeaturedList == true)
//        {
//            home=new Home("list",storeNetworks);
//        }
//        else
//        {
            home = new Home("featuredNetworks","featuredNetworks");
//        }
//        Home home=new Home("list",storeNetworks);
//        Home home=new Home("featuredNetworks",storeNetworks);
//        Fragment home = new Home("featuredNetworks","featuredNetworks");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,home);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
    public void replaceFragment()
    {
        home = new Home("featuredNetworks","featuredNetworks");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,home);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
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

//        Favourite favourite=new Favourite("list",storeFavouriteNetworks);
        Favourite favourite=new Favourite();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,favourite);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void favouriteGridViewMode() {

        Favourite favourites=new Favourite("grid",storeFavouriteNetworks);
//        Favourite favourite=new Favourite();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,favourites);
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
                    homeListViewMode();
                    include.setVisibility(View.GONE);
//                    include.setVisibility(View.GONE);
                } else if (itemId == R.id.profile) {
                    Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                }  else if (itemId == R.id.favourite) {
                    Fragment favourite = new Favourite();
                    fragmentTransaction= getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,favourite);
                    fragmentTransaction.commit();
                } else if (itemId == R.id.about_us) {
                    include.setVisibility(View.VISIBLE);
                }
                drawer.closeDrawers();
                return false;
            }
        });
    }
    public void setUserName(String username)
    {

        userName = (TextView) headerView.findViewById(R.id.tv_username);
        userName.setText(username);

    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== IMAGE && resultCode==RESULT_OK && data!=null)
        {
            path = data.getData();
            imagePath = getRealPathFromUri(path);
            uploadUserProfile();
        }
    }
    private String getRealPathFromUri(Uri uri)
    {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        int col_inex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(col_inex);
        cursor.close();
        return result;
    }
    private void uploadUserProfile() {
        final SharedPrefereneceManager sharedPrefereneceManager = new SharedPrefereneceManager(this);
//                            sharedPrefereneceManager.getLoginStatus();
        if (sharedPrefereneceManager.getLoginStatus() == true) {
            File file = new File(imagePath);
//                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);

            InterfaceApi interfaceApi = RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/newspaper/v2/");

            Call<UserProfile> call = interfaceApi.uploadImages(body);

            call.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                    if (!response.isSuccessful()) {
                        sweetAlertDialogGeneral.showSweetAlertDialog("warning", "Something goes wromg");
//                    Toast.makeText(getActivity(), "Code"+response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    UserProfile userProfile = response.body();
                    status = userProfile.getStatus();
                    userProfileURL = userProfile.getUrl();

                    SharedPrefereneceManager sharedPrefereneceManager = new SharedPrefereneceManager(getApplicationContext());
                    sharedPrefereneceManager.setUserProfileUrl(userProfileURL);

                    Picasso.with(getApplicationContext()).load(userProfileURL).placeholder(R.drawable.user_profile).error(R.drawable.user_profile).into(imageViewUser);
                    SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE );




                    sweetAlertDialogGeneral.showSweetAlertDialog("success", status);
                }

                @Override
                public void onFailure(Call<UserProfile> call, Throwable t) {
                    sweetAlertDialogGeneral.showSweetAlertDialog("error", t.getMessage());
                }
            });
        }
        else
        {
            customAlertDialog.showDialog();
        }
    }

    @Override
    public void onClick(View v) {

    }
}