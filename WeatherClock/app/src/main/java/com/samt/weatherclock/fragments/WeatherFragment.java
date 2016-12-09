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
    private TextView weatherIcon;
    private TextView location;
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

        weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        weatherIcon.setText(getActivity().getString(R.string.weather_sunny));

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
}
