package com.samt.weatherclock.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samt.weatherclock.util.AlarmMockData;
import com.samt.weatherclock.R;

import com.samt.weatherclock.adapters.AlarmAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by AZaharia on 12/7/2016.
 */

public class AlarmFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private AlarmAdapter alarmAdapter;
    private List<AlarmMockData> alarmMockDatas;
    private Realm realm;
    private RealmConfiguration realmConfiguration;


    public AlarmFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


        llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        initializeData();
        alarmAdapter = new AlarmAdapter(alarmMockDatas);
        recyclerView.setAdapter(alarmAdapter);

        return rootView;
    }

    // This method creates an ArrayList that has three AlarmMockData objects
    private void initializeData() {
        alarmMockDatas = new ArrayList<>();
        alarmMockDatas.add(new AlarmMockData("8:00", "08/12/2016"));
        alarmMockDatas.add(new AlarmMockData("8:30", "32/14/2017"));
        alarmMockDatas.add(new AlarmMockData("8:00", "08/12/2016"));
        alarmMockDatas.add(new AlarmMockData("8:30", "32/14/2017"));
        alarmMockDatas.add(new AlarmMockData("8:00", "08/12/2016"));
        alarmMockDatas.add(new AlarmMockData("8:30", "32/14/2017"));
        alarmMockDatas.add(new AlarmMockData("8:00", "08/12/2016"));
        alarmMockDatas.add(new AlarmMockData("8:30", "32/14/2017"));
        alarmMockDatas.add(new AlarmMockData("8:00", "08/12/2016"));
        alarmMockDatas.add(new AlarmMockData("8:30", "32/14/2017"));
        alarmMockDatas.add(new AlarmMockData("8:00", "08/12/2016"));
        alarmMockDatas.add(new AlarmMockData("8:30", "32/14/2017"));

    }
}
