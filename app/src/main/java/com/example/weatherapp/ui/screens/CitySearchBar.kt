package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.weatherapp.R
import com.example.weatherapp.ui.components.CitySearchDropDown
import com.example.weatherapp.ui.icons.ComposableIconConstants
import com.example.weatherapp.ui.icons.SizedIcon


/**
 * City Search bar for Weather Scaffolg
 * */
@Composable
fun CitySearchBar(
    onCurrentLocationButtonClick: () -> Unit
) {
    //Search bar and submission buttons
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CitySearchDropDown(modifier = Modifier.weight(1f, true))

        IconButton(onClick = onCurrentLocationButtonClick) {
            SizedIcon(iconDrawable = ComposableIconConstants.currentLocationIcon, contentDescription = stringResource(R.string.get_current_location))
        }
    }

}