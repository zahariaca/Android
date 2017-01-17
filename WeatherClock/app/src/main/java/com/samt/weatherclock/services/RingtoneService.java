package com.samt.weatherclock.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.samt.weatherclock.R;

/**
 * Created by AZaharia on 1/17/2017.
 */

public class RingtoneService extends Service {
    private final String LOG_TAG = RingtoneService.class.getSimpleName();
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(LOG_TAG, "service starting : " +startId + " : " + intent );

        mediaPlayer = MediaPlayer.create(this, R.raw.test_sound);
        mediaPlayer.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.release();
        Log.e(LOG_TAG, "Service has ended!");
    }
}
