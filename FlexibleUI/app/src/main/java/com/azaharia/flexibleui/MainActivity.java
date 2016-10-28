package com.azaharia.flexibleui;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentA.Communicator {
    FragmentManager manager;
    FragmentA fragmentA;
    FragmentB fragmentB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=getFragmentManager();
        fragmentA = (FragmentA) manager.findFragmentById(R.id.fragment);
        fragmentA.setCommunicator(this);

    }

    @Override
    public void respond(int position) {
        fragmentB = (FragmentB) manager.findFragmentById(R.id.fragment2);
        if(fragmentB != null && fragmentB.isVisible()){
            fragmentB.changeData(position);
        }else{
            Intent intent = new Intent(this, AnotherActivity.class);
            intent.putExtra("position",position);
            startActivity(intent);

        }
    }
}
