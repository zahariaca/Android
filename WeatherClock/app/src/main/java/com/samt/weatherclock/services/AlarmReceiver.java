package com.samt.weatherclock.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    public final String LOG_TAG = AlarmReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "We are in the receiver");

        //create an intent to start the RingtoneService
        Intent serviceIntent = new Intent(context, RingtoneService.class);

        //start the RingtoneService
        context.startService(serviceIntent);
    }
}
