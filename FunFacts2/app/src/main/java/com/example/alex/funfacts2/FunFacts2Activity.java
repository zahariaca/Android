package com.example.alex.funfacts2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class FunFacts2Activity extends ActionBarActivity {

    public static final String TAG = FunFacts2Activity.class.getSimpleName();

    private FactBook mFactBook = new FactBook();
    private ColorWheel mColorWheel = new ColorWheel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_facts2);

        // Declare our View variables and assign the View from the layout file
        final TextView factLabel = (TextView) findViewById(R.id.funFactTextView);
        final Button showFactButton = (Button) findViewById(R.id.funFactsButton);
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fact = mFactBook.getFact();
                //Update the label with our dynamic fact
                factLabel.setText(fact);

                int color = mColorWheel.getColor();
                relativeLayout.setBackgroundColor(color);
                showFactButton.setTextColor(color);

            }
        };
        showFactButton.setOnClickListener(listener);

        Toast.makeText(this, "Yay! Our activity was created", Toast.LENGTH_LONG).show();

        Log.d(TAG, "We're logging from the onCreate method!");
    }
}
