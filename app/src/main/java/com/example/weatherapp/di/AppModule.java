package com.example.weatherapp.di;

import com.example.weatherapp.cache.ImagesCache;
import com.example.weatherapp.constants.ApiBaseUrl;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Singleton
    @Provides
    @Named("geocodingClient")
    public Retrofit provideGeocodingRetrofitClient(
            OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiBaseUrl.GEOCODING_BASE_URL.getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    @Named("weatherClient")
    public Retrofit provideWeatherRetrofitClient(
            OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiBaseUrl.WEATHER_BASE_URL.getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public ImagesCache provideImageCache() {
        ImagesCache imagesCache = ImagesCache.getInstance();
        imagesCache.initializeCache();
        return imagesCache;
    }
}
