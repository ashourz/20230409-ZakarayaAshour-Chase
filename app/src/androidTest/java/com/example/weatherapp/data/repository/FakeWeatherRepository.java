package com.example.weatherapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.data.model.CurrentWeather;
import com.example.weatherapp.data.model.FiveDayForecast;
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

    private MutableLiveData<List<WeatherEntity>> fakeForecastWeatherLiveData = new MutableLiveData<List<WeatherEntity>>();

    private MutableLiveData<List<WeatherEntity>> fakeWeatherLiveData = new MutableLiveData<List<WeatherEntity>>();

    @Override
    public LiveData<List<WeatherEntity>> forecastWeatherLiveData(){
        return fakeForecastWeatherLiveData;
    }

    @Override
    public LiveData<List<WeatherEntity>> currentWeatherLiveData(){
        return fakeWeatherLiveData;
    }
    private Boolean getSuccessfulUpdateCurrentWeather = false;

    private void setSuccessfulUpdateCurrentWeather(Boolean bool){
        getSuccessfulUpdateCurrentWeather = bool;
    }
    @Override
    public Long updateWeather(GeoCity geoCity, CurrentWeather currentWeather, FiveDayForecast fiveDayForecast){

        if(getSuccessfulUpdateCurrentWeather){
            fakeForecastWeatherLiveData.setValue(Collections.emptyList());
            List<WeatherEntity> weatherEntityList = new ArrayList<WeatherEntity>();
            weatherEntityList.addAll(weatherMapper.fiveDayForecastToWeatherEntityList(fiveDayForecast));
            fakeForecastWeatherLiveData.setValue(weatherEntityList);

            fakeWeatherLiveData.setValue(Collections.emptyList());
            List<WeatherEntity> currentWeatherEntityList = new ArrayList<WeatherEntity>();
            currentWeatherEntityList.add(weatherMapper.currentWeatherToWeatherEntity(geoCity, currentWeather));
            fakeWeatherLiveData.setValue(currentWeatherEntityList);
            return 1L;
        }else{
            fakeWeatherLiveData.setValue(Collections.emptyList());
            return -1L;
        }
    }

//    @Override
//    public List<Long> updateFiveDayForecast(FiveDayForecast fiveDayForecast){
//        if(getSuccessfulUpdateCurrentWeather){
//            fakeForecastWeatherLiveData.setValue(Collections.emptyList());
//            List<WeatherEntity> weatherEntityList = new ArrayList<WeatherEntity>();
//            weatherEntityList.addAll(weatherMapper.fiveDayForecastToWeatherEntityList(fiveDayForecast));
//            fakeForecastWeatherLiveData.setValue(weatherEntityList);
//            List<Long> returnList = new ArrayList<Long>();
//            returnList.add(1L);
//            return returnList;
//        }else{
//            fakeForecastWeatherLiveData.setValue(Collections.emptyList());
//            List<Long> returnList = new ArrayList<Long>();
//            returnList.add(-1L);
//            return returnList;
//        }
//    }
}
