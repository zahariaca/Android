package com.samt.weatherclock.util;

import io.realm.RealmObject;

/**
 * Created by AZaharia on 12/13/2016.
 */

public class AlarmModel extends RealmObject{
    private String name;
    private int hour;
    private int minute;
    private String timeFormated;

    public AlarmModel(){}

    public AlarmModel(String name, int hour, int minute){
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.timeFormated = hour + ":" + minute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getTimeFormated() {
        return timeFormated;
    }

    public void setTimeFormated(String timeFormated) {
        this.timeFormated = timeFormated;
    }
}
