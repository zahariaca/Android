package com.samt.weatherclock.fragments;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.samt.weatherclock.R;
import com.samt.weatherclock.WeatherDataMock;
import com.samt.weatherclock.weatherapi.FetchWeatherTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by AZaharia on 12/7/2016.
 */

public class WeatherFragment extends Fragment {
    private Typeface weatherFont;


    private TextView location;
    private TextView updated;
    private TextView weatherIcon;
    private TextView description;
    private TextView temperature;
    private TextView humidity;
    private TextView pressure;

    private Button btn;

    public WeatherFragment(){}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);


        location = (TextView) rootView.findViewById(R.id.tv_location_city);
        updated = (TextView) rootView.findViewById(R.id.tv_location_lastupdated);
        weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        description = (TextView) rootView.findViewById(R.id.tv_weatherType);
        temperature = (TextView) rootView.findViewById(R.id.tv_temperature);
        humidity = (TextView) rootView.findViewById(R.id.tv_humidity);
        pressure = (TextView) rootView.findViewById(R.id.tv_pressure);




        //weatherIcon.setText(getActivity().getString(R.string.weather_sunny));

/*

        test = new FetchWeatherTask(){
            @Override
            protected void onPostExecute(HashMap<String, String> stringStringHashMap) {
                Log.d("HashMapTest", stringStringHashMap.get("Day"));
                testS = stringStringHashMap.get("Day");
                super.onPostExecute(stringStringHashMap);

            }
        }.execute("Bucharest");
*/

        location.setText(getArguments().getString("CityName"));
        updated.setText(getArguments().getString("Updated"));
        setWeatherIcon(getArguments().getString("Id"),getArguments().getString("Sunrise"),getArguments().getString("Sunset"));
        description.setText(getArguments().getString("Description"));
        temperature.setText(getArguments().getString("Temperature"));
        humidity.setText(getArguments().getString("Humidity"));
        pressure.setText(getArguments().getString("Pressure"));

        btn = (Button) rootView.findViewById(R.id.btn_test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HashMapTest", "Starterd");
                try {
                    Log.d("HashMapTest", "fetchTask");


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    private void setWeatherIcon(String iconId, String sunriseString, String sunsetString){
        int id = Integer.parseInt(iconId) /100;
        long sunrise = Long.parseLong(sunriseString);
        long sunset = Long.parseLong(sunsetString);

        String icon = "";
        if(id == 8){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
                case 2 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);

    }
}
