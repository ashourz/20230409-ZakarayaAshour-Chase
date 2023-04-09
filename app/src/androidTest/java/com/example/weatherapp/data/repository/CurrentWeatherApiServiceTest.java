package com.example.weatherapp.data.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.app.Application;

import com.example.weatherapp.constants.ApiBaseUrl;
import com.example.weatherapp.data.model.CurrentWeather;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@RunWith(JUnit4.class)
@HiltAndroidTest
public class CurrentWeatherApiServiceTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Inject
    CurrentWeatherApiService currentWeatherApiService;

    @Inject
    Application application;

    @Before
    public void setUp() throws Exception {
        hiltRule.inject();
    }

    @Test
    public void getCurrentWeatherData_withValidCoordinates_returnsWeatherData() {
        Double latitude = 37.7749;
        Double longitude = -122.4194;

        Optional<CurrentWeather> currentWeatherData = currentWeatherApiService.getCurrentWeatherData(latitude, longitude);

        assertNotNull(currentWeatherData);
        assertTrue(currentWeatherData.isPresent());
    }

    @Test
    public void getCurrentWeatherData_withNullResponse_returnsNull() throws IOException {
        Double latitude = 37.7749;
        Double longitude = -122.4194;

        // Inject a mock Retrofit client that returns a null response
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBaseUrl.WEATHER_BASE_URL.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                                .addInterceptor(chain -> new Response.Builder()
                                        .request(chain.request())
                                        .protocol(Protocol.HTTP_1_1)
                                        .code(200)
                                        .body(null)
                                        .build())
                                .build()
                       )
                .build();
        CurrentWeatherApiService interceptingService = new CurrentWeatherApiService(retrofit, application);

        Optional<CurrentWeather> currentWeatherData = interceptingService.getCurrentWeatherData(latitude, longitude);

        assertTrue(currentWeatherData.isEmpty());
    }

    @Test
    public void getCurrentWeatherData_withIOException_returnsNull() throws IOException {
        Double latitude = 37.7749;
        Double longitude = -122.4194;

        // Inject a mock Retrofit client that throws an IOException
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBaseUrl.WEATHER_BASE_URL.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                                .addInterceptor(chain -> {
                                    throw new IOException("Mock IOException");
                                })
                                .build())
                .build();
        CurrentWeatherApiService interceptingService = new CurrentWeatherApiService(retrofit, application);

        Optional<CurrentWeather> currentWeatherData = interceptingService.getCurrentWeatherData(latitude, longitude);

        assertFalse(currentWeatherData.isPresent());
    }
}