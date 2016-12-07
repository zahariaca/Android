package com.samt.weatherclock.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samt.weatherclock.R;

/**
 * Created by AZaharia on 12/7/2016.
 */

public class AlarmFragment extends Fragment {

    public AlarmFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }
}
