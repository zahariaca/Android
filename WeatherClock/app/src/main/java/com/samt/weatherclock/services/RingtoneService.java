package com.samt.weatherclock.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.samt.weatherclock.R;
import com.samt.weatherclock.util.FetchWeatherTask;
import com.samt.weatherclock.util.GpsData;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class RingtoneService extends Service implements FetchWeatherTask.LoadingTaskFinishedListener{
    private final String LOG_TAG = RingtoneService.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    private LayoutInflater li;
    private WindowManager windowManager;
    private RelativeLayout relativeLayout;
    private ToggleButton cancelButton;
    private TextView location;
    private TextView updated;
    private ImageView imageView;
    private TextView description;
    private TextView temperature;
    private TextView humidity;
    private TextView pressure;
    private GpsData gpsData;
    private Map<String,String> weatherHashMap = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "service starting : " +startId + " : " + intent );

        fetchWeather();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                //WindowManager.LayoutParams.TYPE_INPUT_METHOD |
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,// | WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT);


        params.gravity = Gravity.RIGHT | Gravity.TOP;



        relativeLayout = (RelativeLayout) li.inflate(R.layout.alarm_service_layout, null);

        location = (TextView) relativeLayout.findViewById(R.id.tv_location_city);
        updated = (TextView) relativeLayout.findViewById(R.id.tv_location_lastupdated);
        imageView = (ImageView) relativeLayout.findViewById(R.id.imageView);
        description = (TextView) relativeLayout.findViewById(R.id.tv_weatherType);
        temperature = (TextView) relativeLayout.findViewById(R.id.tv_temperature);
        humidity = (TextView) relativeLayout.findViewById(R.id.tv_humidity);
        pressure = (TextView) relativeLayout.findViewById(R.id.tv_pressure);

        cancelButton = (ToggleButton) relativeLayout.findViewById(R.id.toggleButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(LOG_TAG, "Button clicked : ");
                onDestroy();
                windowManager.removeViewImmediate(relativeLayout);
            }
        });

        windowManager.addView(relativeLayout, params);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_tone);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        return START_NOT_STICKY;
    }

    // Fetching weather data for when the refresh button is clicked
    public void fetchWeather() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (isNetworkAvailable()) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String restoredText = sharedPreferences.getString("CityName", null);
                Log.d(LOG_TAG,"Text from shared preference: " +  restoredText);
                new FetchWeatherTask(this).execute(restoredText);
            } else {
                Log.d(LOG_TAG, "No network");
            }
        } else {
            try {
                gpsData = new GpsData(getApplicationContext());
                gpsData.getData();
                Log.d(LOG_TAG, "LATITUDE: " + gpsData.getLatitude());
                Log.d(LOG_TAG, "LONGITUDE: " + gpsData.getLongitude());
                Log.d(LOG_TAG, "inside of fetchWeathe method");
                if (isNetworkAvailable()) {
                    new FetchWeatherTask(this).execute(gpsData.getLatitude(), gpsData.getLongitude());
                } else {
                    Log.d(LOG_TAG, "No network");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (gpsData != null) {
                    gpsData = null;
                }
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Receiving the weather data with the help of the LoadingTaskFinishedListener interface from FetchWeatherTask
    @Override
    public void onTaskFinished(HashMap<String, String> weatherHashMap) {
        Log.d(LOG_TAG, weatherHashMap.get("Updated") + " -> FROM WEATHER FRAGMENT REFRESH");
        this.weatherHashMap = weatherHashMap;
        Log.d(LOG_TAG, "weatherHashMap has been populated, not waiting anymore: " + weatherHashMap.get("CityName"));
        setWeatherForRefresh(weatherHashMap);
    }

    public void setWeatherForRefresh(HashMap<String, String> weatherHashMap) {
        location.setText(weatherHashMap.get("CityName"));
        updated.setText(weatherHashMap.get("Updated"));
        imageView.setImageDrawable(getDrawable(R.drawable.ic_alarm_on));
        imageView.setVisibility(View.VISIBLE);
        Log.d(LOG_TAG, "Starting to set weather icon");
        Log.d(LOG_TAG, "Finished weather icon");
        description.setText(weatherHashMap.get("Description"));
        temperature.setText(weatherHashMap.get("Temperature"));
        humidity.setText(weatherHashMap.get("Humidity"));
        pressure.setText(weatherHashMap.get("Pressure"));
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        Log.d(LOG_TAG, "Service has ended!");
    }

}
