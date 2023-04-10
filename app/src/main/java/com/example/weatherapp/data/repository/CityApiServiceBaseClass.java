package com.example.weatherapp.data.repository;


import com.example.weatherapp.data.model.GeoCity;

import java.util.List;
import java.util.Optional;

/**
 * CityApiServiceInterface
 */
public class CityApiServiceBaseClass {

    public Optional<List<GeoCity>> getCityData(String cityName){
        return Optional.empty();
    };

    public Optional<List<GeoCity>> getCityNameData(Double latitude, Double longitude){
        return Optional.empty();
    };
}