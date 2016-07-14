package com.azaharia.vogellafirstexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RadioGroup group1 = (RadioGroup) findViewById(R.id.orientation);
        group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.horizontal:
                        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
                        break;
                    case R.id.vertical:
                        radioGroup.setOrientation(LinearLayout.VERTICAL);
                        break;
                }
            }
        });
    }

    public void onClick(View view){
        ImageView imageView = (ImageView) findViewById(R.id.myicon);
        imageView.setImageResource(R.drawable.ic_offline);
    }
}
