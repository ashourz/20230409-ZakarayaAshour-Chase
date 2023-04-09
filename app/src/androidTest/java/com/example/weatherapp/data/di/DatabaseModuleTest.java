package com.example.weatherapp.data.di;

import static org.junit.Assert.*;

import androidx.room.RoomDatabase;

import com.example.weatherapp.data.room.dao.WeatherDao;
import com.example.weatherapp.data.room.database.WeatherDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@RunWith(JUnit4.class)
@HiltAndroidTest
public class DatabaseModuleTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Inject
    WeatherDao weatherDao;

    @Inject
    WeatherDatabase weatherDatabase;

    @Before
    public void setUp() throws Exception {
        hiltRule.inject();
    }

    @Test
    public void testWeatherDao() {
        assertNotNull(weatherDao);
        assertEquals(weatherDatabase.weatherDao(), weatherDao);
    }

    @Test
    public void testWeatherDatabase() {
        assertNotNull(weatherDatabase);
        assertEquals("weather_database", weatherDatabase.getOpenHelper().getDatabaseName());
    }
}