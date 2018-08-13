package com.adityasubathu.weatherapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static com.adityasubathu.weatherapp.weatherDownloader.getWeatherJSON;
import static com.adityasubathu.weatherapp.weatherDownloader.setWeatherIcon;

public class backgroundProcess extends AsyncTask<String, Void, JSONObject> {

    private ASyncResponse delegate; //Call back interface

    backgroundProcess(ASyncResponse asyncResponse) {
        delegate = asyncResponse; //Assigning call back interface through constructor
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        try {
            return getWeatherJSON(SetupFragment.location);
        } catch (Exception e) {
            Log.d("Error", "Cannot process JSON results", e);
            return null;
        }

    }

    @Override
    protected void onPostExecute(JSONObject json) {
        try {
            if (json != null) {
                JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                JSONObject main = json.getJSONObject("main");
                DateFormat df = DateFormat.getDateTimeInstance();

                String city = json.getString("name").toUpperCase(Locale.getDefault()) + ", " + json.getJSONObject("sys").getString("country");

                String description = details.getString("description").toUpperCase(Locale.getDefault());
                String temperature = String.valueOf(main.getDouble("temp")) + "°";
                String humidity = main.getString("humidity") + "%";
                String pressure = main.getString("pressure") + " hPa";
                String updatedOn = df.format(new Date(json.getLong("dt") * 1000));
                String iconText = setWeatherIcon(details.getInt("id"), json.getJSONObject("sys").getLong("sunrise") * 1000, json.getJSONObject("sys").getLong("sunset") * 1000);

                delegate.processFinish(city, description, temperature, humidity, pressure, updatedOn, iconText, "" + (json.getJSONObject("sys").getLong("sunrise") * 1000));

            }
        } catch (JSONException e) {
            Log.e("A", "Cannot process JSON results");
        }
    }
}