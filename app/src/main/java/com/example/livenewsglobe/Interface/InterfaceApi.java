package com.example.livenewsglobe.Interface;

import android.provider.SyncStateContract;

import com.example.livenewsglobe.models.Cities;
import com.example.livenewsglobe.models.FeaturedNetworks;
import com.example.livenewsglobe.models.Register;
import com.example.livenewsglobe.models.RegisterUser;
import com.example.livenewsglobe.models.SearchNetwork;
import com.example.livenewsglobe.models.States;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @POST("users/register") //post request in body form-data parameter
    @FormUrlEncoded
//    use must @FormUrlEncoded for form data and @FieldMap or @Field to send params
//    Call<Register> registerUser(@FieldMap Map<String,String> params);
    Call<Register> registerUser(@Field("username") String username,@Field("email") String email,@Field("password") String password);

    @POST("login") //post request in body form-data parameter
    @FormUrlEncoded
//    use must @FormUrlEncoded for form data and @FieldMap or @Field to send params
//    Call<Register> registerUser(@FieldMap Map<String,String> params);
    Call<Register> loginUsre( @Field("email") String email,@Field("password") String password);

    @GET("posts/")
    Call<List<Cities>> getCitesByStates(@Query("category") String stateName);
    

}
