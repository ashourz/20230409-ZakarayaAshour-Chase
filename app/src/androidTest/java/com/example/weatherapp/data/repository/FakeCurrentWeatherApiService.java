package com.example.weatherapp.data.repository;

import com.example.weatherapp.data.model.CurrentWeather;
import com.example.weatherapp.data.model.GeoCity;
import com.example.weatherapp.data.model.weather_components.Main;
import com.example.weatherapp.data.model.weather_components.Weather;
import com.example.weatherapp.data.repository.baseclass.WeatherApiServiceBaseClass;

import java.util.List;
import java.util.Optional;

/**
 * FAKE
 * Makes Retrofit call to api and retrieves maps data from JSON to java object.
 * Further development could include different outputs depending on the response failure.
 * At the moment, all failures or empty responses will result in a null or empty optional value.
 */
public class FakeCurrentWeatherApiService extends WeatherApiServiceBaseClass {

    private Boolean getCurrentWeatherDataReturnsNull = false;

    public void setCurrentWeatherDataReturnsNull(Boolean bool) {
        getCurrentWeatherDataReturnsNull = bool;
    }

    private CurrentWeather getFakeCurrentWeather() {
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setDt(1619288400L);

        GeoCity city = new GeoCity();
        city.setName("London");
        city.setCountry("GB");

        Main main1 = new Main();
        main1.setTemp(10.0);
        main1.setFeelsLike(8.0);
        main1.setTempMin(9.0);
        main1.setTempMax(11.0);
        currentWeather.setMain(main1);

        Weather weather1 = new Weather();
        weather1.setIcon("800");
        weather1.setDescription("clear sky");

        currentWeather.setWeather(List.of(weather1));
        return currentWeather;
    }

    /**
     * Get current weather data or return null on null body or exception
     */
    @Override
    public Optional<CurrentWeather> getCurrentWeatherData(Double latitude, Double longitude) {
       if(getCurrentWeatherDataReturnsNull){
           return Optional.empty();
       }else{
           return Optional.of(getFakeCurrentWeather());
       }
    }
}
