package com.example.weatherapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.data.model.CurrentWeather;
import com.example.weatherapp.data.model.GeoCity;
import com.example.weatherapp.data.model.WeatherMapper;
import com.example.weatherapp.data.room.entity.WeatherEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FAKE
 * Repository that updates and retrieves data from Room database.
 * Data conversion from java object (derived from JSON) to Room Entity is also called here
 * */
public class FakeWeatherRepository extends RepositoryBaseClass {

    private final WeatherMapper weatherMapper = new WeatherMapper();

    private MutableLiveData<List<WeatherEntity>> fakeWeatherLiveData = new MutableLiveData<List<WeatherEntity>>();
    @Override
    public LiveData<List<WeatherEntity>> weatherLiveData(){
        return fakeWeatherLiveData;
    }
    private Boolean getSuccessfulUpdateCurrentWeather = false;

    private void setSuccessfulUpdateCurrentWeather(Boolean bool){
        getSuccessfulUpdateCurrentWeather = bool;
    }
    @Override
    public Long updateCurrentWeather(GeoCity geoCity, CurrentWeather currentWeather){

        if(getSuccessfulUpdateCurrentWeather){
            fakeWeatherLiveData.setValue(Collections.emptyList());
            List<WeatherEntity> weatherEntityList = new ArrayList<WeatherEntity>();
            weatherEntityList.add(weatherMapper.currentWeatherToWeatherEntity(geoCity, currentWeather));
            fakeWeatherLiveData.setValue(weatherEntityList);
            return 1L;
        }else{
            fakeWeatherLiveData.setValue(Collections.emptyList());
            return -1L;
        }
    }
}
