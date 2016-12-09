package com.samt.weatherclock;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.samt.weatherclock.weatherapi.FetchWeatherTask;

import java.util.HashMap;
import java.util.Map;


public class LoadingScreen extends AppCompatActivity implements FetchWeatherTask.LoadingTaskFinishedListener {
    public final String LOG_TAG = LoadingScreen.class.getSimpleName();
    private Map<String, String> weatherHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

/*        new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1000);
                } catch (Exception e) {

                } finally {

                }
            }
        }.start();*/
        Log.d(LOG_TAG, "Fetching weather data");
        if(!isNetworkAvailable()){
            Log.d(LOG_TAG, "No network");
        }else {
            new FetchWeatherTask(this).execute("Bucharest");
        }
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
