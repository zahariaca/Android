package com.azaharia.fragmenttransactions;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by AZaharia on 10/28/2016.
 */

public class FragmentA extends Fragment {
    @Override
    public void onAttach(Context context) {
        Log.d("AZAHARIA", "FragmentA onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("AZAHARIA", "FragmentA onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewA = inflater.inflate(R.layout.fragment_a, container, false);
        Log.d("AZAHARIA", "FragmentA onCreateView");
        return viewA;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("AZAHARIA", "FragmentA onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        Log.d("AZAHARIA", "FragmentA onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("AZAHARIA", "FragmentA onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("AZAHARIA", "FragmentA onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("AZAHARIA", "FragmentA onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("AZAHARIA", "FragmentA onDetach");
        super.onDetach();
    }
}
