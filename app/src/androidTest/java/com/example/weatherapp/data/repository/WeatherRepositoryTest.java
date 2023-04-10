package com.example.weatherapp.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.weatherapp.data.model.CurrentWeather;
import com.example.weatherapp.data.model.FiveDayForecast;
import com.example.weatherapp.data.model.GeoCity;
import com.example.weatherapp.data.model.WeatherMapper;
import com.example.weatherapp.data.model.weather_components.City;
import com.example.weatherapp.data.model.weather_components.Main;
import com.example.weatherapp.data.model.weather_components.Weather;
import com.example.weatherapp.data.model.weather_components.WeatherListItem;
import com.example.weatherapp.data.room.dao.WeatherDao;
import com.example.weatherapp.data.room.entity.WeatherEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@RunWith(AndroidJUnit4.class)
@HiltAndroidTest
public class WeatherRepositoryTest {

    @Rule
    public HiltAndroidRule hiltAndroidRule = new HiltAndroidRule(this);

    @Inject
    WeatherDao weatherDao;

    @Inject
    RepositoryBaseClass weatherRepository;

    @Inject
    WeatherMapper weatherMapper;

    @Before
    public void setUp() {
        hiltAndroidRule.inject();
    }

    @After
    public void tearDown() {
        weatherDao.deleteAll();
    }


    @Test
    public void updateCurrentWeather_withValidInput_shouldInsertIntoWeatherDao() {
        // Arrange
        CurrentWeather currentWeather = createMockCurrentWeather();
        GeoCity city = createMockGeoCity();
        FiveDayForecast fiveDayForecast = createMockFiveDayForecast();

        // Act
        Long insertedId = weatherRepository.updateWeather(city, currentWeather,fiveDayForecast);
        assertNotNull(insertedId);
        assertNotEquals(Optional.of(-1L), insertedId);

        // Assert
        List<WeatherEntity> actualWeatherEntities = weatherDao.getAll();
        WeatherEntity expectedWeatherEntity = weatherMapper.currentWeatherToWeatherEntity(city, currentWeather);
        for(WeatherEntity item : actualWeatherEntities){
            assertEquals(item.city, expectedWeatherEntity.city);
            assertEquals(item.state, expectedWeatherEntity.state);
            assertEquals(item.country, expectedWeatherEntity.country);
            assertEquals(item.icon, expectedWeatherEntity.icon);
            assertEquals(item.feelsLike, expectedWeatherEntity.feelsLike);
            assertEquals(item.temp, expectedWeatherEntity.temp);
            assertEquals(item.tempMin, expectedWeatherEntity.tempMin);
            assertEquals(item.tempMax, expectedWeatherEntity.tempMax);
            assertEquals(item.description, expectedWeatherEntity.description);
            assertEquals(item.datetime, expectedWeatherEntity.datetime);
        }
    }

    private CurrentWeather createMockCurrentWeather() {
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setDt(1648834800L);
        currentWeather.setWeather(createMockWeatherList());
        currentWeather.setMain(createMockMain());
        return currentWeather;
    }

    private FiveDayForecast createMockFiveDayForecast() {
        List<WeatherListItem> weatherListItems = new ArrayList<>();
        weatherListItems.add(createMockWeatherListItem());

        FiveDayForecast fiveDayForecast = new FiveDayForecast();
        fiveDayForecast.setList(weatherListItems);
        fiveDayForecast.setCity(createMockCity());

        return fiveDayForecast;
    }

    private List<Weather> createMockWeatherList() {

        List<Weather> weatherList = new ArrayList<>();
        Weather weather = new Weather();
        weather.setId(800);
        weather.setDescription("clear sky");
        weatherList.add(weather);

        return weatherList;
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


    private GeoCity createMockGeoCity() {
        GeoCity city = new GeoCity();
        city.setName("Test City");
        city.setCountry("TC");

        return city;
    }

    private City createMockCity() {
        City city = new City();
        city.setName("Test City");
        city.setCountry("TC");

        return city;
    }


}