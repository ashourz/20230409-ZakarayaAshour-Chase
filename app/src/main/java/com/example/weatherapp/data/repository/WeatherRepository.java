package com.example.weatherapp.data.repository;

import androidx.lifecycle.LiveData;

import com.example.weatherapp.data.model.CurrentWeather;
import com.example.weatherapp.data.model.FiveDayForecast;
import com.example.weatherapp.data.model.GeoCity;
import com.example.weatherapp.data.model.WeatherMapper;
import com.example.weatherapp.data.room.dao.WeatherDao;
import com.example.weatherapp.data.room.entity.WeatherEntity;

import java.util.List;

/**
 * Repository that updates and retrieves data from Room database.
 * Data conversion from java object (derived from JSON) to Room Entity is also called here
 * */
public class WeatherRepository extends RepositoryBaseClass {
    private final WeatherDao weatherDao;
    private final WeatherMapper weatherMapper;

    public WeatherRepository(
            WeatherDao weatherDao,
         WeatherMapper weatherMapper) {

        this.weatherDao = weatherDao;
        this.weatherMapper = weatherMapper;
    }

    @Override
    public LiveData<List<WeatherEntity>> forecastWeatherLiveData(){
        return weatherDao.getAllButFirstLiveData();
    }

    @Override
    public LiveData<List<WeatherEntity>> currentWeatherLiveData(){
        return weatherDao.getFirstLiveData();
    }

    @Override
    public Long updateWeather(GeoCity geoCity, CurrentWeather currentWeather, FiveDayForecast fiveDayForecast){
        Long returnInt = 0L;
        weatherDao.deleteAll();
        returnInt += weatherDao.insert(weatherMapper.currentWeatherToWeatherEntity(geoCity, currentWeather));
        returnInt += weatherDao.insertAll(weatherMapper.fiveDayForecastToWeatherEntityList(fiveDayForecast)).stream().count();
        return returnInt;
    }

}
