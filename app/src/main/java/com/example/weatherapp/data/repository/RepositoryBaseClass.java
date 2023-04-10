package com.example.weatherapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.data.model.CurrentWeather;
import com.example.weatherapp.data.model.GeoCity;
import com.example.weatherapp.data.room.entity.WeatherEntity;

import java.util.List;

/**
 * Weather Repository Interface
 * */
public class RepositoryBaseClass {

    public LiveData<List<WeatherEntity>> weatherLiveData(){
        return new MutableLiveData<List<WeatherEntity>>();
    };

    public Long updateCurrentWeather(GeoCity geoCity, CurrentWeather currentWeather){
        return -1L;
    };

}
