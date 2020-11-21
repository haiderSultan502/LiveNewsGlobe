package com.example.livenewsglobe.Interface;

import com.example.livenewsglobe.models.Cities;
import com.example.livenewsglobe.models.FeaturedNetworks;
import com.example.livenewsglobe.models.SearchNetwork;
import com.example.livenewsglobe.models.States;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceApi {

    @GET("post/")
    Call<List<SearchNetwork>> getNetworks(@Query("s") String networkName);

    @GET("posts/2")
    Call<List<FeaturedNetworks>> getNetworks();

    @GET("tags")
    Call<List<Cities>> getCites();

    @GET("states")
    Call<List<States>> getStates();

    @GET("city_tags/")
    Call<List<FeaturedNetworks>> getChannelsAccordingToCities(@Query("tag_id") int tagId);

    @GET("tags/")
    Call<List<FeaturedNetworks>> getChannelsAccordingToMianNetworks(@Query("tag_id") int tagId,@Query("network") int network);
    

}
