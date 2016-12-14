package com.samt.weatherclock.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.samt.weatherclock.R;

import com.samt.weatherclock.adapters.AlarmAdapter;
import com.samt.weatherclock.util.AlarmModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AlarmFragment extends Fragment implements DialogAdd.OnCompleteListener {
    public final String LOG_TAG = AlarmFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView textView;
    private LinearLayoutManager llm;
    private AlarmAdapter alarmAdapter;
    private List<AlarmModel> alarmMockDatas = new ArrayList<>();;
    private Realm realm;

    public AlarmFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("AlarmReal").build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_activity_fragment_alarm, container, false);

        textView = (TextView) rootView.findViewById(R.id.tv_no_data);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


        llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                RealmResults<AlarmModel> result = realm.where(AlarmModel.class).equalTo("name",alarmMockDatas.get(viewHolder.getAdapterPosition()).getName()).findAll();
                realm.beginTransaction();
                result.deleteFirstFromRealm();
                realm.commitTransaction();

                alarmMockDatas.remove(viewHolder.getAdapterPosition());

                if(!alarmMockDatas.isEmpty()) {
                    alarmAdapter.notifyDataSetChanged();
                }else {
                    textView.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getActivity(), "Removed the alarm!", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        initializeData();
        if (!alarmMockDatas.isEmpty()) {
            textView.setVisibility(View.INVISIBLE);
            alarmAdapter = new AlarmAdapter(alarmMockDatas);
            recyclerView.setAdapter(alarmAdapter);
        }else {
            textView.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    // This method creates an ArrayList that has three AlarmMockData objects
    private void initializeData() {

        RealmResults<AlarmModel> realmResults = realm.where(AlarmModel.class).findAll();
        for (AlarmModel alarmModel : realmResults){
            alarmMockDatas.add(alarmModel);
        }

    }

    @Override
    public void onComplete(String name, int hour, int minute) {
        Log.d(LOG_TAG, "Finally received the data in " + LOG_TAG);
        Log.d(LOG_TAG, "RECEIVED: " + name + " / " + hour + " / " + minute);
        if(alarmAdapter != null) {
            textView.setVisibility(View.INVISIBLE);
            alarmMockDatas.add(new AlarmModel(name, hour, minute));
            alarmAdapter.notifyDataSetChanged();
        }else {
            textView.setVisibility(View.INVISIBLE);
            alarmAdapter = new AlarmAdapter(alarmMockDatas);
            alarmMockDatas.add(new AlarmModel(name, hour, minute));
            recyclerView.setAdapter(alarmAdapter);
        }

        RealmResults<AlarmModel> realmResults = realm.where(AlarmModel.class).findAll();
        for (AlarmModel alarmModel : realmResults){
            Log.d(LOG_TAG,"REAL RESULTS: "+ "Name: " + alarmModel.getName() + " " +alarmModel.getHour() + " " + alarmModel.getMinute());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}
