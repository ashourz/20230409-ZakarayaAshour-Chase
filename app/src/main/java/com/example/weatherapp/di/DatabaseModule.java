package com.example.weatherapp.di;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.weatherapp.data.model.WeatherMapper;
import com.example.weatherapp.data.room.dao.WeatherDao;
import com.example.weatherapp.data.room.database.WeatherDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Singleton
    @Provides
    public WeatherDao provideWeatherDao(WeatherDatabase weatherDatabase) {
        return weatherDatabase.weatherDao();
    }

    @Singleton
    @Provides
    public WeatherDatabase providesWeatherDatabase(@ApplicationContext Context applicationContext) {
        return Room
                .databaseBuilder(
                        applicationContext,
                        WeatherDatabase.class,
                        "weather_database")
                .fallbackToDestructiveMigration()
                .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
                .build();
    }

    @Singleton
    @Provides
    public WeatherMapper provideWeatherMapper(WeatherDatabase weatherDatabase) {return new WeatherMapper();}
}


