package com.samt.weatherclock.util;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by AZaharia on 12/8/2016.
 */

public class FetchWeatherTask extends AsyncTask<String, Void, HashMap<String,String>>{
    public interface LoadingTaskFinishedListener {
        void onTaskFinished(HashMap<String,String> weatherHashMap); // If you want to pass something back to the listener add a param to this method
    }

    public final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
    private final LoadingTaskFinishedListener finishedListener ;

    public FetchWeatherTask(){

        finishedListener = null;
    }

    public FetchWeatherTask(LoadingTaskFinishedListener finishedListener){
        this.finishedListener = finishedListener;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private HashMap<String, String> getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String OWM_WEATHER = "weather";
        final String OWM_MAIN = "main";
        final String OWM_SYS = "sys";
        final String OWM_COUNTRY = "country";
        final String OWM_NAME = "name";
        final String OWM_DESCRIPTION = "description";
        final String OWM_ID = "id";
        final String OWM_SUNRISE = "sunrise";
        final String OWM_SUNSET = "sunset";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_HUMIDITY = "humidity";
        final String OWM_PRESSURE = "pressure";

        HashMap<String, String> hashMap = new HashMap<String, String>();

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONObject weather = forecastJson.getJSONArray(OWM_WEATHER).getJSONObject(0);
        JSONObject main = forecastJson.getJSONObject(OWM_MAIN);
        JSONObject sys = forecastJson.getJSONObject(OWM_SYS);

        String country = sys.getString(OWM_COUNTRY);
        String cityName = forecastJson.getString(OWM_NAME);
        String description = weather.getString(OWM_DESCRIPTION);
        String id = weather.getString(OWM_ID);
        String sunrise = sys.getString(OWM_SUNRISE);
        String sunset = sys.getString(OWM_SUNSET);
        String temperature = main.getString(OWM_TEMPERATURE);
        String humidity = main.getString(OWM_HUMIDITY);
        String pressure = main.getString(OWM_PRESSURE);


        DateFormat df = DateFormat.getDateTimeInstance();
        String updatedOn = df.format(new Date(forecastJson.getLong("dt")*1000));

        hashMap.put("CityName", cityName + ", " + country);
        hashMap.put("Updated", "Last update: " + updatedOn);
        hashMap.put("Id", id);
        hashMap.put("Sunrise", sunrise);
        hashMap.put("Sunset", sunset);
        hashMap.put("Description", description.toUpperCase());
        hashMap.put("Temperature", temperature + "°C");
        hashMap.put("Humidity", "Humidity: " + humidity + "%");
        hashMap.put("Pressure", "Pressure: " + pressure + "hPa");

        return hashMap;
    }

    @Override
    protected HashMap<String, String> doInBackground(String... params) {
        Log.d("HashMapTest", "Starterd doInBackground");
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;
        String format = "json";
        String units = "metric";
        int numDays = 16;
        String key = "aeefe119edb695e78e33dd7eb70ef6d2";



        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
            final String QUERY_PARAM = "q";
            final String QUERY_PARAM1 = "lat";
            final String QUERY_PARAM2 = "lon";
            final String FORMAT_PARAM = "mode";
            final String UNITS_PARAM = "units";
            final String DAYS_PARAM = "cnt";
            final String KEY_PARAM = "APPID";
            URL url = null;

            if(params.length == 2) {
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM1, params[0])
                        .appendQueryParameter(QUERY_PARAM2, params[1])
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        .appendQueryParameter(KEY_PARAM, key)
                        .build();

                Log.d(LOG_TAG, builtUri.toString());
                url = new URL(builtUri.toString());
            } else if (params.length == 1){
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        .appendQueryParameter(KEY_PARAM, key)
                        .build();
                Log.d(LOG_TAG, builtUri.toString());
                url = new URL(builtUri.toString());
            }
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            Log.d("HashMapTest", "Returning a HashMap");
            return getWeatherDataFromJson(forecastJsonStr, numDays);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        Log.d("HashMapTest", "Returning null");
        return null;
    }

    @Override
    protected void onPostExecute(HashMap<String, String> stringStringHashMap) {
        super.onPostExecute(stringStringHashMap);
        finishedListener.onTaskFinished(stringStringHashMap);
    }
}