package com.example.weatherapp.data.repository.baseclass;

import com.example.weatherapp.data.model.FiveDayForecast;

import java.util.Optional;


public class ForecastApiServiceBaseClass {

    public Optional<FiveDayForecast> getFiveDayForecastData(Double latitude, Double longitude){
        return Optional.empty();
    };
}
