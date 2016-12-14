package com.samt.weatherclock.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.samt.weatherclock.R;
import com.samt.weatherclock.adapters.ViewPagerAdapter;
import com.samt.weatherclock.fragments.AlarmFragment;
import com.samt.weatherclock.fragments.DialogAdd;
import com.samt.weatherclock.fragments.DialogChangeLocation;
import com.samt.weatherclock.fragments.WeatherFragment;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity implements DialogAdd.OnCompleteListener {
    public final String LOG_TAG = MainActivity.class.getSimpleName();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private AlarmFragment alarmFragment = new AlarmFragment();
    private WeatherFragment weatherFragment = new WeatherFragment();
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Realm.init(this);

        Log.d(LOG_TAG, "Creating toolbar");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.d(LOG_TAG, "Creating viewPager");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        Log.d(LOG_TAG, "Creating tabs");
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Log.d(LOG_TAG, "Getting weather data from previous activity");
        Intent i = getIntent();
        Log.d("MAIN", i.getStringExtra("CityName"));
        Log.d("MAIN", i.getStringExtra("Updated"));
        Log.d("MAIN", i.getStringExtra("Id"));
        Log.d("MAIN", i.getStringExtra("Sunrise"));
        Log.d("MAIN", i.getStringExtra("Sunset"));
        Log.d("MAIN", i.getStringExtra("Description"));
        Log.d("MAIN", i.getStringExtra("Temperature"));
        Log.d("MAIN", i.getStringExtra("Humidity"));
        Log.d("MAIN", i.getStringExtra("Pressure"));

        passWeatherData(i);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(alarmFragment, "ALARMS");
        adapter.addFragment(weatherFragment, "WEATHER");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                showDialogAdd();
                Toast.makeText(this, "Add an alarm button toast for testing", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_refresh:
                return false;
            case R.id.action_settings:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    showDialogChangeLocation();
                } else {
                    Toast.makeText(this, "Settings menu is for Android 6+", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogAdd() {
        DialogAdd dialog = new DialogAdd();
        dialog.setOnCompleteListeneristener(this);
        dialog.show(getFragmentManager(), "Add");
    }

    private void showDialogChangeLocation() {
        DialogChangeLocation dialog = new DialogChangeLocation();
        dialog.show(getFragmentManager(), "ChangeLocation");
    }

    @Override
    public void onComplete(String name, int hour, int minute) {
        Log.d(LOG_TAG, "data received to Activity... send to alarm fragment");
        Log.d(LOG_TAG, "RECEIVED: " + name + " / " + hour + " / " + minute);
        alarmFragment.onComplete(name, hour, minute);
    }

    private void passWeatherData(Intent i) {
        Log.d(LOG_TAG, "Passing weather data to the WeatherFragment as Bundled arguments");
        Bundle args = new Bundle();
        args.putString("CityName", i.getStringExtra("CityName"));
        args.putString("Updated", i.getStringExtra("Updated"));
        args.putString("Id", i.getStringExtra("Id"));
        args.putString("Sunrise", i.getStringExtra("Sunrise"));
        args.putString("Sunset", i.getStringExtra("Sunset"));
        args.putString("Description", i.getStringExtra("Description"));
        args.putString("Temperature", i.getStringExtra("Temperature"));
        args.putString("Humidity", i.getStringExtra("Humidity"));
        args.putString("Pressure", i.getStringExtra("Pressure"));
        weatherFragment.setArguments(args);
    }
}
