package com.azaharia.fragmentcommunication;

import android.app.Fragment;
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
    String data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewB = inflater.inflate(R.layout.fragment_b,container,false);
        if(savedInstanceState == null){
            data = "The button was clicked: 0 times!";
            TextView textView = (TextView) viewB.findViewById(R.id.textView);
            textView.setText(data);
        }else{
            data = savedInstanceState.getString("data");
            TextView textView = (TextView) viewB.findViewById(R.id.textView);
            textView.setText(data);
        }
        return viewB;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView= (TextView) getActivity().findViewById(R.id.textView);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("data", data);
    }

    public void changeText (String data){
        this.data = data;
        textView.setText(data);
    }
}
