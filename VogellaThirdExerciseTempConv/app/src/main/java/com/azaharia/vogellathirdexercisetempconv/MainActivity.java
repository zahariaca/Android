package com.azaharia.vogellathirdexercisetempconv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText1);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                RadioButton celButton = (RadioButton) findViewById(R.id.button1);
                RadioButton fahButton = (RadioButton) findViewById(R.id.button2);
                if(editText.getText().length() == 0){
                    Toast.makeText(this,"Please input a valid number", Toast.LENGTH_SHORT).show();
                    return;
                }
                float inputValue = Float.parseFloat(editText.getText().toString());
                if(celButton.isChecked()){
                    editText.setText(String.valueOf(ConverterUtil.convertFahrenheitToCelsius(inputValue)) );
                    celButton.setChecked(false);
                    fahButton.setChecked(true);
                }else{
                    editText.setText(String.valueOf(ConverterUtil.convertCelsiusToFahrenheit(inputValue)) );
                    celButton.setChecked(true);
                    fahButton.setChecked(false);
                }
                break;
        }
    }
}
