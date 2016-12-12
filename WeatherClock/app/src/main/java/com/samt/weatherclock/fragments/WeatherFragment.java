package com.samt.weatherclock.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.samt.weatherclock.util.GpsData;
import com.samt.weatherclock.R;
import com.samt.weatherclock.util.FetchWeatherTask;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by AZaharia on 12/7/2016.
 */

public class WeatherFragment extends Fragment implements FetchWeatherTask.LoadingTaskFinishedListener {
    public final String LOG_TAG = WeatherFragment.class.getSimpleName();
    private GpsData gpsData;
    private Typeface weatherFont;
    private TextView location;
    private TextView updated;
    private TextView weatherIcon;
    private TextView description;
    private TextView temperature;
    private TextView humidity;
    private TextView pressure;
    private ImageButton btn_refresh;

    public WeatherFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        btn_refresh = (ImageButton) rootView.findViewById(R.id.btn_refresh);
        location = (TextView) rootView.findViewById(R.id.tv_location_city);
        updated = (TextView) rootView.findViewById(R.id.tv_location_lastupdated);
        weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        description = (TextView) rootView.findViewById(R.id.tv_weatherType);
        temperature = (TextView) rootView.findViewById(R.id.tv_temperature);
        humidity = (TextView) rootView.findViewById(R.id.tv_humidity);
        pressure = (TextView) rootView.findViewById(R.id.tv_pressure);

        setWeather();

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Starterd");
                try {
                    Log.d(LOG_TAG, "fetchTask");
                    Toast.makeText(getContext(), "Fetching fresh weather data...", Toast.LENGTH_SHORT).show();
                    fetchWeather();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh: {
                Toast.makeText(getContext(), "Fetching fresh weather data...", Toast.LENGTH_SHORT).show();
                fetchWeather();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //Checking the value of the weather id, and setting the appropriate icon for our WeatherFragment
    private void setWeatherIcon(String iconId, String sunriseString, String sunsetString) {
        int id = Integer.parseInt(iconId) / 100;
        long sunrise = Long.parseLong(sunriseString) * 1000;
        long sunset = Long.parseLong(sunsetString) * 1000;

        String icon = "";
        if (id == 8) {
            long currentTime = new Date().getTime();
            Log.d(LOG_TAG, "currentTime: " + currentTime);
            Log.d(LOG_TAG, "sunrise: " + sunrise);
            Log.d(LOG_TAG, "sunset: " + sunset);
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 6:
                    icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);

    }

    // Fetching weather data for when the refresh button is clicked
    public void fetchWeather() {

        try {
            gpsData = new GpsData(getActivity());
            gpsData.getData();
            Log.d(LOG_TAG, "LATITUDE: " + gpsData.getLatitude());
            Log.d(LOG_TAG, "LONGITUDE: " + gpsData.getLongitude());
            Log.d(LOG_TAG, "inside of fetchWeathe method");
            new FetchWeatherTask(this).execute(gpsData.getLatitude(), gpsData.getLongitude());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (gpsData != null) {
                gpsData = null;
            }
        }
    }

    // Receiving the weather data with the help of the LoadingTaskFinishedListener interface from FetchWeatherTask
    @Override
    public void onTaskFinished(HashMap<String, String> weatherHashMap) {
        Log.d(LOG_TAG, weatherHashMap.get("Updated") + " -> FROM WEATHER FRAGMENT REFRESH");
        setWeatherForRefresh(weatherHashMap);
    }

    //Setting the weather for when the application first runs
    public void setWeather() {
        location.setText(getArguments().getString("CityName"));
        updated.setText(getArguments().getString("Updated"));
        Log.d(LOG_TAG, "Starting to set weather icon");
        setWeatherIcon(getArguments().getString("Id"), getArguments().getString("Sunrise"), getArguments().getString("Sunset"));
        Log.d(LOG_TAG, "Finished weather icon");
        description.setText(getArguments().getString("Description"));
        temperature.setText(getArguments().getString("Temperature"));
        humidity.setText(getArguments().getString("Humidity"));
        pressure.setText(getArguments().getString("Pressure"));
    }

    //Setting the weather for when the refresh button is clicked
    public void setWeatherForRefresh(HashMap<String, String> weatherHashMap) {
        location.setText(weatherHashMap.get("CityName"));
        updated.setText(weatherHashMap.get("Updated"));
        Log.d(LOG_TAG, "Starting to set weather icon");
        setWeatherIcon(weatherHashMap.get("Id"), weatherHashMap.get("Sunrise"), weatherHashMap.get("Sunset"));
        Log.d(LOG_TAG, "Finished weather icon");
        description.setText(weatherHashMap.get("Description"));
        temperature.setText(weatherHashMap.get("Temperature"));
        humidity.setText(weatherHashMap.get("Humidity"));
        pressure.setText(weatherHashMap.get("Pressure"));
    }
}
