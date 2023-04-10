package com.example.weatherapp.data.repository;


import com.example.weatherapp.data.model.GeoCity;
import com.example.weatherapp.data.repository.baseclass.CityApiServiceBaseClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * FAKE
 * Makes Retrofit call to api and retrieves maps data from JSON to java object.
 * Further development could include different outputs depending on the response failure.
 * At the moment, all failures or empty responses will result in a null or empty optional value.
 * */
public class FakeGeoCityApiService extends CityApiServiceBaseClass {

    private Boolean getCityDataReturnsNull = false;

    public void setCityDataReturnsNull(Boolean bool) {
        getCityDataReturnsNull = bool;
    }

    private Boolean getCityNameDataReturnsNull = false;

    public void setCityNameDataReturnsNull(Boolean bool) {
        getCityNameDataReturnsNull = bool;
    }

    private List<GeoCity> getFakeGeoCityList(){
        GeoCity city = new GeoCity();
        city.setName("New York");
        city.setState("New York");
        city.setCountry("US");
        List<GeoCity> geoCityList = new ArrayList<GeoCity>();
        geoCityList.add(city);
        return geoCityList;
    }

    @Override
    public Optional<List<GeoCity>> getCityData(String cityName) {
        if(getCityDataReturnsNull){
            return Optional.empty();
        }else{
            return Optional.of(getFakeGeoCityList());
        }
    }

    @Override
    public Optional<List<GeoCity>> getCityNameData(Double latitude, Double longitude) {
        if(getCityNameDataReturnsNull){
            return Optional.empty();
        }else{
            return Optional.of(getFakeGeoCityList());
        }
    }
}
