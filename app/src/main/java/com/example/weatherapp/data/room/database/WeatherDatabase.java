package com.example.weatherapp.data.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.weatherapp.data.room.dao.WeatherDao;
import com.example.weatherapp.data.room.entity.WeatherEntity;

@Database(entities = {WeatherEntity.class}, version = 1)
@TypeConverters(RoomTypeConverter.class)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();

}