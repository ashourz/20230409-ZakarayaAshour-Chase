package com.example.weatherapp.data.repository;

import android.app.Application;

import com.example.weatherapp.R;
import com.example.weatherapp.data.model.FiveDayForecast;
import com.example.weatherapp.data.remote.WeatherApiInterface;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Response;
import retrofit2.Retrofit;


public class FiveDayForecastApiService {

    private final WeatherApiInterface weatherApiInterface;
    private final String apiKey;

    @Inject
    FiveDayForecastApiService(
            @Named("weatherClient") Retrofit retrofitClient,
            Application application) {
        this.weatherApiInterface = retrofitClient.create(WeatherApiInterface.class);
        this.apiKey = application.getString(R.string.open_weather_api_key);

    }


    public Optional<FiveDayForecast> getFiveDayForecastData(Double latitude, Double longitude) {
        try {
            Response<FiveDayForecast> response = weatherApiInterface.getFiveDayForecastData(latitude, longitude, apiKey)
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
