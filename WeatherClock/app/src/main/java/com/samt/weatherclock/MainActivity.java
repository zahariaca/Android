package com.samt.weatherclock;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.samt.weatherclock.adapters.AlarmAdapter;
import com.samt.weatherclock.adapters.ViewPagerAdapter;
import com.samt.weatherclock.fragments.AlarmFragment;
import com.samt.weatherclock.fragments.DialogAdd;
import com.samt.weatherclock.fragments.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<Person> persons;
    private AlarmAdapter alarmAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private Realm realm;
    private RealmConfiguration realmConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        realmConfiguration = new RealmConfiguration.Builder().name("weatherRealm").build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);

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
        adapter.addFragment(new WeatherFragment(), "WEATHER");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add:
                showDialogAdd();
                Toast.makeText(this, "Add an alarm button toast for testing", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh button toast for testing", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Settings toast for testing", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogAdd() {
        DialogAdd dialog = new DialogAdd();
        dialog.show(getFragmentManager(), "Add");
    }

    // This method creates an ArrayList that has three Person objects
    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old"));
        persons.add(new Person("Lavery Maiss", "25 years old"));
        persons.add(new Person("Lillie Watts", "35 years old"));
        persons.add(new Person("Emma Wilson", "23 years old"));
        persons.add(new Person("Lavery Maiss", "25 years old"));
        persons.add(new Person("Lillie Watts", "35 years old"));
        persons.add(new Person("Emma Wilson", "23 years old"));
        persons.add(new Person("Lavery Maiss", "25 years old"));
        persons.add(new Person("Lillie Watts", "35 years old"));
        persons.add(new Person("Emma Wilson", "23 years old"));
        persons.add(new Person("Lavery Maiss", "25 years old"));
        persons.add(new Person("Lillie Watts", "35 years old"));
        persons.add(new Person("Emma Wilson", "23 years old"));
        persons.add(new Person("Lavery Maiss", "25 years old"));
        persons.add(new Person("Lillie Watts", "35 years old"));
        persons.add(new Person("Emma Wilson", "23 years old"));
    }
}
