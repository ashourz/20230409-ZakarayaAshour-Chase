package com.example.weatherapp.data.repository;

import com.example.weatherapp.data.model.FiveDayForecast;
import com.example.weatherapp.data.model.weather_components.City;
import com.example.weatherapp.data.model.weather_components.Main;
import com.example.weatherapp.data.model.weather_components.Weather;
import com.example.weatherapp.data.model.weather_components.WeatherListItem;
import com.example.weatherapp.data.repository.baseclass.ForecastApiServiceBaseClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * FAKE
 * Makes Retrofit call to api and retrieves maps data from JSON to java object.
 * Further development could include different outputs depending on the response failure.
 * At the moment, all failures or empty responses will result in a null or empty optional value.
 */
public class FakeForecastWeatherApiService extends ForecastApiServiceBaseClass {

    private Boolean getForecastDataReturnsNull = false;

    public void setForecastDataReturnsNull(Boolean bool) {
        getForecastDataReturnsNull = bool;
    }


    /**
     * Get current weather data or return null on null body or exception
     */
    @Override
    public Optional<FiveDayForecast> getFiveDayForecastData(Double latitude, Double longitude) {
       if(getForecastDataReturnsNull){
           return Optional.empty();
       }else{
           return Optional.of(getFakeForecast());
       }
    }

    private FiveDayForecast getFakeForecast() {
        List<WeatherListItem> weatherListItems = new ArrayList<>();
        weatherListItems.add(createMockWeatherListItem());

        FiveDayForecast fiveDayForecast = new FiveDayForecast();
        fiveDayForecast.setList(weatherListItems);
        fiveDayForecast.setCity(createMockCity());

        return fiveDayForecast;
    }
    private WeatherListItem createMockWeatherListItem() {

        WeatherListItem weatherListItem = new WeatherListItem();
        weatherListItem.setDt(1648834800L);
        weatherListItem.setMain(createMockMain());
        weatherListItem.setWeather(createMockWeatherList());

        return weatherListItem;
    }
    private Main createMockMain() {
        Main main = new Main();
        main.setTemp(25.0);
        main.setFeelsLike(28.0);
        main.setTempMin(23.0);
        main.setTempMax(27.0);

        return main;
    }
    private List<Weather> createMockWeatherList() {

        List<Weather> weatherList = new ArrayList<>();
        Weather weather = new Weather();
        weather.setId(800);
        weather.setDescription("clear sky");
        weatherList.add(weather);

        return weatherList;
    }
    private City createMockCity() {
        City city = new City();
        city.setName("Test City");
        city.setCountry("TC");

        return city;
    }
}
