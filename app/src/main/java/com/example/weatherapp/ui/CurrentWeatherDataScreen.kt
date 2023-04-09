package com.example.weatherapp.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R
import com.example.weatherapp.data.room.entity.WeatherEntity
import com.example.weatherapp.extensions.getStateCountryDisplayName
import com.example.weatherapp.extensions.toDisplayFormat
import com.example.weatherapp.presentation.DataViewModel
import com.example.weatherapp.ui.components.PortraitLandscapeScreen
import com.example.weatherapp.ui.icons.ComposableIconConstants
import com.example.weatherapp.ui.icons.SizedIcon
import java.util.Locale

@Composable
fun CurrentWeatherDataScreen(
    weatherEntity: WeatherEntity,
    viewModel: DataViewModel = viewModel()

) {
    val currentWeatherBitmap: Bitmap? by viewModel.currentWeatherBitmapFlow.collectAsState(null)
    val localContext = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        SizedIcon(
            iconDrawable = ComposableIconConstants.locationPinIcon,
            contentDescription = stringResource(R.string.location_icon),
        )
        Column() {
            Text(
                modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.current_data_name) }, text = weatherEntity.city, style = MaterialTheme.typography.subtitle1,
                fontWeight =
                FontWeight
                    .Bold
            )
            Text(text = weatherEntity.getStateCountryDisplayName(), style = MaterialTheme.typography.caption)
        }
    }
    Text(
        modifier = Modifier.padding(start = ComposableIconConstants.defaultIconSize), text = stringResource(R.string.weather_as_of_prefix).plus(weatherEntity.datetime.toDisplayFormat()), style = MaterialTheme.typography
            .caption
    )
    PortraitLandscapeScreen(
        lazyPrimary = {

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(150.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        currentWeatherBitmap?.let { weatherBitmap ->
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                bitmap = weatherBitmap.asImageBitmap(),
                                contentDescription = stringResource(R.string.weather_icon),
                            )
                        }
                        Text(text = weatherEntity.description.split(" ").joinToString(" ") { it.capitalize(Locale.getDefault()) }, style = MaterialTheme.typography.body2)

                    }

                    Column() {
                        Text(
                            modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.temperature_value) }, text = weatherEntity.temp.toInt().toString().plus(" \u2109"), style = MaterialTheme
                                .typography.h1
                        )
                    }
                }
            }
        },
        lazySecondary = {
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = ComposableIconConstants.defaultIconSize)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Column() {
                        Text(
                            modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.feels_like) }, text = stringResource(R.string.feels_like_prefix).plus(weatherEntity.feelsLike.toInt().toString().plus(" " +
                                    "\u2109")),
                            style = MaterialTheme.typography.body2
                        )
                        Text(
                            modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.low_temp) }, text = stringResource(R.string.low_temp_prefix).plus(weatherEntity.tempMin.toInt().toString().plus(" \u2109")), style =
                            MaterialTheme.typography.body2
                        )
                        Text(
                            modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.high_temp) }, text = stringResource(R.string.high_temp_prefix).plus(weatherEntity.tempMax.toInt().toString().plus(" \u2109")),
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        })
}