package com.samt.weatherclock.util;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlarmModel extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private int hour;
    private int minute;
    private String timeFormated;

    public AlarmModel() {
    }

    public AlarmModel(String id, String name, int hour, int minute) {
        this.id = id;
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        if (minute >= 10) {
            this.timeFormated = hour + ":" + minute;
        } else {
            this.timeFormated = hour + ":0" + minute;
        }
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

    public String getId() {
        return id;
    }
}
