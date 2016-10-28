package com.azaharia.flexiblefragments;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by AZaharia on 10/28/2016.
 */

public class FragmentB extends Fragment {
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewB = inflater.inflate(R.layout.fragment_b, container, false);
        return viewB;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = (TextView) getActivity().findViewById(R.id.textView);
    }

    public void changeData(int i) {
        Resources res = getResources();
        String[] descriptions = res.getStringArray(R.array.descriptions);
        textView.setText(descriptions[i]);
    }
}
