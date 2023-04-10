package com.example.weatherapp.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.weatherapp.data.room.entity.WeatherEntity;

import java.util.List;

/**
 * DAO for Room Weather Table
 * */
@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather_table WHERE uid NOT IN (SELECT uid FROM weather_table ORDER BY datetime LIMIT 1) ORDER BY datetime")
    LiveData<List<WeatherEntity>> getAllButFirstLiveData();

    @Query("SELECT * FROM weather_table ORDER BY datetime LIMIT 1")
    LiveData<List<WeatherEntity>> getFirstLiveData();

    @Query("SELECT * FROM weather_table")
    List<WeatherEntity> getAll();

    /**
     * Returns Long rowId of inserted Element or -1 if nothing inserted
     * */
    @Insert
    Long insert(WeatherEntity weatherEntity);

    /**
     * Returns list of Long rowIds of inserted Elements
     * */
    @Insert
    List<Long> insertAll(List<WeatherEntity> weatherEntityList);

    @Query("DELETE FROM weather_table")
    void deleteAll();
}
