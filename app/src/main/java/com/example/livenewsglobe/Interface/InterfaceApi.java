package com.example.livenewsglobe.Interface;

import android.database.Observable;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.SyncStateContract;

import com.example.livenewsglobe.models.Cities;
import com.example.livenewsglobe.models.FavouritesModel;
import com.example.livenewsglobe.models.FeaturedNetworks;
import com.example.livenewsglobe.models.ForgetPassword;
import com.example.livenewsglobe.models.InsertChannelResponse;
import com.example.livenewsglobe.models.Login.LoginModel;
import com.example.livenewsglobe.models.Register;
import com.example.livenewsglobe.models.RegisterUser;
import com.example.livenewsglobe.models.SearchNetwork;
import com.example.livenewsglobe.models.States;
import com.example.livenewsglobe.models.UserProfile;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
import retrofit2.http.Url;

public interface InterfaceApi {

    @GET("post/")   //working
    Call<List<FeaturedNetworks>> getNetworks(@Query("user_id") int userID,@Query("s") String networkName);
    @GET("post/")   //working
    Call<List<FeaturedNetworks>> getNetworks(@Query("s") String networkName);

    @GET("posts/2")
    Call<List<FeaturedNetworks>> getNetworks(@Query("user_id") int userID);

    @GET("posts/2")
    Call<List<FeaturedNetworks>> getNetworks();

    @GET("tags")
    Call<List<Cities>> getCites();

    @GET("states")
    Call<List<States>> getStates();

    @GET("city_tags/")
    Call<List<FeaturedNetworks>> getChannelsAccordingToCities(@Query("tag_id") int tagId,@Query("user_id") int userID);

    @GET("city_tags/")
    Call<List<FeaturedNetworks>> getChannelsAccordingToCities(@Query("tag_id") int tagId);

    @GET("tags/")
    Call<List<FeaturedNetworks>> getChannelsAccordingToMianNetworks(@Query("tag_id") int tagId,@Query("network") int network,@Query("user_id") int userID);

    @GET("tags/")
    Call<List<FeaturedNetworks>> getChannelsAccordingToMianNetworks(@Query("tag_id") int tagId,@Query("network") int network);

    @POST("users/register") //post request in body form-data parameter
    @FormUrlEncoded
//    use must @FormUrlEncoded for form data and @FieldMap or @Field to send params
//    Call<Register> registerUser(@FieldMap Map<String,String> params);
    Call<RegisterUser> registerUser(@Field("username") String username,@Field("email") String email,@Field("password") String password);

    @POST("login") //post request in body form-data parameter
    @FormUrlEncoded
//    use must @FormUrlEncoded for form data and @FieldMap or @Field to send params
//    Call<Register> registerUser(@FieldMap Map<String,String> params);
    Call<LoginModel> loginUsre(@Field("email") String email, @Field("password") String password);

    @GET("posts/")
    Call<List<Cities>> getCitesByStates(@Query("category") String stateName);

    @POST("fav_insert")
    Call<InsertChannelResponse> insertFavouriteChannels(@Query("user_id") int userID, @Query("user_email") String userEmail, @Query("posts_id") int postID);

    @POST("fav_delete")
    Call<InsertChannelResponse> deleteFavouriteChannels(@Query("user_id") int userID,@Query("posts_id") int postID);

    @GET("fav_get/")
    Call<List<FavouritesModel>> getFavouritesChannels(@Query("user_id") int userId);

    @POST("fav_check")
    Call<InsertChannelResponse> checkIsFavouriteOrNot(@Query("user_id") int userID,@Query("posts_id") int postID);

    @Multipart
    @POST("avatar_add/")
    Call<UserProfile> uploadImages(@Part MultipartBody.Part file);

    @POST("reset-password")
    Call<ForgetPassword> passwordForget(@Query("email") String userEmail);

    @POST("set-password")
    Call<ForgetPassword> passwordReset(@Query("email") String userEmail,@Query("password") String password,@Query("code") String code);



}
