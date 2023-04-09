package com.example.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R
import com.example.weatherapp.data.room.entity.WeatherEntity
import com.example.weatherapp.extensions.getActivity
import com.example.weatherapp.extensions.getStateCountryDisplayName
import com.example.weatherapp.extensions.openAppSettings
import com.example.weatherapp.extensions.toDisplayFormat
import com.example.weatherapp.presentation.DataViewModel
import com.example.weatherapp.ui.components.CitySearchDropDown
import com.example.weatherapp.ui.components.PortraitLandscapeScreen
import com.example.weatherapp.ui.icons.ComposableIconConstants
import com.example.weatherapp.ui.icons.SizedIcon
import com.example.weatherapp.ui.permissions.LocationPermissionTextProvider
import com.example.weatherapp.ui.permissions.PermissionDialog

@Composable
fun MainScaffold(
    viewModel: DataViewModel = viewModel()
) {
    val localContext = LocalContext.current
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    val activity = localContext.getActivity()
    val storedWeather: WeatherEntity? by viewModel.storedWeatherFlow.collectAsState(null)
    val currentWeatherBitmap: Bitmap? by viewModel.currentWeatherIconFlow.collectAsState(null)

    //Launcher State for Request Permission Dialog Launcher
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionGrantedMap ->
            permissionGrantedMap.forEach { (permissionString, isGranted) ->
                viewModel.onPermissionResult(
                    permissionString,
                    isGranted = isGranted
                )
            }
        })

    Scaffold(
        topBar = {
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
                    SizedIcon(iconDrawable = ComposableIconConstants.currentLocationIcon, contentDescription = "Get Current Location")
                }
            }
            storedWeather?.let { weatherEntity ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SizedIcon(
                        iconDrawable = ComposableIconConstants.locationPinIcon,
                        contentDescription = "Location Icon",
                    )
                    Column() {
                        Text(modifier = Modifier.semantics { contentDescription = "Current Data Name" },text = weatherEntity.city, style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight
                            .Bold)
                        Text(text = weatherEntity.getStateCountryDisplayName(), style = MaterialTheme.typography.caption)
                    }
                }
                Text(
                    modifier = Modifier.padding(start = ComposableIconConstants.defaultIconSize), text = "Updated: ${weatherEntity.datetime.toDisplayFormat()}", style = MaterialTheme.typography
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
                                            contentDescription = "Weather Icon",
                                        )
                                    }
                                    Text(text = weatherEntity.description.split(" ").joinToString(" ") { it.capitalize() }, style = MaterialTheme.typography.body2)

                                }

                                Column() {
                                    Text(modifier = Modifier.semantics { contentDescription = "Temperature Value" }, text = weatherEntity.temp.toInt().toString().plus(" \u2109"), style = MaterialTheme
                                        .typography.h1)
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
                                    Text(modifier = Modifier.semantics { contentDescription = "Feels Like" }, text = "Feels Like: ".plus(weatherEntity.feelsLike.toInt().toString().plus(" \u2109")),
                                        style = MaterialTheme.typography.body2)
                                    Text(modifier = Modifier.semantics { contentDescription = "Low Temp" }, text = "Low Temp: ".plus(weatherEntity.tempMin.toInt().toString().plus(" \u2109")), style =
                                    MaterialTheme.typography.body2)
                                    Text(modifier = Modifier.semantics { contentDescription = "High Temp" }, text = "High Temp: ".plus(weatherEntity.tempMax.toInt().toString().plus(" \u2109")),
                                        style = MaterialTheme.typography.body2)

                                }
                            }
                        }

                    })
            }
        }
    }


    //Displays Permission Rationale Dialog on Decline of Permission
    if (activity != null) {
        dialogQueue.forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.ACCESS_COARSE_LOCATION -> LocationPermissionTextProvider()
                    else -> return@forEach
                },
                isPermanentlyDeclined = (
                        ActivityCompat.checkSelfPermission(activity.applicationContext, permission) == PackageManager.PERMISSION_DENIED &&
                                !shouldShowRequestPermissionRationale(activity, permission)),
                onDismiss = viewModel::dismissPermissionDialogQueue,
                onOkClick = {
                    viewModel.dismissPermissionDialogQueue()
                    multiplePermissionResultLauncher.launch(arrayOf(permission))
                },
                onGoToAppSetttingsClick = {
                    viewModel.dismissPermissionDialogQueue()
                    activity.openAppSettings()
                }
            )
        }
    }
}