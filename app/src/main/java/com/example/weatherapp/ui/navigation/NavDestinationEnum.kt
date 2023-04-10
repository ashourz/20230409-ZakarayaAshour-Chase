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

import com.example.weatherapp.ui.icons.ComposableIconConstants


/**
 * Navigation Destination Enum for Navigation
 * */
enum class NavDestinationEnum(
    val icon: Int
){
    CURRENT_WEATHER(ComposableIconConstants.todayIcon),
    FIVEDAY_FORECAST( ComposableIconConstants.fiveDayIcon);

    companion object {
        fun fromRoute(route: String?): NavDestinationEnum =
            when (route?.substringBefore("/")) {
                CURRENT_WEATHER.name -> CURRENT_WEATHER
                FIVEDAY_FORECAST.name -> FIVEDAY_FORECAST
                else -> CURRENT_WEATHER
            }
    }
}