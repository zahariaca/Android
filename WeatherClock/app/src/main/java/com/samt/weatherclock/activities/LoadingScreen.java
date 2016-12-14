package com.samt.weatherclock.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.samt.weatherclock.R;
import com.samt.weatherclock.util.FetchWeatherTask;
import com.samt.weatherclock.util.GpsData;

import java.util.HashMap;
import java.util.Map;


public class LoadingScreen extends AppCompatActivity implements FetchWeatherTask.LoadingTaskFinishedListener {
    public final String LOG_TAG = LoadingScreen.class.getSimpleName();
    private Map<String, String> weatherHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen_activity);

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor cityName = preferences.edit();
        cityName.putString("CityName", "Bucharest");
        cityName.commit();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            Toast.makeText(this, "Sorry mate...", Toast.LENGTH_SHORT).show();
            if (isNetworkAvailable()) {
                new FetchWeatherTask(this).execute(preferences.getString("CityName", null));
            } else {
                Log.d(LOG_TAG, "No network");
            }
        } else {
            GpsData gpsData = new GpsData(this);
            gpsData.getData();

            Log.d(LOG_TAG, "LATITUDE: " + gpsData.getLatitude());
            Log.d(LOG_TAG, "LONGITUDE: " + gpsData.getLongitude());

            Log.d(LOG_TAG, "Fetching weather data");
            if (isNetworkAvailable()) {
                new FetchWeatherTask(this).execute(gpsData.getLatitude(), gpsData.getLongitude());
            } else {
                Log.d(LOG_TAG, "No network");
            }
        }

        return super.onCreateView(name, context, attrs);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void completeSplash() {
        startApp();
        Log.d(LOG_TAG, "Finishing LoadingScreen activity");
        finish(); // Don't forget to finish this Splash Activity so the user can't return to it!
    }

    private void startApp() {
        Log.d(LOG_TAG, "Starting the next activity");
        Intent intent = new Intent(LoadingScreen.this, MainActivity.class);
        for (Map.Entry<String, String> e : weatherHashMap.entrySet()) {
            intent.putExtra(e.getKey(), e.getValue());
        }
        startActivity(intent);
    }


    @Override
    public void onTaskFinished(HashMap<String, String> weatherHashMap) {
        Log.d(LOG_TAG, "Data was fetched, copying to weatherHashMap");
        this.weatherHashMap = weatherHashMap;
        completeSplash();
    }

}
