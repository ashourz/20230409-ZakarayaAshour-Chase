package com.example.weatherapp.data.remote;

import com.example.weatherapp.data.model.CurrentWeather;
import com.example.weatherapp.data.model.FiveDayForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface for retrofit calls for current weather data
 * */
public interface WeatherApiInterface {
    @GET("weather?units=imperial")
    Call<CurrentWeather> getCurrentWeatherData(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String apiKey);

    @GET("forecast?units=imperial")
    Call<FiveDayForecast> getFiveDayForecastData(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String apiKey);
}
