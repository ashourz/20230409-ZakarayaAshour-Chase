/*
 * Copyright 2023 Zakaraya Thomas Ashour
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.weatherapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.presentation.DataViewModel
import com.example.weatherapp.ui.screens.current.CurrentWeatherDataScreen
import com.example.weatherapp.ui.screens.forecast.FiveDayForecastScreen

/**
 * Nabigation Host for Main Activity
 * */
@Composable
fun MainActivityNavHost(
    navController: NavHostController,
    viewModel: DataViewModel
) {
    Scaffold(
    ) {
        NavHost(
            modifier = Modifier.fillMaxSize().padding(it),
            navController = navController,
            startDestination = NavDestinationEnum.CURRENT_WEATHER.name
        ) {

            composable(NavDestinationEnum.CURRENT_WEATHER.name) {
                CurrentWeatherDataScreen(viewModel)
            }

            composable(NavDestinationEnum.FIVEDAY_FORECAST.name) {
                FiveDayForecastScreen(viewModel)
            }
        }
    }
}