package com.samt.weatherclock.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.samt.weatherclock.R;

public class DialogChangeLocation extends DialogFragment {
    public final String LOG_TAG = DialogChangeLocation.class.getSimpleName();
    private EditText editText;

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

        builder.setView(inflater.inflate(R.layout.main_activity_dialog_change_location,null))
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
}