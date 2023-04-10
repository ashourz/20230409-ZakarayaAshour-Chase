package com.example.weatherapp.presentation

import android.graphics.Bitmap
import com.example.weatherapp.data.room.entity.WeatherEntity

data class WeatherDisplayData(
    val weatherEntity: WeatherEntity,
    val weatherBitmap: Bitmap?
)