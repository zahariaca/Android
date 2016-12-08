package com.samt.weatherclock.fragments;

import android.graphics.Typeface;
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

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by AZaharia on 12/7/2016.
 */

public class WeatherFragment extends Fragment {
    Typeface weatherFont;
    TextView weatherIcon;
    TextView location;
    private Button btn;
    Realm realm;
    private WeatherDataMock data = new WeatherDataMock(1, "Bucharest, RO", "Last update:  Dec 08, 2014 13:45:58 AM",
                                                        "&#xf014;", "Sunny",
                                                        "Humidity: 59%", "Pressure: 977 hPa", "-1 °C");

    public WeatherFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        weatherIcon.setText(getActivity().getString(R.string.weather_sunny));

        try {
            Realm.init(getActivity());
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
            Realm.setDefaultConfiguration(realmConfiguration);

            realm = Realm.getDefaultInstance();
 /*           realm.beginTransaction();
            realm.copyToRealm(data);
            realm.commitTransaction();*/

        }catch (Exception e) {
            e.printStackTrace();
        }

        location = (TextView) rootView.findViewById(R.id.tv_location_city);

        btn = (Button) rootView.findViewById(R.id.btn_test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<WeatherDataMock> query = realm.where(WeatherDataMock.class)
                        .findAll();
                for(WeatherDataMock c: query) {
                    realm.beginTransaction();
                    c.setLocation("Moscow");
                    realm.commitTransaction();
                    location.setText(c.getLocation());
                    Log.d("REALMQUERY", c.getLocation() + " " + c.getHumidity());
                }
            }
        });

        return rootView;
    }
}
