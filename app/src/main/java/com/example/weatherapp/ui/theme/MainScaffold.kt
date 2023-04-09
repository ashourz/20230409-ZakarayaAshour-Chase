package com.example.weatherapp.ui.theme

import androidx.compose.runtime.Composable
import com.example.weatherapp.ui.PermissionRequestScaffoldWrapper
import com.example.weatherapp.ui.WeatherScaffold

@Composable
fun MainScaffold() {
    PermissionRequestScaffoldWrapper{ multiplePermissionResultLauncher ->
        WeatherScaffold(multiplePermissionResultLauncher = multiplePermissionResultLauncher)
    }
}