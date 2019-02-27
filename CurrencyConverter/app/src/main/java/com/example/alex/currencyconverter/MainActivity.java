package com.example.alex.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void clickFunction(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);

        double convertedValue = Double.parseDouble(editText.getText().toString())*4.12;

        Toast.makeText(MainActivity.this, editText.getText().toString() + "$ = " + convertedValue + " RON", Toast.LENGTH_LONG).show();
    }
}
