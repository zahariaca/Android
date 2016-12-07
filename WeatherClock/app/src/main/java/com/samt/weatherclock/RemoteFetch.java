package com.samt.weatherclock;

import android.content.Context;
import android.net.Uri;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteFetch {
    /*TODO
        add parsing of the weather for 16 days
    */
    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";

    public static JSONObject getJSON(Context context, String city){
        try {
            String format = "json";
            String units= "metric";
            int numDays = 16;
            String key = "aeefe119edb695e78e33dd7eb70ef6d2";
            final String FORECAST_BASE_URL="http://api.openweathermap.org/data/2.5/forecast/daily?";
            final String QUERY_PARAM="q";
            final String FORMAT_PARAM="mode";
            final String UNITS_PARAM="units";
            final String DAYS_PARAM="cnt";
            final String KEY_PARAM="APPID";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(FORMAT_PARAM, format)
                    .appendQueryParameter(UNITS_PARAM, units)
                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                    .appendQueryParameter(KEY_PARAM, key)
                    .build();


            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

 /*           connection.addRequestProperty("x-api-key",
                    context.getString(R.string.open_weather_maps_app_id));*/

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }
}
