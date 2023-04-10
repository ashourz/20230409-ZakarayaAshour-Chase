package com.example.weatherapp.ui.screens.forecast

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.weatherapp.extensions.toDisplayDateFormat
import com.example.weatherapp.presentation.DataViewModel
import com.example.weatherapp.ui.screens.LandscapeForecastWeatherCard
import com.example.weatherapp.ui.screens.PortraitForecastWeatherCard


/**
 * Screen component to show current weather 5 day - 3 hour weather forecast
 * */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FiveDayForecastScreen(
    dataViewModel: DataViewModel
) {
    val forecastWeatherData by dataViewModel.forecastWeatherDataFlow.collectAsState(initial = emptyList())
    val forecastBitmapList by dataViewModel.forecastWeatherBitmapFlow.collectAsState(initial = emptyList())
    val orientation = LocalConfiguration.current.orientation
    LazyPortraitLandscapeItems {
        forecastWeatherData.withIndex().groupBy { it.value.datetime.toDisplayDateFormat() }.forEach { localDate, indexedValues ->
                stickyHeader {
                    Surface() {
                        Text(text = localDate)
                    }
                }
            indexedValues.forEach { indexedValue ->
                when (orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        item {
                            LandscapeForecastWeatherCard(
                                weatherEntity = indexedValue.value,
                                weatherBitmap = forecastBitmapList.getOrNull(indexedValue.index)
                            )
                        }
                    }
                    Configuration.ORIENTATION_PORTRAIT,
                    Configuration.ORIENTATION_SQUARE,
                    Configuration.ORIENTATION_UNDEFINED -> {

                        item {
                            PortraitForecastWeatherCard(
                                weatherEntity = indexedValue.value,
                                weatherBitmap = forecastBitmapList.getOrNull(indexedValue.index)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LazyPortraitLandscapeItems(
    items: (LazyListScope.() -> Unit),
) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items()
            }
        }
        Configuration.ORIENTATION_PORTRAIT,
        Configuration.ORIENTATION_SQUARE,
        Configuration.ORIENTATION_UNDEFINED -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items()
            }
        }
    }
}