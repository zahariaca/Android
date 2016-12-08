package com.samt.weatherclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class LoadingScreen extends AppCompatActivity {
    private Realm realm;
    private RealmConfiguration realmConfiguration;
    private WeatherDataMock data = new WeatherDataMock(1, "Bucharest, RO", "Last update:  Dec 08, 2014 13:45:58 AM",
            "&#xf014;", "Sunny",
            "Humidity: 59%", "Pressure: 977 hPa", "-1 °C");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        Realm.init(this);
        realmConfiguration = new RealmConfiguration.Builder().name("weatherRealmFromLoadingScreen").build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);

/*        realm.beginTransaction();
        realm.copyToRealm(data);
        realm.commitTransaction();*/
        realm.close();

        new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1000);
                } catch (Exception e) {

                } finally {



                    Intent i = new Intent(LoadingScreen.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }.start();
    }
}
