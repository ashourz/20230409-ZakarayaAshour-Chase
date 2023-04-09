package com.example.weatherapp.data.repository;


import android.app.Application;

import com.example.weatherapp.R;
import com.example.weatherapp.data.model.GeoCity;
import com.example.weatherapp.data.remote.CityApiInterface;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Response;
import retrofit2.Retrofit;


public class GeoCityApiService {
    private final CityApiInterface cityApiInterface;
    private final String apiKey;

    @Inject
    public GeoCityApiService(
    @Named("geocodingClient") Retrofit retrofitClient,
    Application application) {
        this.cityApiInterface = retrofitClient.create(CityApiInterface.class);
        this.apiKey = application.getString(R.string.open_weather_api_key);
    }

    public Optional<List<GeoCity>> getCityData(String cityName) {
        try {
            Response<List<GeoCity>> response = cityApiInterface.getCityCoordinates(cityName, apiKey)
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

    public Optional<List<GeoCity>> getCityNameData(Double latitude, Double longitude) {
        try {
            Response<List<GeoCity>> response = cityApiInterface.getCityName(latitude, longitude, apiKey).execute();
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
