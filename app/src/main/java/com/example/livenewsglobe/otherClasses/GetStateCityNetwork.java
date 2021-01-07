package com.example.livenewsglobe.otherClasses;

import android.content.Context;
import android.widget.Toast;

import com.example.livenewsglobe.Interface.InterfaceApi;
import com.example.livenewsglobe.models.FeaturedNetworks;
import com.example.livenewsglobe.models.States;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetStateCityNetwork {

    Context context;
    InterfaceApi interfaceApi;
    Call<List<States>> call;
    static ArrayList<States> states;

    public GetStateCityNetwork(Context context) {
        this.context=context;
    }
    public ArrayList<States> getStates()
    {
        states = new ArrayList<>();
        interfaceApi = RetrofitLab.connect("https://www.livenewsglobe.com/wp-json/Newspaper/v2/");
        call = interfaceApi.getStates();
        call.enqueue(new Callback<List<States>>() {
            @Override
            public void onResponse(Call<List<States>> call, Response<List<States>> response) {
                if(!response.isSuccessful())
                {
//                    Toast.makeText(context, "Code"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                states = (ArrayList<States>) response.body();
//                Toast.makeText(context, states.get(0).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<States>> call, Throwable t) {
//                Toast.makeText(context, "Failed to load states", Toast.LENGTH_SHORT).show();
            }
        });
        return states;
    }
}
