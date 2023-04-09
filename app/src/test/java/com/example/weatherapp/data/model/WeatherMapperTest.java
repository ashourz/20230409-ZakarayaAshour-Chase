package com.example.weatherapp.data.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.example.weatherapp.data.model.weather_components.City;
import com.example.weatherapp.data.model.weather_components.Main;
import com.example.weatherapp.data.model.weather_components.Weather;
import com.example.weatherapp.data.model.weather_components.WeatherListItem;
import com.example.weatherapp.data.room.entity.WeatherEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(JUnit4.class)
public class WeatherMapperTest {
    private WeatherMapper weatherMapper;

    @Before
    public void setup() {
        weatherMapper = new WeatherMapper();
    }

    @Test
    public void fiveDayForecastToWeatherEntityList_withValidInput_shouldReturnWeatherEntityList() {
        // Arrange
        FiveDayForecast fiveDayForecast = new FiveDayForecast();
        City city = new City();
        city.setName("London");
        city.setCountry("GB");
        fiveDayForecast.setCity(city);
        WeatherListItem weatherListItem1 = new WeatherListItem();
        weatherListItem1.setDt(1619288400L);
        Main main1 = new Main();
        main1.setTemp(10.0);
        main1.setFeelsLike(8.0);
        main1.setTempMin(9.0);
        main1.setTempMax(11.0);
        weatherListItem1.setMain(main1);
        Weather weather1 = new Weather();
        weather1.setIcon("800");
        weather1.setDescription("clear sky");
        weatherListItem1.setWeather(Collections.singletonList(weather1));
        WeatherListItem weatherListItem2 = new WeatherListItem();
        weatherListItem2.setDt(1619299200L);
        Main main2 = new Main();
        main2.setTemp(11.0);
        main2.setFeelsLike(9.0);
        main2.setTempMin(10.0);
        main2.setTempMax(12.0);
        weatherListItem2.setMain(main2);
        Weather weather2 = new Weather();
        weather2.setIcon("803");
        weather2.setDescription("broken clouds");
        weatherListItem2.setWeather(Collections.singletonList(weather2));
        fiveDayForecast.setList(Arrays.asList(weatherListItem1, weatherListItem2));

        // Act
        List<WeatherEntity> result = weatherMapper.fiveDayForecastToWeatherEntityList(fiveDayForecast);

        // Assert
        assertEquals(2, result.size());
        WeatherEntity weatherEntity1 = result.get(0);
        assertEquals(LocalDateTime.ofEpochSecond(1619288400L, 0, ZoneOffset.UTC), weatherEntity1.datetime);
        assertEquals("London", weatherEntity1.city);
        assertEquals("GB", weatherEntity1.country);
        assertEquals("800", weatherEntity1.icon);
        assertEquals("clear sky", weatherEntity1.description);
        assertEquals(10.0, weatherEntity1.temp, 0.001);
        assertEquals(8.0, weatherEntity1.feelsLike, 0.001);
        assertEquals(9.0, weatherEntity1.tempMin, 0.001);
        assertEquals(11.0, weatherEntity1.tempMax, 0.001);
        WeatherEntity weatherEntity2 = result.get(1);
        assertEquals(LocalDateTime.ofEpochSecond(1619299200L, 0, ZoneOffset.UTC), weatherEntity2.datetime);
        assertEquals("London", weatherEntity2.city);
        assertEquals("GB", weatherEntity2.country);
        assertEquals("803", weatherEntity2.icon);
        assertEquals("broken clouds", weatherEntity2.description);
        assertEquals(11.0, weatherEntity2.temp, 0.001);
        assertEquals(9.0, weatherEntity2.feelsLike, 0.001);
        assertEquals(10.0, weatherEntity2.tempMin, 0.001);
        assertEquals(12.0, weatherEntity2.tempMax, 0.001);
    }
    @Test
    public void fiveDayForecastToWeatherEntityList_withNullInput_shouldReturnEmptyList() {
        assertThrows(NullPointerException.class, () -> {List<WeatherEntity> weatherEntityList = weatherMapper.fiveDayForecastToWeatherEntityList(null);});
    }

    @Test
    public void fiveDayForecastToWeatherEntityList_withEmptyList_shouldReturnEmptyList() {
        FiveDayForecast fiveDayForecast = new FiveDayForecast();
        fiveDayForecast.setList(Collections.emptyList());

        List<WeatherEntity> weatherEntityList = weatherMapper.fiveDayForecastToWeatherEntityList(fiveDayForecast);

        assertNotNull(weatherEntityList);
        assertTrue(weatherEntityList.isEmpty());
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
    public void currentWeatherToWeatherEntityList_withNullInput_shouldReturnEmptyList() {
        assertThrows(NullPointerException.class, () -> {WeatherEntity weatherEntity = weatherMapper.currentWeatherToWeatherEntity(null, null);});
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