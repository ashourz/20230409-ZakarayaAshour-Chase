package com.example.weatherapp.data.remote;

import com.example.weatherapp.data.model.GeoCity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Interface for retrofit calls for geocoding and reverse lookup
 * */
public interface CityApiInterface {
    @GET("direct?limit=5")
    Call<List<GeoCity>> getCityCoordinates(@Query("q") String cityName, @Query("appid") String apiKey);
    @GET("reverse?limit=1")
    Call<List<GeoCity>> getCityName(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String apiKey);
}
