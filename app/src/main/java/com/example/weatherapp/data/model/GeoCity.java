package com.example.weatherapp.data.model;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCity {
    @JsonProperty("name")
    private String name;
    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("lon")
    private double longitude;
    @JsonProperty("country")
    private String country;
    @JsonProperty("state")
    private String state;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    @NonNull
    @Override
    public String toString() {
        return "City{" +
               "name='" + name + '\'' +
               ", lat=" + latitude +
               ", lon=" + longitude +
               ", state='" + state + '\'' +
               ", country='" + country + '\'' +
               '}';
    }
}