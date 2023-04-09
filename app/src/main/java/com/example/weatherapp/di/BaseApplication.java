package com.example.weatherapp.di;

import android.app.Application;
import android.content.Context;

import com.example.weatherapp.data.remote.CityApiInterface;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Provides;
import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Retrofit;
/**
 * Hilt Base Application Module
 * */
@HiltAndroidApp
public class BaseApplication extends Application {
}
