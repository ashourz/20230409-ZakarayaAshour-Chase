package com.example.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R
import com.example.weatherapp.data.room.entity.WeatherEntity
import com.example.weatherapp.presentation.DataViewModel
import com.example.weatherapp.ui.components.CitySearchDropDown
import com.example.weatherapp.ui.icons.ComposableIconConstants
import com.example.weatherapp.ui.icons.SizedIcon

@Composable
fun WeatherScaffold(
    multiplePermissionResultLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    viewModel: DataViewModel = viewModel()
) {
    val localContext = LocalContext.current
    val storedWeather: WeatherEntity? by viewModel.storedWeatherFlow.collectAsState(null)

    Scaffold(
        topBar = {
            //Simple top bar
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
    ) { interPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(interPadding)
                .padding(4.dp)
        ) {

            //Search bar and submission buttons
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CitySearchDropDown(modifier = Modifier.weight(1f, true))

                IconButton(onClick = {
                    if (ActivityCompat.checkSelfPermission(localContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    ) {
                        viewModel.updateCurrentLocationWeather()
                    } else {
                        multiplePermissionResultLauncher.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
                    }
                }) {
                    SizedIcon(iconDrawable = ComposableIconConstants.currentLocationIcon, contentDescription = stringResource(R.string.get_current_location))
                }
            }

            //Show current weather data screen if not null
            storedWeather?.let { weatherEntity ->
                CurrentWeatherDataScreen(weatherEntity)
            }
        }
    }

}