package com.azaharia.fragmentswithjava;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyFragment frag = new MyFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.add(R.id.activity_main, frag, "MyFragment");
        transaction.commit();
    }
}
