package com.example.weatherapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.data.model.CurrentWeather;
import com.example.weatherapp.data.model.FiveDayForecast;
import com.example.weatherapp.data.model.GeoCity;
import com.example.weatherapp.data.room.entity.WeatherEntity;

import java.util.List;

/**
 * Weather Repository Interface
 * */
public class RepositoryBaseClass {

    public LiveData<List<WeatherEntity>> forecastWeatherLiveData(){
        return new MutableLiveData<List<WeatherEntity>>();
    };

    public LiveData<List<WeatherEntity>> currentWeatherLiveData(){
        return new MutableLiveData<List<WeatherEntity>>();
    };


    public Long updateWeather(GeoCity geoCity, CurrentWeather currentWeather, FiveDayForecast fiveDayForecast){
        return -1L;
    };



}
