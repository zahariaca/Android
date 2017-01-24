package com.samt.weatherclock.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.samt.weatherclock.services.AlarmReceiver;
import com.samt.weatherclock.util.AlarmModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AlarmFragment extends Fragment implements DialogAdd.OnCompleteListener {
    public final String LOG_TAG = AlarmFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView textView;
    private LinearLayoutManager llm;
    private AlarmAdapter alarmAdapter;
    private List<AlarmModel> alarmMockDatas = new ArrayList<>();

    private Realm realm;
    private AlarmManager alarmManager;
    private Intent myIntent;
    private Map<String, PendingIntent> pendingIntents = new HashMap<String, PendingIntent>();

    public AlarmFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("AlarmRealm3").build();
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

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                String uniqueId = alarmMockDatas.get(viewHolder.getAdapterPosition()).getId();
                Log.d(LOG_TAG, "This is the id for the object that is being deleted : " + uniqueId);

                // Find in the Realm db all the references that match the card that is being swiped(its position)
                // by using the id of the object represented in that card
                // delete that reference from the DB
                RealmResults<AlarmModel> result = realm.where(AlarmModel.class).equalTo("id", uniqueId).findAll();
                realm.beginTransaction();
                result.deleteFirstFromRealm();
                realm.commitTransaction();
                Log.d(LOG_TAG, "Refrence deleted from Realm db");

                //cancel the pending intent of the alarm
                if (pendingIntents.containsKey(uniqueId)) {
                    alarmManager.cancel(pendingIntents.get(uniqueId));
                    pendingIntents.remove(uniqueId);
                    getActivity().sendBroadcast(myIntent);
                    Log.d(LOG_TAG, "Alarm canceled in alarmManager");
                }

                // remove the object from the List of alarms by using the swiped card position
                alarmMockDatas.remove(viewHolder.getAdapterPosition());
                Log.d(LOG_TAG, "Alarm card removed from view");

                // update the view
                if (!alarmMockDatas.isEmpty()) {
                    alarmAdapter.notifyDataSetChanged();
                } else {
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
        } else {
            textView.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    // This method creates an ArrayList that has three AlarmMockData objects
    private void initializeData() {

        RealmResults<AlarmModel> realmResults = realm.where(AlarmModel.class).findAll();
        for (AlarmModel alarmModel : realmResults) {
            alarmMockDatas.add(alarmModel);
        }

    }

    @Override
    public void onComplete(String id, String name, int hour, int minute) {
        Log.d(LOG_TAG, "Finally received the data in " + LOG_TAG);
        Log.d(LOG_TAG, "RECEIVED: " + name + " / " + id + " / " + hour + " / " + minute + " subString : " + id.substring(0, 4));
        Integer testId = Integer.parseInt(id.substring(0, 4), 16);

        RealmResults<AlarmModel> realmResults = realm.where(AlarmModel.class).findAll();
        for (AlarmModel alarmModel : realmResults) {
            Log.d(LOG_TAG, "REALM RESULTS: " + "Name: " + alarmModel.getName() + " UUID : " + alarmModel.getId() + " Hour: " + alarmModel.getHour() + " Minute :  " + alarmModel.getMinute());
        }

        if (alarmAdapter != null) {
            textView.setVisibility(View.INVISIBLE);
            alarmMockDatas.add(new AlarmModel(id, name, hour, minute));
            alarmAdapter.notifyDataSetChanged();
        } else {
            textView.setVisibility(View.INVISIBLE);
            alarmAdapter = new AlarmAdapter(alarmMockDatas);
            alarmMockDatas.add(new AlarmModel(id, name, hour, minute));
            recyclerView.setAdapter(alarmAdapter);
        }

        //creates a Calendar object that is set to the hour and minute selected in the DialogAdd dialog
        Calendar calendar = getACalendar(hour, minute);
        Log.d(LOG_TAG, "The time to be set in AlarmManager is : " + calendar.getTimeInMillis() + " actual time value : " + calendar.getTime());

        Log.d(LOG_TAG, "Creating intent and pending intent for the alarms");

        myIntent = new Intent(getActivity().getApplicationContext(), AlarmReceiver.class);

        // we create a HashMap to store all of our pending intents
        // this is needed to be able to actually cancel alarms when swiping
        pendingIntents.put(id, PendingIntent.getBroadcast(getActivity(), testId, myIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        Log.d(LOG_TAG, "Finished creating intent and pending intent for the alarms");

        //set the alarm manager
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntents.get(id));
        Log.d(LOG_TAG, "Set the AlarmManager");

    }

    private Calendar getACalendar(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}
