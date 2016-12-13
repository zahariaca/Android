package com.samt.weatherclock.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.samt.weatherclock.R;

public class DialogChangeLocation extends DialogFragment {
    public final String LOG_TAG = DialogChangeLocation.class.getSimpleName();
    private EditText editText;
    private ImageButton imageButtonClose;
    private Button buttonAdd;
/*

    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.ib_exit:
                    break;
                case R.id.btn_commit:
                    changeLocationInSharedPref();
            }
            dismiss();
        }
    };
*/

    public DialogChangeLocation() {
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();



        builder.setView(inflater.inflate(R.layout.dialog_change_location,null))
                .setTitle("Input your location")
                .setPositiveButton("Commit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText = (EditText) DialogChangeLocation.this.getDialog().findViewById(R.id.et_name);
                        changeLocationInSharedPref(String.valueOf(editText.getText()));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogChangeLocation.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void changeLocationInSharedPref(String editText) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d(LOG_TAG, "Changing current city with: " + editText);
        editor.putString("CityName", editText);
        editor.commit();
        Toast.makeText(getActivity().getApplicationContext(), "Please refresh weather page for the update to take place", Toast.LENGTH_SHORT).show();
    }

/*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_change_location, container, false);

        editText = (EditText) rootView.findViewById(R.id.et_name);
        imageButtonClose = (ImageButton) rootView.findViewById(R.id.ib_exit);
        buttonAdd = (Button) rootView.findViewById(R.id.btn_commit);

        imageButtonClose.setOnClickListener(buttonOnClickListener);
        buttonAdd.setOnClickListener(buttonOnClickListener);

        return rootView;
    }*/
}