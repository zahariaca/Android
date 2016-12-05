package com.samt.weatherclock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AZaharia on 12/5/2016.
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        AlarmAdapter alarmAdapter = new AlarmAdapter(persons);
        mRecyclerView.setAdapter(alarmAdapter);

    }

    // This method creates an ArrayList that has three Person objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.
    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old"));
        persons.add(new Person("Lavery Maiss", "25 years old"));
        persons.add(new Person("Lillie Watts", "35 years old"));
    }
}
