package com.example.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.room.entity.WeatherEntity
import com.example.weatherapp.extensions.getStateCountryDisplayName
import com.example.weatherapp.presentation.DataViewModel
import com.example.weatherapp.ui.icons.ComposableIconConstants
import com.example.weatherapp.ui.icons.SizedIcon
import com.example.weatherapp.ui.navigation.MainActivityNavHost
import com.example.weatherapp.ui.navigation.NavDestinationEnum
import com.example.weatherapp.ui.screens.CitySearchBar
import com.example.weatherapp.ui.screens.LandscapeSideNavigationBar
import com.example.weatherapp.ui.screens.PortraitBottomNavigationBar

@Composable
fun WeatherScaffold(
    multiplePermissionResultLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    viewModel: DataViewModel = hiltViewModel()
) {
    val localContext = LocalContext.current
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen: NavDestinationEnum = NavDestinationEnum.fromRoute(navBackStackEntry?.destination?.route)
    val currentWeather: WeatherEntity? by viewModel.currentWeatherFlow.collectAsState(null)

    Scaffold(
        topBar = {
            //Simple top bar
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        bottomBar = {
            when (LocalConfiguration.current.orientation) {
                Configuration.ORIENTATION_PORTRAIT,
                Configuration.ORIENTATION_SQUARE,
                Configuration.ORIENTATION_UNDEFINED -> {
                    PortraitBottomNavigationBar(
                        selectedDestination = currentScreen,
                        onNavigationItemClick = {
                            navController.navigate(it.name) {
                                this.launchSingleTop
                            }
                        })
                }
                else -> {}
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(4.dp)
            ) {
                CitySearchBar(
                    onCurrentLocationButtonClick = {
                        if (ActivityCompat.checkSelfPermission(localContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        ) {
                            viewModel.updateCurrentLocationWeather()
                        } else {
                            multiplePermissionResultLauncher.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
                        }
                    }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SizedIcon(
                        iconDrawable = ComposableIconConstants.locationPinIcon,
                        contentDescription = stringResource(R.string.location_icon),
                    )
                    Column() {
                        Text(
                            modifier = Modifier.semantics { contentDescription = localContext.getString(R.string.current_data_name) },
                            text = currentWeather?.city?:"Location Not Found",
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight =
                            FontWeight
                                .Bold
                        )
                        Text(text = currentWeather?.getStateCountryDisplayName()?:"", style = MaterialTheme.typography.caption)
                    }
                }
                MainActivityNavHost(navController, viewModel)
            }
            when (LocalConfiguration.current.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    LandscapeSideNavigationBar(
                        selectedDestination = currentScreen,
                        onNavigationItemClick = {
                            navController.navigate(it.name) {
                                this.launchSingleTop
                            }
                        }
                    )
                }
                else -> {}
            }
        }
    }
}