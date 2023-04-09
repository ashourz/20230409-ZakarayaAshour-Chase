package com.example.weatherapp.extensions

import com.example.weatherapp.data.model.GeoCity
import com.example.weatherapp.data.room.entity.WeatherEntity

fun GeoCity.getFullDisplayName(): String {
    var displayName = this.name
    if(this.state.isNullOrBlank()){
        displayName = displayName.plus(", ${this.state}")
    }
    if(this.country.isNotBlank()){
        displayName = displayName.plus(", ${this.country}")
    }
    return displayName
}

fun WeatherEntity.getFullDisplayName(): String {
    var displayName = this.city
    if(this.state.isNotBlank()){
        displayName = displayName.plus(", ${this.state}")
    }
    if(this.country.isNotBlank()){
        displayName = displayName.plus(", ${this.country}")
    }
    return displayName
}

fun WeatherEntity.getStateCountryDisplayName(): String {
    var displayName = this.state
    if(displayName.isNotBlank()){displayName = displayName.plus(", ")}
    displayName = displayName.plus(this.country)

    return displayName
}

