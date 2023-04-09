package com.example.weatherapp.constants;

/**
 * Enum of base Urls to be for Retrofit Clients
 * */
public enum ApiBaseUrl {

    GEOCODING_BASE_URL("https://api.openweathermap.org/geo/1.0/"),

    WEATHER_BASE_URL("https://api.openweathermap.org/data/2.5/");
    ;

    private final String baseUrl;

    ApiBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

}