package com.samt.weatherclock.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by AZaharia on 1/16/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public final String LOG_TAG = AlarmReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(LOG_TAG, "We are in the receiver");

        //create an intent to start the RingtoneService
        Intent serviceIntent = new Intent(context, RingtoneService.class);

        //start the RingtoneService
        context.startService(serviceIntent);
    }
}
