package com.example.weatherapp.data.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Application;

import com.example.weatherapp.constants.ApiBaseUrl;
import com.example.weatherapp.data.model.GeoCity;
import com.example.weatherapp.data.repository.baseclass.CityApiServiceBaseClass;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@RunWith(JUnit4.class)
@HiltAndroidTest
public class GeoCityApiServiceTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Inject
    CityApiServiceBaseClass geoCityApiService;

    @Inject
    Application application;

    @Before
    public void setUp() throws Exception {
        hiltRule.inject();
    }

    @Test
    public void getGeoCityData_withValidName_returnsGeoCityData() {
        String cityName = "London";

        Optional<List<GeoCity>> geoCityData = geoCityApiService.getCityData(cityName);

        assertNotNull(geoCityData);
        geoCityData.ifPresentOrElse(data -> assertTrue(true), Assert::fail);
    }

    @Test
    public void getGeoCityData_withNullResponse_returnsNull() throws IOException {
        String cityName = "London";


        // Inject a mock Retrofit client that returns a null response
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBaseUrl.GEOCODING_BASE_URL.getBaseUrl())
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
        GeoCityApiService interceptingService = new GeoCityApiService(retrofit, application);

        Optional<List<GeoCity>> geoCityData = interceptingService.getCityData(cityName);

        assertTrue(geoCityData.isEmpty());
    }

    @Test
    public void getGeoCityData_withIOException_returnsNull() throws IOException {
        String cityName = "London";


        // Inject a mock Retrofit client that throws an IOException
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBaseUrl.GEOCODING_BASE_URL.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                                .addInterceptor(chain -> {
                                    throw new IOException("Mock IOException");
                                })
                                .build())
                .build();
        GeoCityApiService interceptingService = new GeoCityApiService(retrofit, application);

        Optional<List<GeoCity>> geoCityData = interceptingService.getCityData(cityName);

        assertFalse(geoCityData.isPresent());
    }

    @Test
    public void getGeoCityNameData_withValidCoordinates_returnsGeoCityData() {
        Double latitude = 37.7749;
        Double longitude = -122.4194;

        Optional<List<GeoCity>> geoCityData = geoCityApiService.getCityNameData(latitude, longitude);

        assertNotNull(geoCityData);
        geoCityData.ifPresentOrElse(data -> assertTrue(true), Assert::fail);
    }

    @Test
    public void getGeoCityNameData_withNullResponse_returnsNull() throws IOException {
        Double latitude = 37.7749;
        Double longitude = -122.4194;

        // Inject a mock Retrofit client that returns a null response
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBaseUrl.GEOCODING_BASE_URL.getBaseUrl())
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
        GeoCityApiService interceptingService = new GeoCityApiService(retrofit, application);

        Optional<List<GeoCity>> geoCityData = interceptingService.getCityNameData(latitude, longitude);

        assertTrue(geoCityData.isEmpty());
    }

    @Test
    public void getGeoCityNameData_withIOException_returnsNull() throws IOException {
        Double latitude = 37.7749;
        Double longitude = -122.4194;

        // Inject a mock Retrofit client that throws an IOException
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBaseUrl.GEOCODING_BASE_URL.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                                .addInterceptor(chain -> {
                                    throw new IOException("Mock IOException");
                                })
                                .build())
                .build();
        GeoCityApiService interceptingService = new GeoCityApiService(retrofit, application);

        Optional<List<GeoCity>> geoCityData = interceptingService.getCityNameData(latitude, longitude);

        assertFalse(geoCityData.isPresent());
    }
}