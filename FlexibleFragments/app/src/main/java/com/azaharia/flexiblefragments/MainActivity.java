package com.azaharia.flexiblefragments;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentA.Communicator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getFragmentManager();
        FragmentA fragmentA = (FragmentA) manager.findFragmentById(R.id.fragment);
        fragmentA.setCommunicator(this);
    }

    @Override
    public void respond(int i) {
        FragmentManager manager = getFragmentManager();
        FragmentB fragmentB = (FragmentB) manager.findFragmentById(R.id.fragment2);
        fragmentB.changeData(i);
    }
}
