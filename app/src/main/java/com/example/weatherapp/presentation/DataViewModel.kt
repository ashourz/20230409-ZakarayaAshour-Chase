package com.example.weatherapp.presentation

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.cache.ImagesCache
import com.example.weatherapp.data.model.GeoCity
import com.example.weatherapp.data.repository.CurrentWeatherApiService
import com.example.weatherapp.data.repository.GeoCityApiService
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.data.room.entity.WeatherEntity
import com.example.weatherapp.extensions.getFullDisplayName
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull


@HiltViewModel
class DataViewModel @Inject constructor(
    private val currentWeatherApiService: CurrentWeatherApiService,
    private val geoCityApiService: GeoCityApiService,
    private val weatherRepository: WeatherRepository,
    private val application: Application,
    private val imagesCache: ImagesCache
) : ViewModel() {

    init {
        //On Initialization if permission is granted retrieve current location weather data
        updateCurrentLocationWeather()
    }

    //Permission Handling
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissPermissionDialogQueue() {
        visiblePermissionDialogQueue.removeLastOrNull()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted) {
            val nextIndex = visiblePermissionDialogQueue.lastIndex + 1
            visiblePermissionDialogQueue.add(nextIndex, permission)
        }
    }

    //Stored Location Name
    private var _searchLocationNameFlow = MutableStateFlow<String>("")
    var searchLocationNameFlow = _searchLocationNameFlow.asStateFlow()
    fun updateSearchLocationName(updatedSearchName: String) {
        _searchLocationNameFlow.value = updatedSearchName
    }

    private var _currentWeatherIconFlow = MutableStateFlow<Bitmap?>(null)
    var currentWeatherIconFlow = _currentWeatherIconFlow.asStateFlow()
    private fun updateCurrentWeatherIcon(bitmap: Bitmap?) {
        _currentWeatherIconFlow.value = bitmap
    }

    //Weather Flow
    @OptIn(ExperimentalCoroutinesApi::class)
    val storedWeatherFlow: Flow<WeatherEntity?> = weatherRepository.weatherLiveData().asFlow().flowOn(Dispatchers.IO).mapLatest { weatherEntityList ->
        weatherEntityList.firstOrNull()
    }.onEach { weatherEntity ->
        if (weatherEntity != null) {
            updateSearchLocationName(weatherEntity.getFullDisplayName())
            updateWeatherIconBitmap(weatherEntity.icon)
        } else {
            updateSearchLocationName("")
            updateCurrentWeatherIcon(null)
        }
    }


    //GeoCity Search Result Flow
    private var _geoCitySearchResults: MutableStateFlow<List<GeoCity>> = MutableStateFlow(emptyList())
    val geoCitySearchResults: StateFlow<List<GeoCity>> = _geoCitySearchResults.asStateFlow()
    fun clearGeoCitySearchResults() {
        _geoCitySearchResults.value = emptyList()
    }

    fun refreshCurrentWeather(geoCity: GeoCity, latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val optionalCurrentWeather = currentWeatherApiService.getCurrentWeatherData(latitude, longitude)
            if (!optionalCurrentWeather.isPresent) {
                viewModelScope.launch {
                    Toast.makeText(application.applicationContext, "Weather Data Request Failed", Toast.LENGTH_SHORT).show()
                }
            }
            if (optionalCurrentWeather.isPresent) {
                optionalCurrentWeather.ifPresent { currentWeather ->
                    val returnedId = weatherRepository.updateCurrentWeather(geoCity, currentWeather)
                    if (returnedId >= 0) {
                        viewModelScope.launch {
                            Toast.makeText(application.applicationContext, "Current Weather Updated", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        viewModelScope.launch {
                            Toast.makeText(application.applicationContext, "Failed To Store Current Weather Data", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }
        }
    }

    private suspend fun getCurrentCity(latitude: Double, longitude: Double): GeoCity? {
        return withContext(Dispatchers.IO) {
            val optionalCurrentCity = geoCityApiService.getCityNameData(latitude, longitude)
            when (optionalCurrentCity.isPresent) {
                true -> {
                    return@withContext optionalCurrentCity?.getOrNull()?.getOrNull(0)
                }
                false -> {
                    //Reverse Location Failed
                    return@withContext null
                }
            }
        }
    }

    fun updateCurrentLocationWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            if (ActivityCompat.checkSelfPermission(application.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {
                val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application.applicationContext)
                fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener { location: Location? ->
                    if (location == null) {
                        viewModelScope.launch {
                            Toast.makeText(application.applicationContext, "Cannot get location.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        viewModelScope.launch(Dispatchers.IO) {
                            getCurrentCity(latitude = location.latitude, longitude = location.longitude)?.let { currentGeoCity ->
                                refreshCurrentWeather(geoCity = currentGeoCity, latitude = location.latitude, longitude = location.longitude)
                            } ?: run {
                                viewModelScope.launch {
                                    Toast.makeText(application.applicationContext, "Cannot get location.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    fun getGeo() {
        viewModelScope.launch(Dispatchers.IO) {
            val optionalGeoCityList = geoCityApiService.getCityData(_searchLocationNameFlow.value)
            if (!optionalGeoCityList.isPresent) {
                //GEO CITY REQUEST RETURNED EMPTY OR ERROR
                viewModelScope.launch {
                    Toast.makeText(application.applicationContext, "City Not Found", Toast.LENGTH_SHORT).show()
                }
            }
            if (optionalGeoCityList.isPresent) {
                optionalGeoCityList.ifPresent { geoCityList ->
                    viewModelScope.launch(Dispatchers.IO) {
                        _geoCitySearchResults.emit(geoCityList)
                    }
                }
            }
        }
    }

    private fun updateWeatherIconBitmap(imageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateCurrentWeatherIcon(
                imagesCache.getImageFromCache(imageName)
                    ?: try {
                        val baseUrl = "https://openweathermap.org/img/wn/"
                        val tailUrl = "@2x.png"
                        val inputStream: InputStream = URL(baseUrl.plus(imageName).plus(tailUrl)).content as InputStream
                        val imageBitmap = Drawable.createFromStream(inputStream, "src name")?.toBitmap(200, 200, Bitmap.Config.ARGB_8888)
                        imageBitmap?.setHasAlpha(true)
                        imagesCache.addImageToCache(imageName, imageBitmap)
                        imageBitmap
                    } catch (e: Exception) {
                        println(e.message)
                        null
                    }
            )
        }
    }
}