package com.samt.weatherclock.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.samt.weatherclock.R;
import com.samt.weatherclock.util.AlarmModel;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DialogAdd extends DialogFragment {

    public interface OnCompleteListener {
        void onComplete(String id, String name, int hour, int minute);
    }

    public final String LOG_TAG = DialogAdd.class.getSimpleName();
    private OnCompleteListener onCompleteListeneristener;
    private EditText editText;
    private TimePicker timePicker;
    private String name;
    private int alarmHour;
    private int alarmMinute;
    private Realm realm;

    @Override
    public void onStart() {
        super.onStart();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("AlarmRealm3").build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.main_activity_dialog_add, null);

        editText = (EditText) rootView.findViewById(R.id.et_name);
        timePicker = (TimePicker) rootView.findViewById(R.id.timePicker);

        alarmHour = timePicker.getCurrentHour();
        alarmMinute = timePicker.getCurrentMinute();

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.d(LOG_TAG, "Current selected time: " + hourOfDay + ":" + minute);

                alarmHour = hourOfDay;
                alarmMinute = minute;
            }
        });

        builder.setView(rootView)
                .setTitle("Create an alarm")
                .setPositiveButton("Commit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addAlarm();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAdd.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public DialogAdd() {
    }

    private void addAlarm() {
        String id = UUID.randomUUID().toString();
        if (!(String.valueOf(editText.getText()).equals(""))) {
            name = String.valueOf(editText.getText());
        } else {
            name = "Alarm";
        }
        //passing the name, hour and minute back to the AlarmFragment
        this.onCompleteListeneristener.onComplete(id,name, alarmHour, alarmMinute);
        //writting the values into the Realm database
        realm.beginTransaction();
        AlarmModel alarmModel = realm.createObject(AlarmModel.class, id);
        alarmModel.setName(name);
        alarmModel.setHour(alarmHour);
        alarmModel.setMinute(alarmMinute);
        alarmModel.setTimeFormated(alarmHour + ":" + alarmMinute);
        realm.commitTransaction();
    }

    public void setOnCompleteListeneristener(OnCompleteListener onCompleteListeneristener) {
        this.onCompleteListeneristener = onCompleteListeneristener;
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}