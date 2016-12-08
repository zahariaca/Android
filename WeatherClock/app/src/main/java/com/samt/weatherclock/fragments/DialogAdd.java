package com.samt.weatherclock.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.samt.weatherclock.R;

public class DialogAdd extends DialogFragment {

    private ImageButton imageButtonClose;
    private Button buttonAdd;

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

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_add, container, false);
        imageButtonClose = (ImageButton) rootView.findViewById(R.id.ib_exit);
        buttonAdd = (Button) rootView.findViewById(R.id.btn_commit);
        imageButtonClose.setOnClickListener(buttonOnClickListener);
        buttonAdd.setOnClickListener(buttonOnClickListener);

        return rootView;
    }
}