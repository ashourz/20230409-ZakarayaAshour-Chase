package com.example.weatherapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.weatherapp.constants.ApiBaseUrl;
import com.example.weatherapp.di.AppModule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@RunWith(JUnit4.class)
@HiltAndroidTest
public class AppModuleTest {
    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    @Named("geocodingClient")
    Retrofit geocodingRetrofitClient;

    @Inject
    @Named("weatherClient")
    Retrofit weatherRetrofitClient;

    @Before
    public void init() {
        hiltRule.inject();
    }


    @Test
    public void testOkHttpClient() {
        assertNotNull(okHttpClient);
        // Test callTimeout is set to 2 minutes
        assertEquals(120000, okHttpClient.callTimeoutMillis());
        // Test connectTimeout is set to 20 seconds
        assertEquals(20000, okHttpClient.connectTimeoutMillis());
        // Test readTimeout is set to 30 seconds
        assertEquals(30000, okHttpClient.readTimeoutMillis());
        assertTrue(okHttpClient.interceptors().stream()
                               .anyMatch(interceptor -> interceptor instanceof HttpLoggingInterceptor));
    }

    @Test
    public void testProvideGeocodingRetrofitClient() {
        // Test that the Retrofit client for geocoding provides a valid client with the specified base URL and OkHttp client
        assertNotNull(geocodingRetrofitClient);
        assertEquals(ApiBaseUrl.GEOCODING_BASE_URL.getBaseUrl(), geocodingRetrofitClient.baseUrl().toString());
        assertEquals(okHttpClient, geocodingRetrofitClient.callFactory());
        assertTrue(geocodingRetrofitClient.converterFactories().stream()
                           .anyMatch(factory -> factory instanceof JacksonConverterFactory));
    }

    @Test
    public void testProvideCurrentWeatherRetrofitClient() {
        // Test that the Retrofit client for OneCall provides a valid client with the specified base URL and OkHttp client
        assertNotNull(weatherRetrofitClient);
        assertEquals(ApiBaseUrl.WEATHER_BASE_URL.getBaseUrl(), weatherRetrofitClient.baseUrl().toString());
        assertEquals(okHttpClient, weatherRetrofitClient.callFactory());
        assertTrue(weatherRetrofitClient.converterFactories().stream()
                           .anyMatch(factory -> factory instanceof JacksonConverterFactory));
    }

}
