package com.azaharia.intenttestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by AZaharia on 10/26/2016.
 */

public class NextActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_view);
        String name = null;
        Intent intent = getIntent();
        name = intent.getStringExtra(getString(R.string.key_names));
        textView = (TextView) findViewById(R.id.textView2);
        textView.setText(name);
        textView.setTextSize(22);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startPreviousActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void startPreviousActivity() {
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

}
