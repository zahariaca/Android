package com.example.alex.testproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void clickFunction(View view){
        EditText textField = (EditText) findViewById(R.id.editText);
        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setImageResource(R.drawable.image2);

        Toast.makeText(MainActivity.this, "Hello, is it me your looking for?", Toast.LENGTH_LONG).show();

        textView.setText(textField.getText());

        Log.i("TestClickFunction", textField.getText().toString());
    }

}
