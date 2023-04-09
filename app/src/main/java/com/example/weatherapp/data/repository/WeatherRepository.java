package com.example.weatherapp.data.repository;

import androidx.lifecycle.LiveData;

import com.example.weatherapp.data.model.CurrentWeather;
import com.example.weatherapp.data.model.GeoCity;
import com.example.weatherapp.data.model.WeatherMapper;
import com.example.weatherapp.data.room.dao.WeatherDao;
import com.example.weatherapp.data.room.entity.WeatherEntity;

import java.util.List;

import javax.inject.Inject;

/**
 * Repository that updates and retrieves data from Room database.
 * Data conversion from java object (derived from JSON) to Room Entity is also called here
 * */
public class WeatherRepository {

    private final WeatherDao weatherDao;
    private final WeatherMapper weatherMapper;

    @Inject
    public WeatherRepository(
            WeatherDao weatherDao,
         WeatherMapper weatherMapper) {

        this.weatherDao = weatherDao;
        this.weatherMapper = weatherMapper;

    }

    public LiveData<List<WeatherEntity>> weatherLiveData(){
        return weatherDao.getAllLiveData();
    }

    public Long updateCurrentWeather(GeoCity geoCity, CurrentWeather currentWeather){
        weatherDao.deleteAll();
        return weatherDao.insert(weatherMapper.currentWeatherToWeatherEntity(geoCity, currentWeather));
    }

}
