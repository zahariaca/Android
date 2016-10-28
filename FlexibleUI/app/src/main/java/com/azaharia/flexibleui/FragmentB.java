package com.azaharia.flexibleui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by AZaharia on 10/28/2016.
 */

public class FragmentB extends Fragment{
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewB = inflater.inflate(R.layout.fragment_b,container,false);
        textView = (TextView) viewB.findViewById(R.id.textView);
        return viewB;
    }

    public void changeData(int position){
        String[] descriptions = getResources().getStringArray(R.array.descriptions);
        textView.setText(descriptions[position]);
    }
}

