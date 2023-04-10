package com.example.weatherapp.data.repository;

import android.app.Application;

import com.example.weatherapp.R;
import com.example.weatherapp.data.model.CurrentWeather;
import com.example.weatherapp.data.remote.WeatherApiInterface;
import com.example.weatherapp.data.repository.baseclass.WeatherApiServiceBaseClass;

import java.util.Optional;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Makes Retrofit call to api and retrieves maps data from JSON to java object.
 * Further development could include different outputs depending on the response failure.
 * At the moment, all failures or empty responses will result in a null or empty optional value.
 * */
public class CurrentWeatherApiService extends WeatherApiServiceBaseClass {

    private final WeatherApiInterface weatherApiInterface;
    private final String apiKey;

    public CurrentWeatherApiService (
            Retrofit retrofitClient,
            Application application) {
        this.weatherApiInterface = retrofitClient.create(WeatherApiInterface.class);
        this.apiKey = application.getString(R.string.open_weather_api_key);
    }

    /**
     * Synchronous request for current weather data. Return empty optional on null body or exception.
     * */
    @Override
    public Optional<CurrentWeather> getCurrentWeatherData(Double latitude, Double longitude) {
        try {
            Response<CurrentWeather> response = weatherApiInterface.getCurrentWeatherData(latitude, longitude, apiKey)
                                                                   .execute();
            if (response.isSuccessful()) {
                if (response.body() == null) {
                    //TODO: POSSIBLE FURTHER IMPROVEMENT - NULL RESPONSE BODY
                    return Optional.empty();
                }
                return Optional.of(response.body());
            } else {
                //TODO: POSSIBLE FURTHER IMPROVEMENT - UNSUCCESSFUL RESPONSE
                return Optional.empty();
            }
        } catch (Exception e) {
            //TODO: POSSIBLE FURTHER IMPROVEMENT - REST CALL FAILURE
            return Optional.empty();
        }
    }
}
