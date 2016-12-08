package com.samt.weatherclock;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AZaharia on 12/8/2016.
 */

public class WeatherDataMock extends RealmObject {
    @PrimaryKey
    private int id;
    private String location;
    private String timeOfTheUpdate;
    private String typeOfWeatherIcon;
    private String typeOfWeather;
    private String humidity;
    private String pressure;
    private String temperature;

    public WeatherDataMock(){}

    public WeatherDataMock(int id,String location, String timeOfTheUpdate,String typeOfWeatherIcon, String typeOfWeather, String humidity, String pressure, String temperature) {
        this.id = id;
        this.location = location;
        this.timeOfTheUpdate = timeOfTheUpdate;
        this.typeOfWeatherIcon = typeOfWeatherIcon;
        this.typeOfWeather = typeOfWeather;
        this.humidity = humidity;
        this.pressure = pressure;
        this.temperature = temperature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeOfTheUpdate() {
        return timeOfTheUpdate;
    }

    public void setTimeOfTheUpdate(String timeOfTheUpdate) {
        this.timeOfTheUpdate = timeOfTheUpdate;
    }

    public String getTypeOfWeatherIcon() {
        return typeOfWeatherIcon;
    }

    public void setTypeOfWeatherIcon(String typeOfWeatherIcon) {
        this.typeOfWeatherIcon = typeOfWeatherIcon;
    }

    public String getTypeOfWeather() {
        return typeOfWeather;
    }

    public void setTypeOfWeather(String typeOfWeather) {
        this.typeOfWeather = typeOfWeather;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
