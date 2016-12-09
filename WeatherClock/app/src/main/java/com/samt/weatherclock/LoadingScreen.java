package com.samt.weatherclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.samt.weatherclock.weatherapi.FetchWeatherTask;

import java.util.HashMap;
import java.util.Map;


public class LoadingScreen extends AppCompatActivity implements FetchWeatherTask.LoadingTaskFinishedListener {
    Map<String, String> weatherHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1000);
                } catch (Exception e) {

                } finally {

                }
            }
        }.start();
        new FetchWeatherTask(this).execute("Bucharest");
    }

    private void completeSplash() {
        startApp();
        finish(); // Don't forget to finish this Splash Activity so the user can't return to it!
    }

    private void startApp() {
        Intent intent = new Intent(LoadingScreen.this, MainActivity.class);
        for (Map.Entry<String, String> e : weatherHashMap.entrySet()) {
            intent.putExtra(e.getKey(), e.getValue());
        }
        startActivity(intent);
    }


    @Override
    public void onTaskFinished(HashMap<String, String> weatherHashMap) {
        this.weatherHashMap = weatherHashMap;
        completeSplash();
    }
}
