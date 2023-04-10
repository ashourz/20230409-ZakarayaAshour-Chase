package com.example.weatherapp.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.cache.ImagesCache
import com.example.weatherapp.data.repository.FakeCurrentWeatherApiService
import com.example.weatherapp.data.repository.FakeForecastWeatherApiService
import com.example.weatherapp.data.repository.FakeGeoCityApiService
import com.example.weatherapp.data.repository.FakeWeatherRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@RunWith(JUnit4::class)
@HiltAndroidTest
class DataViewModelTest {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTasExecutorRule = InstantTaskExecutorRule()

    private lateinit var dataViewModel: DataViewModel
    private lateinit var fakeCurrentWeatherApiService: FakeCurrentWeatherApiService
    private lateinit var fakeGeoCityApiService: FakeGeoCityApiService
    private lateinit var fakeWeatherRepository: FakeWeatherRepository
    private lateinit var fakeForecastApiService: FakeForecastWeatherApiService

    @Inject
    lateinit var application: Application

    @Before
    fun init() {
        hiltRule.inject()
        fakeCurrentWeatherApiService = FakeCurrentWeatherApiService()
        fakeGeoCityApiService = FakeGeoCityApiService()
        fakeWeatherRepository = FakeWeatherRepository()
        fakeForecastApiService = FakeForecastWeatherApiService()

        dataViewModel = DataViewModel(
            forecastWeatherApiService = fakeForecastApiService,
            currentWeatherApiService = fakeCurrentWeatherApiService,
            geoCityApiService = fakeGeoCityApiService,
            weatherRepository = fakeWeatherRepository,
            application = application,
            imagesCache = ImagesCache()
        )
    }

    @Test
    fun onAddPermissionResultGranted_expectEmptyQueue() {
        dataViewModel.onPermissionResult("PERMISSION", true)
        assertFalse(dataViewModel.visiblePermissionDialogQueue.toList().any())
    }

    @Test
    fun onAddPermissionResultNotGranted_expectEmptyQueue() {
        dataViewModel.onPermissionResult("PERMISSION", false)
        assertTrue(dataViewModel.visiblePermissionDialogQueue.toList().any())
    }

    @Test
    fun dismissPermissionDialogQueue_expectEmptyQueue() {
        dataViewModel.onPermissionResult("PERMISSION", false)
        assertTrue(dataViewModel.visiblePermissionDialogQueue.toList().any())
        dataViewModel.dismissPermissionDialogQueue()
        assertFalse(dataViewModel.visiblePermissionDialogQueue.toList().any())
    }

    @Test
    fun onAddSearchLocationNameFlow() {
        dataViewModel.updateSearchLocationName("NAME")
        assertTrue(dataViewModel.searchLocationNameFlow.value == "NAME")
    }

}