package com.example.weatherapp.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.weatherapp.data.room.entity.WeatherEntity;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather_table ORDER BY datetime")
    LiveData<List<WeatherEntity>> getAllLiveData();

    @Query("SELECT * FROM weather_table")
    List<WeatherEntity> getAll();
    /**
     * Returns list of Long rowIds of inserted Elements
     * */
    @Insert
    List<Long> insertAll(List<WeatherEntity> weatherEntityList);
    @Insert
    Long insert(WeatherEntity weatherEntity);
    @Delete
    void delete(WeatherEntity user);

    @Query("DELETE FROM weather_table")
    void deleteAll();
}
