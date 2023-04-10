package com.example.weatherapp.presentation

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.*
import com.example.weatherapp.cache.ImagesCache
import com.example.weatherapp.constants.ApiBaseUrl
import com.example.weatherapp.data.model.GeoCity
import com.example.weatherapp.data.repository.*
import com.example.weatherapp.data.repository.baseclass.CityApiServiceBaseClass
import com.example.weatherapp.data.repository.baseclass.ForecastApiServiceBaseClass
import com.example.weatherapp.data.repository.baseclass.WeatherApiServiceBaseClass
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
    private val forecastWeatherApiService: ForecastApiServiceBaseClass,
    private val currentWeatherApiService: WeatherApiServiceBaseClass,
    private val geoCityApiService: CityApiServiceBaseClass,
    private val weatherRepository: RepositoryBaseClass,
    private val application: Application,
    private val imagesCache: ImagesCache,
    private val weatherBitmapApiService: BitmapApiServiceBaseClass

) : ViewModel() {

    /**
     *On Initialization if permission is granted retrieve current location weather data
     */
    init {
        updateCurrentLocationWeather()
    }

    /**
     * Provides scalable functionality to queue multiple permission dialogs if actions require permissions that are not granted
     * */
    //region: Permission Handling
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
    //endregion

    /**
     * Provides maintenance of state of city search name text and weather icon bitmap
     * These flows are updated on incoming stored weather data flow emissions
     * */
    //region: Stored Display Data Flows
    //Search Location Name
    private var _searchLocationNameFlow = MutableStateFlow<String>("")
    var searchLocationNameFlow = _searchLocationNameFlow.asStateFlow()
    fun updateSearchLocationName(updatedSearchName: String) {
        _searchLocationNameFlow.value = updatedSearchName
    }

    //Current Weather Bitmap
    private var _currentWeatherBitmapFlow = MutableStateFlow<Bitmap?>(null)
    var currentWeatherBitmapFlow = _currentWeatherBitmapFlow.asStateFlow()
    private fun updateCurrentWeatherBitmap(bitmap: Bitmap?) {
        _currentWeatherBitmapFlow.value = bitmap
    }

    private var _forecastWeatherBitmapFlow = MutableStateFlow<List<Bitmap?>>(emptyList())
    var forecastWeatherBitmapFlow = _forecastWeatherBitmapFlow.asStateFlow()
    private fun updateForecastWeatherBitmap(bitmapList: List<Bitmap?>) {
        _forecastWeatherBitmapFlow.value = bitmapList
    }
    //endregion

    /**
     * currentWeatherFlow
     * Maps hot live data flow to cold kotlin flow
     * On each emission of this flow (if subscribed to by the UI) relative update processes are initiated to the update searchLocationName Flow and currentWeatherBitmap Flow
     * */
    @OptIn(ExperimentalCoroutinesApi::class)
    val currentWeatherFlow: Flow<WeatherEntity?> = weatherRepository.currentWeatherLiveData().asFlow().flowOn(Dispatchers.IO).mapLatest { weatherEntityList ->
        weatherEntityList.firstOrNull()
    }.onEach { weatherEntity ->
        if (weatherEntity != null) {
            updateSearchLocationName(weatherEntity.getFullDisplayName())
            updateWeatherIconBitmap(weatherEntity.icon)
        } else {
            updateSearchLocationName("")
            updateCurrentWeatherBitmap(null)
        }
    }

    /**
     * forecastWeatherDataFlow
     * Maps hot live data flow to cold kotlin flow
     * On each emission of this flow (if subscribed to by the UI) relative update processes are initiated to the update forecastWeatherBitmap Flow
     * */
    val forecastWeatherDataFlow: Flow<List<WeatherEntity>> = weatherRepository.forecastWeatherLiveData().asFlow().flowOn(Dispatchers.IO).onEach { weatherEntityList ->
        if (weatherEntityList.isNotEmpty()) {
            updateWeatherIconBitmapList(weatherEntityList.map { it.icon })
        } else {
            updateForecastWeatherBitmap(emptyList())
        }
    }

    /**
     * Provides city search results that are to be displayed in search bar dropdown in UI
     * */
    //region: GeoCity Search Result Flow
    private var _geoCitySearchResults: MutableStateFlow<List<GeoCity>> = MutableStateFlow(emptyList())
    val geoCitySearchResults: StateFlow<List<GeoCity>> = _geoCitySearchResults.asStateFlow()
    fun clearGeoCitySearchResults() {
        _geoCitySearchResults.value = emptyList()
    }
    //endregion

    /**
     * Data refresh method for either selected dropdown city result or current location (once reverse geocity data is retrieved)
     * */
    fun refreshCurrentWeather(geoCity: GeoCity, latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val optionalCurrentWeather = currentWeatherApiService.getCurrentWeatherData(latitude, longitude)
            if (!optionalCurrentWeather.isPresent) {
                viewModelScope.launch(Dispatchers.Main) {
                    Toast.makeText(application.applicationContext, "Current Weather Request Failed", Toast.LENGTH_SHORT).show()
                }
            }
            val optionalForecastWeather = forecastWeatherApiService.getFiveDayForecastData(latitude, longitude)
            if (!optionalForecastWeather.isPresent) {
                viewModelScope.launch(Dispatchers.Main) {
                    Toast.makeText(application.applicationContext, " Forecast Weather Request Failed", Toast.LENGTH_SHORT).show()
                }
            }
            if (optionalCurrentWeather.isPresent) {
                optionalCurrentWeather.ifPresent { currentWeather ->
                    optionalForecastWeather.ifPresent { fiveDayForecast ->
                        val returnedId = weatherRepository.updateWeather(geoCity, currentWeather, fiveDayForecast)
                        if (returnedId >= 0) {
                            viewModelScope.launch(Dispatchers.Main) {
                                Toast.makeText(application.applicationContext, "Weather Data Updated", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            viewModelScope.launch(Dispatchers.Main) {
                                Toast.makeText(application.applicationContext, "Failed To Store Weather Data", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Data refresh method for current location. This action is taken on initialization of DataViewModel and on click of the current location icon (with sufficient permissions)
     * */
    fun updateCurrentLocationWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            if (ActivityCompat.checkSelfPermission(application.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {
                val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application.applicationContext)
                fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener { location: Location? ->
                    if (location == null) {
                        viewModelScope.launch(Dispatchers.Main) {
                            Toast.makeText(application.applicationContext, "Cannot get location.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        viewModelScope.launch(Dispatchers.IO) {
                            getCurrentCity(latitude = location.latitude, longitude = location.longitude)?.let { currentGeoCity ->
                                refreshCurrentWeather(geoCity = currentGeoCity, latitude = location.latitude, longitude = location.longitude)
                            } ?: run {
                                viewModelScope.launch(Dispatchers.Main) {
                                    Toast.makeText(application.applicationContext, "Cannot get location.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Geo City search on user click of search icon in UI. The results of this method are emitted onto the GeoCitySearchResults flow and displayed in the search bar dropdown menu.
     * */
    fun getGeo() {
        viewModelScope.launch(Dispatchers.IO) {
            val optionalGeoCityList = geoCityApiService.getCityData(_searchLocationNameFlow.value)
            if (!optionalGeoCityList.isPresent) {
                //GEO CITY REQUEST RETURNED EMPTY OR ERROR
                viewModelScope.launch(Dispatchers.Main) {
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

    /**
     * Private function to get current city data
     * */
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

    /**
     * Private function to inspect local image cache for update weather bitmap.
     * If bitmap is not located in image cache it is retrieved through a UTL request and stored in the local cache.
     * Bitmap is then emitted to currentWeatherBitmap flow
     * */
    private fun updateWeatherIconBitmap(imageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateCurrentWeatherBitmap(
                imagesCache.getImageFromCache(imageName)
                    ?: weatherBitmapApiService.requestBitmap(imageName)
            )
        }
    }

    /**
     * Private function to inspect local image cache for update weather bitmap.
     * If bitmap is not located in image cache it is retrieved through a UTL request and stored in the local cache.
     * All Bitmaps are then emitted to forecastWeatherBitmap flow
     * */
    private fun updateWeatherIconBitmapList(imageNameList: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            updateForecastWeatherBitmap(
                imageNameList.map { imageName ->
                    imagesCache.getImageFromCache(imageName)
                        ?: weatherBitmapApiService.requestBitmap(imageName)
                }
            )
        }
    }

}