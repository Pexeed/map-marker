package br.com.pexed.mapmarker.model.repository;

import br.com.pexed.mapmarker.model.model.Container;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapsService {

    @GET("json")
    Call<Container> getLocationByAddress(@Query("address") String address,
                                         @Query("sensor") String sensor,
                                         @Query("key") String apiKey);

}
