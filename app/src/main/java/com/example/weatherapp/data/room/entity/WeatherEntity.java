package com.example.weatherapp.data.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity(tableName = "weather_table")
public class WeatherEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @NotNull
    @ColumnInfo(name = "datetime")
    public LocalDateTime datetime;

    @NotNull
    @ColumnInfo(name = "city")
    public String city;

    @NotNull
    @ColumnInfo(name = "state")
    public String state;

    @NotNull
    @ColumnInfo(name = "country")
    public String country;

    @NotNull
    @ColumnInfo(name = "icon")
    public String icon;

    @NotNull
    @ColumnInfo(name = "description")
    public String description;

    @NotNull
    @ColumnInfo(name = "temp")
    public Double temp;

    @NotNull
    @ColumnInfo(name = "feels_like")
    public Double feelsLike;

    @NotNull
    @ColumnInfo(name = "temp_min")
    public Double tempMin;

    @NotNull
    @ColumnInfo(name = "temp_max")
    public Double tempMax;

    public WeatherEntity(
            @NonNull LocalDateTime datetime,
            @NonNull String city,
            @NonNull String state,
            @NonNull String country,
            @NonNull String icon,
            @NonNull String description,
            @NonNull Double temp,
            @NonNull Double feelsLike,
            @NonNull Double tempMin,
            @NonNull Double tempMax){
        this.datetime = datetime;
        this.city = city;
        this.state = state;
        this.country = country;
        this.icon = icon;
        this.description = description;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }
}
