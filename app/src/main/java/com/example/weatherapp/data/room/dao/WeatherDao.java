package com.example.weatherapp.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.weatherapp.data.room.entity.WeatherEntity;

import java.util.List;

/**
 * DAO for Room Weather Table
 * */
@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather_table ORDER BY datetime")
    LiveData<List<WeatherEntity>> getAllLiveData();

    @Query("SELECT * FROM weather_table")
    List<WeatherEntity> getAll();

    /**
     * Returns Long rowId of inserted Element or -1 if nothing inserted
     * */
    @Insert
    Long insert(WeatherEntity weatherEntity);

    @Query("DELETE FROM weather_table")
    void deleteAll();
}
