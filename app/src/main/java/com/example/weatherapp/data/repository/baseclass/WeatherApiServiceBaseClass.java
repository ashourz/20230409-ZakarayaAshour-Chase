package com.example.weatherapp.data.repository.baseclass;

import com.example.weatherapp.data.model.CurrentWeather;

import java.util.Optional;

/**
 * WeatherApiService Interface
 * */
public class WeatherApiServiceBaseClass {

    public Optional<CurrentWeather> getCurrentWeatherData(Double latitude, Double longitude){
        return Optional.empty();
    };
}
