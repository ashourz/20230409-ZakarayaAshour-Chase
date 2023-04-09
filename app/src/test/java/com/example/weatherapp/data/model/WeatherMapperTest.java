package com.example.weatherapp.data.model;

import static org.junit.Assert.assertEquals;

import com.example.weatherapp.data.model.weather_components.Main;
import com.example.weatherapp.data.model.weather_components.Weather;
import com.example.weatherapp.data.room.entity.WeatherEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RunWith(JUnit4.class)
public class WeatherMapperTest {
    private WeatherMapper weatherMapper;

    @Before
    public void setup() {
        weatherMapper = new WeatherMapper();
    }


    @Test
    public void currentWeatherToWeatherEntityList_withValidInput_shouldReturnWeatherEntity() {
        // Arrange
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

        // Act
        WeatherEntity weatherEntity1 = weatherMapper.currentWeatherToWeatherEntity(city, currentWeather);

        // Assert
        assertEquals(LocalDateTime.ofEpochSecond(1619288400L, 0, ZoneOffset.UTC), weatherEntity1.datetime);
        assertEquals("London", weatherEntity1.city);
        assertEquals("GB", weatherEntity1.country);
        assertEquals("800", weatherEntity1.icon);
        assertEquals("clear sky", weatherEntity1.description);
        assertEquals(10.0, weatherEntity1.temp, 0.001);
        assertEquals(8.0, weatherEntity1.feelsLike, 0.001);
        assertEquals(9.0, weatherEntity1.tempMin, 0.001);
        assertEquals(11.0, weatherEntity1.tempMax, 0.001);
    }

    @Test
    public void currentWeatherToWeatherEntityList_withEmptyList_shouldReturnIconIdZeroEmptyDescription() {
        // Arrange
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setDt(1619288400L);

        GeoCity city = new GeoCity();
        city.setName("London");
        city.setState("");
        city.setCountry("GB");

        Main main1 = new Main();
        main1.setTemp(10.0);
        main1.setFeelsLike(8.0);
        main1.setTempMin(9.0);
        main1.setTempMax(11.0);
        currentWeather.setMain(main1);

        currentWeather.setWeather(List.of());

        // Act
        WeatherEntity weatherEntity1 = weatherMapper.currentWeatherToWeatherEntity(city, currentWeather);

        // Assert
        assertEquals(LocalDateTime.ofEpochSecond(1619288400L, 0, ZoneOffset.UTC), weatherEntity1.datetime);
        assertEquals("London", weatherEntity1.city);
        assertEquals("GB", weatherEntity1.country);
        assertEquals("", weatherEntity1.icon);
        assertEquals("", weatherEntity1.description);
        assertEquals(10.0, weatherEntity1.temp, 0.001);
        assertEquals(8.0, weatherEntity1.feelsLike, 0.001);
        assertEquals(9.0, weatherEntity1.tempMin, 0.001);
        assertEquals(11.0, weatherEntity1.tempMax, 0.001);
    }

}