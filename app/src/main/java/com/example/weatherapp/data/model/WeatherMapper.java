package com.example.weatherapp.data.model;

import com.example.weatherapp.data.model.weather_components.Weather;
import com.example.weatherapp.data.room.entity.WeatherEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 * Maps java object (derived from JSON) to Room Entity
 * */
public class WeatherMapper {
    public WeatherMapper(){}

    public WeatherEntity currentWeatherToWeatherEntity(GeoCity city, CurrentWeather currentWeather) {
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(currentWeather.getDt(), 0, ZoneOffset.UTC);
        Optional<Weather> firstWeather = currentWeather.getWeather()
                                                       .stream()
                                                       .findFirst();
        String weatherIconId = firstWeather.map(Weather::getIcon)
                                        .orElse("");
        String weatherDescription = firstWeather.map(Weather::getDescription)
                                                .orElse("");

        String state = "";
        if(city.getState() != null){
            state = city.getState();
        }

        return new WeatherEntity(
                        dateTime,
                        city.getName(),
                        state,
                        city.getCountry(),
                        weatherIconId,
                        weatherDescription,
                        currentWeather.getMain()
                                       .getTemp(),
                        currentWeather.getMain()
                                       .getFeelsLike(),
                        currentWeather.getMain()
                                       .getTempMin(),
                        currentWeather.getMain()
                                       .getTempMax()
                );

    }
}
