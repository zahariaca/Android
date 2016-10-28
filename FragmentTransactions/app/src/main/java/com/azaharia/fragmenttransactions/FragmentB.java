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

public class FragmentB extends Fragment {
    @Override
    public void onAttach(Context context) {
        Log.d("AZAHARIA", "FragmentB onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("AZAHARIA", "FragmentB onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewA = inflater.inflate(R.layout.fragment_b, container, false);
        Log.d("AZAHARIA", "FragmentB onCreateView");
        return viewA;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("AZAHARIA", "FragmentB onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        Log.d("AZAHARIA", "FragmentB onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("AZAHARIA", "FragmentB onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("AZAHARIA", "FragmentB onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("AZAHARIA", "FragmentB onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("AZAHARIA", "FragmentB onDetach");
        super.onDetach();
    }
}
