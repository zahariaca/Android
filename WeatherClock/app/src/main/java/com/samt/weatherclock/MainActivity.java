package com.samt.weatherclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.samt.weatherclock.adapters.ViewPagerAdapter;
import com.samt.weatherclock.fragments.AlarmFragment;
import com.samt.weatherclock.fragments.DialogAdd;
import com.samt.weatherclock.fragments.DialogChangeLocation;
import com.samt.weatherclock.fragments.WeatherFragment;


public class MainActivity extends AppCompatActivity {
    public final String LOG_TAG = MainActivity.class.getSimpleName();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private WeatherFragment weatherFragment = new WeatherFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


/*        realmConfiguration = new RealmConfiguration.Builder().name("weatherRealm").build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);*/

        /*initializeData();

        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
        fetchWeatherTask.execute("Bucharest");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        alarmAdapter = new AlarmAdapter(persons);
        mRecyclerView.setAdapter(alarmAdapter);*/

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AlarmFragment(), "ALARMS");
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
                Toast.makeText(this, "Settings toast for testing", Toast.LENGTH_SHORT).show();
                showDialogChangeLocation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogAdd() {
        DialogAdd dialog = new DialogAdd();
        dialog.show(getFragmentManager(), "Add");
    }

    private void showDialogChangeLocation() {
        DialogChangeLocation dialog = new DialogChangeLocation();
        dialog.show(getFragmentManager(), "ChangeLocation");
    }

}
