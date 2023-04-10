package com.example.weatherapp.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.data.room.entity.WeatherEntity
import com.example.weatherapp.extensions.toDisplayTimeFormat
import java.util.*

/**
 * Weather data card for forecast screen
 * */
@Composable
fun PortraitForecastWeatherCard(
    weatherEntity: WeatherEntity,
    weatherBitmap: Bitmap?
) {
    val localContext = LocalContext.current

    Card(elevation = 8.dp) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Max).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(0.3f),
            horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    contentAlignment = Alignment.BottomCenter
                ) {
                    weatherBitmap?.let { weatherBitmap ->
                        Image(
                            modifier = Modifier.size(100.dp),
                            bitmap = weatherBitmap.asImageBitmap(),
                            contentDescription = stringResource(R.string.weather_icon),
                        )
                    }
                    Text(text = weatherEntity.description.split(" ").joinToString(" ") { it.capitalize(Locale.getDefault()) }, style = MaterialTheme.typography.body2)
                }
            }
            Column(modifier = Modifier.weight(0.3f),
            horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.temperature_value) },
                    text = weatherEntity.temp.toInt().toString().plus(" \u2109"),
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Column(modifier = Modifier.weight(0.4f)) {
                Text(text = weatherEntity.datetime.toDisplayTimeFormat(), style = MaterialTheme.typography.body1)
                Text(
                    modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.feels_like) }, text = stringResource(R.string.feels_like_prefix).plus(
                        weatherEntity.feelsLike.toInt().toString().plus(
                            " " +
                                    "\u2109"
                        )
                    ),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.low_temp) },
                    text = stringResource(R.string.low_temp_prefix).plus(weatherEntity.tempMin.toInt().toString().plus(" \u2109")),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.high_temp) },
                    text = stringResource(R.string.high_temp_prefix).plus(weatherEntity.tempMax.toInt().toString().plus(" \u2109")),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun LandscapeForecastWeatherCard(
    weatherEntity: WeatherEntity,
    weatherBitmap: Bitmap?
) {
    val localContext = LocalContext.current

    Card(
    elevation = 8.dp) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Max).padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Box(
                    contentAlignment = Alignment.BottomCenter
                ) {
                    weatherBitmap?.let { weatherBitmap ->
                        Image(
                            modifier = Modifier.size(100.dp),
                            bitmap = weatherBitmap.asImageBitmap(),
                            contentDescription = stringResource(R.string.weather_icon),
                        )
                    }
                    Text(text = weatherEntity.description.split(" ").joinToString(" ") { it.capitalize(Locale.getDefault()) }, style = MaterialTheme.typography.body2)
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Row(modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center) {
                Column() {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.temperature_value) },
                        text = weatherEntity.temp.toInt().toString().plus(" \u2109"),
                        style = MaterialTheme.typography.h4
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Row(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.width(IntrinsicSize.Max),
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = weatherEntity.datetime.toDisplayTimeFormat(), style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.feels_like) }, text = stringResource(R.string.feels_like_prefix).plus(
                            weatherEntity.feelsLike.toInt().toString().plus(
                                " " +
                                        "\u2109"
                            )
                        ),
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.low_temp) },
                        text = stringResource(R.string.low_temp_prefix).plus(weatherEntity.tempMin.toInt().toString().plus(" \u2109")),
                        style =
                        MaterialTheme.typography.body2
                    )
                    Text(
                        modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.high_temp) },
                        text = stringResource(R.string.high_temp_prefix).plus(weatherEntity.tempMax.toInt().toString().plus(" \u2109")),
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}