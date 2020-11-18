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

//    @GET("posts")
//    Call<List<NetworkModel>> getNetworks();


    @GET("post/")
    Call<List<SearchNetwork>> getNetworks(@Query("s") String networkName);

    @GET("posts/2")
    Call<List<FeaturedNetworks>> getNetworks();

    @GET("tags")
    Call<List<Cities>> getCites();

    @GET("states")
    Call<List<States>> getStates();

//    @GET("tags/58")
//    Call<List<FeaturedNetworks>> getRelatedAbcNetworks();
//
//    @GET("tags/60")
//    Call<List<FeaturedNetworks>> getRelatedFoxNetworks();
//
//    @GET("tags/62")
//    Call<List<FeaturedNetworks>> getRelatedCbsNetworks();
//
//    @GET("tags/70")
//    Call<List<FeaturedNetworks>> getRelatedIndependentNetworks();
//
//    @GET("tags/59")
//    Call<List<FeaturedNetworks>> getRelatedNbcNetworks();

    //below cities of california state

    @GET("tags/33")
    Call<List<FeaturedNetworks>> getRelatedSanDiegoChannels();

    @GET("tags/30")
    Call<List<FeaturedNetworks>> getRelatedLosAngelesCaChannels();

    @GET("tags/73")
    Call<List<FeaturedNetworks>> getRelatedSacramentoChannels();;

    @GET("tags/48")
    Call<List<FeaturedNetworks>> getRelatedSanFranciscoNetworks();

    @GET("tags/50")
    Call<List<FeaturedNetworks>> getRelatedOaklandNetworks();

    //below cities of colorado state

    @GET("tags/43")
    Call<List<FeaturedNetworks>> getRelatedDenverChannels();

    //below cities of arizona state

    @GET("tags/49")
    Call<List<FeaturedNetworks>> getRelatedPhoenixChannels();

    //below cities of florida state

    @GET("tags/34")
    Call<List<FeaturedNetworks>> getRelatedMiamiChannels();

    @GET("tags/65")
    Call<List<FeaturedNetworks>> getRelatedJacksonvilleChannels();

    @GET("tags/69")
    Call<List<FeaturedNetworks>> getRelatedStPetersburgChannels();

    @GET("tags/68")
    Call<List<FeaturedNetworks>> getRelatedTampaChannels();

    @GET("tags/71")
    Call<List<FeaturedNetworks>> getRelatedOrlandoChannels();

    //below cities of georgia state

    @GET("tags/39")
    Call<List<FeaturedNetworks>> getRelatedAtlantaChannels();

    //below cities of lllinois state

    @GET("tags/35")
    Call<List<FeaturedNetworks>> getRelatedChicagoChannels();

    @GET("tags/76")
    Call<List<FeaturedNetworks>> getRelatedDecaturChannels();

    //below cities of Massachusetts  and New Hampshire state

    @GET("tags/44")
    Call<List<FeaturedNetworks>> getRelatedBostonChannels();

    //below cities of Michigan state

    @GET("tags/51")
    Call<List<FeaturedNetworks>> getRelatedDetroitChannels();

    //below cities of Missouri state

    @GET("tags/40")
    Call<List<FeaturedNetworks>> getRelatedColumbiaMoChannels();

    //below cities of Nevada state

    @GET("tags/54")
    Call<List<FeaturedNetworks>> getRelatedLasVegasChannels();


    //below cities of ohio state

    @GET("tags/85")
    Call<List<FeaturedNetworks>> getRelatedClevelandChannels();

    @GET("tags/52")
    Call<List<FeaturedNetworks>> getRelatedColumbusChannels();

    //below cities of Oregon state

    @GET("tags/56")
    Call<List<FeaturedNetworks>> getRelatedPortlandChannels();

    //below cities of Pennsylvania state

    @GET("tags/46")
    Call<List<FeaturedNetworks>> getRelatedHarrisburgandChannels();

    //below cities of South Carolina state

    @GET("tags/85")
    Call<List<FeaturedNetworks>> getRelatedCharlestonScChannels();

    //below cities of Texas state

    @GET("tags/53")
    Call<List<FeaturedNetworks>> getRelatedAustinChannels();

    @GET("tags/31")
    Call<List<FeaturedNetworks>> getRelatedDallasAndFortWorthChannels();

    @GET("tags/42")
    Call<List<FeaturedNetworks>> getRelatedHoustonChannels();

    //below cities of Virginia state

    @GET("tags/38")
    Call<List<FeaturedNetworks>> getRelatedRichmondChannels();

    //below cities of Washington state

    @GET("tags/63")
    Call<List<FeaturedNetworks>> getRelatedSeattleChannels();

    @GET("tags/91")
    Call<List<FeaturedNetworks>> getRelatedWashingtonDcChannels();

//    @GET("tags/")
//    Call<List<FeaturedNetworks>> getChannelsAccordingToCities(@Query("tag_id") int tagId);
    //channels according to main networks

//    Call<List<FeaturedNetworks>> getChannelsAccordingToMainNetworks();
    @GET("tags/")
    Call<List<FeaturedNetworks>> getChannelsAccordingToMianNetworks(@Query("tag_id") int tagId,@Query("network") int network);
    

}
