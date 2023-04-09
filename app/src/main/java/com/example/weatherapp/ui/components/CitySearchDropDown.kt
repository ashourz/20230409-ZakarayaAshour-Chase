package com.example.weatherapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R
import com.example.weatherapp.extensions.getFullDisplayName
import com.example.weatherapp.presentation.DataViewModel
import com.example.weatherapp.ui.icons.ComposableIconConstants
import com.example.weatherapp.ui.icons.SizedIcon


/**
 * Custom Exposed Dropdown Menu
 * */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CitySearchDropDown (
    modifier: Modifier = Modifier,
    viewModel: DataViewModel = viewModel()
){
    var dropdownExpanded by remember { mutableStateOf(false) }
    val storedSearchText by viewModel.searchLocationNameFlow.collectAsState()
    val geoCitySearchList by viewModel.geoCitySearchResults.collectAsState()
    val localContext = LocalContext.current
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = dropdownExpanded,
        onExpandedChange = { dropdownExpanded = false }) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().semantics { contentDescription = localContext.getString(R.string.city_search)  },
            label = { Text(text = stringResource(R.string.city_search)) },
            value = storedSearchText,
            onValueChange = { updatedText ->
                viewModel.clearGeoCitySearchResults()
                viewModel.updateSearchLocationName(updatedText)
            },
            leadingIcon = {
                SizedIcon(iconDrawable = ComposableIconConstants.locationPinIcon, contentDescription = stringResource(R.string.weather_location))
            },
            maxLines = 1,
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.getGeo()
                    dropdownExpanded = true
                }) {
                    SizedIcon(iconDrawable = ComposableIconConstants.searchIcon, contentDescription = stringResource(R.string.search_city_weather))
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )
        DropdownMenu(
            modifier = Modifier.exposedDropdownSize(),
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false }) {
            geoCitySearchList.forEach { location ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth().semantics { contentDescription = localContext.getString(R.string.dropdown_city_item) },
                    onClick = {
                        dropdownExpanded = false
                        viewModel.refreshCurrentWeather(
                            geoCity = location,
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    }
                ) {
                    Text(text = location.getFullDisplayName())
                }
            }
        }
    }
}