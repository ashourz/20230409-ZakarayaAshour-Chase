package com.example.weatherapp.data.room.database;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class RoomTypeConverter {
    @TypeConverter
    public static LocalDateTime fromLong(Long value) {
        return value == null ? null : LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC);
    }

    @TypeConverter
    public static Long fromLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
