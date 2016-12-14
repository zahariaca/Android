package com.samt.weatherclock.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.Calendar;

public class GpsData implements LocationListener {
    public final String LOG_TAG = GpsData.class.getSimpleName();
    private Context context;
    private LocationManager locationManager;
    private Location location = null;
    private DecimalFormat df = new DecimalFormat("##.##");
    private String latitude;
    private String longitude;

    public GpsData(Context context) {
        this.context = context;
    }

    public void getData() {
        gpsData();
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void gpsData() {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "EXCEPTION: " + e.getMessage());
        }
        if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
            Log.d(LOG_TAG, "LATITUDE: " + String.valueOf(location.getLatitude()));
        } else {
            Log.d(LOG_TAG, "LOCATION WAS NULL");
            try {
                Log.d(LOG_TAG, "LOCATION IS UPDATING");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 100, this);
                Log.d(LOG_TAG, "LOCATION UPDATED");
            } catch (SecurityException e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "EXCEPTION: " + e.getMessage());
            }
        }

        latitude = String.valueOf(df.format(location.getLatitude()));
        longitude = String.valueOf(df.format(location.getLongitude()));
        Log.d(LOG_TAG, "LATITUDE: " + String.valueOf(df.format(location.getLatitude())));
        Log.d(LOG_TAG, "LONGITUDE: " + String.valueOf(df.format(location.getLongitude())));

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
            try {
                locationManager.removeUpdates(this);
            } catch (SecurityException e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "EXCEPTION: " + e.getMessage());
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(LOG_TAG, "Status has changed");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(LOG_TAG, "Provider enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(LOG_TAG, "Provider disabled");
    }
}
