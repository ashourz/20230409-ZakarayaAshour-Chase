package com.example.weatherapp.data.model.weather_components;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonPropertyOrder({
        "1h"
})
public class Rain {

    /**
     * No args constructor for use in serialization
     *
     */
    public Rain() {
    }

    /**
     *
     * @param _1h
     */
    public Rain(Double _1h) {
        super();
        this._1h = _1h;
    }


    @JsonProperty("1h")
    private Double _1h;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("1h")
    public Double get1h() {
        return _1h;
    }

    @JsonProperty("1h")
    public void set1h(Double _1h) {
        this._1h = _1h;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
