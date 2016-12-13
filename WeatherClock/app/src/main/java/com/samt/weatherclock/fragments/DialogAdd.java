package com.samt.weatherclock.fragments;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.samt.weatherclock.MainActivity;
import com.samt.weatherclock.R;
import com.samt.weatherclock.util.AlarmModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;

public class DialogAdd extends DialogFragment implements RealmChangeListener {


    @Override
    public void onChange(Object element) {
        Log.d(LOG_TAG, "Something changed in the REALM");
    }

    public interface OnCompleteListener {
        void onComplete(String name, int hour, int minute);
    }

    public final String LOG_TAG = DialogAdd.class.getSimpleName();
    private OnCompleteListener onCompleteListeneristener;
    private EditText editText;
    private ImageButton imageButtonClose;
    private Button buttonAdd;
    private TimePicker timePicker;
    private String name;
    private int alarmHour;
    private int alarmMinute;
    private Realm realm;

    @Override
    public void onStart() {
        super.onStart();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("AlarmReal").build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
    }

    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.ib_exit:
                    break;
                case R.id.btn_commit:
                    addAlarm();
            }
            dismiss();
        }
    };

    public DialogAdd() {
    }

    private void addAlarm() {
        name = String.valueOf(editText.getText());
        this.onCompleteListeneristener.onComplete(name, alarmHour, alarmMinute);
        realm.beginTransaction();
        AlarmModel alarmModel = realm.createObject(AlarmModel.class);
        alarmModel.setName(name);
        alarmModel.setHour(alarmHour);
        alarmModel.setMinute(alarmMinute);
        alarmModel.setTimeFormated(alarmHour+":"+alarmMinute);
        realm.commitTransaction();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_add, container, false);

        editText = (EditText) rootView.findViewById(R.id.et_name);
        imageButtonClose = (ImageButton) rootView.findViewById(R.id.ib_exit);
        buttonAdd = (Button) rootView.findViewById(R.id.btn_commit);
        timePicker = (TimePicker) rootView.findViewById(R.id.timePicker);

        alarmHour = timePicker.getCurrentHour();
        alarmMinute = timePicker.getCurrentMinute();


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                alarmHour = hourOfDay;
                alarmMinute = minute;
                Log.d(LOG_TAG, "HOUR: " + hourOfDay);
                Log.d(LOG_TAG, "MINUTE: " +minute);
            }
        });


        imageButtonClose.setOnClickListener(buttonOnClickListener);
        buttonAdd.setOnClickListener(buttonOnClickListener);

        return rootView;
    }

    public void setOnCompleteListeneristener(OnCompleteListener onCompleteListeneristener){
        this.onCompleteListeneristener = onCompleteListeneristener;
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}