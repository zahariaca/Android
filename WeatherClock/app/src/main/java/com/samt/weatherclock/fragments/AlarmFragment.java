package com.samt.weatherclock.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samt.weatherclock.Person;
import com.samt.weatherclock.R;
import com.samt.weatherclock.adapters.AlarmAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AZaharia on 12/7/2016.
 */

public class AlarmFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private AlarmAdapter alarmAdapter;
    private List<Person> persons;
    public AlarmFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


        llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        initializeData();
        alarmAdapter = new AlarmAdapter(persons);
        recyclerView.setAdapter(alarmAdapter);


        return rootView;
    }

    // This method creates an ArrayList that has three Person objects
    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("8:00", "08/12/2016"));
        persons.add(new Person("8:30", "32/14/2017"));
        persons.add(new Person("8:00", "08/12/2016"));
        persons.add(new Person("8:30", "32/14/2017"));
        persons.add(new Person("8:00", "08/12/2016"));
        persons.add(new Person("8:30", "32/14/2017"));
        persons.add(new Person("8:00", "08/12/2016"));
        persons.add(new Person("8:30", "32/14/2017"));
        persons.add(new Person("8:00", "08/12/2016"));
        persons.add(new Person("8:30", "32/14/2017"));
        persons.add(new Person("8:00", "08/12/2016"));
        persons.add(new Person("8:30", "32/14/2017"));

    }
}
