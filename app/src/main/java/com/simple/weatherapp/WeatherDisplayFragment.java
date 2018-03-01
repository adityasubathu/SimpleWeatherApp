package com.simple.weatherapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;

import java.util.Locale;
import java.util.Objects;

public class WeatherDisplayFragment extends Fragment {

    View v;
    Typeface weatherFont;

    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField, unitsField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_weatherdisplay, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        AdView mAdView;


        weatherFont = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), "fonts/webfont.ttf");

        cityField = v.findViewById(R.id.city_field);
        updatedField = v.findViewById(R.id.updated_field);
        detailsField = v.findViewById(R.id.details_field);
        currentTemperatureField = v.findViewById(R.id.current_temperature_field);
        humidity_field = v.findViewById(R.id.humidity_field);
        pressure_field = v.findViewById(R.id.pressure_field);
        weatherIcon = v.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        unitsField = v.findViewById(R.id.units_field);

        Function.placeIdTask asyncTask = new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                humidity_field.setText(String.format(Locale.ENGLISH, "%s %s", getString(R.string.humidity), weather_humidity));
                pressure_field.setText(String.format(Locale.ENGLISH, "%s %s", getString(R.string.pressure), weather_pressure));
                weatherIcon.setText(Html.fromHtml(weather_iconText));
                unitsField.setText(SetupFragment.units);

            }
        });
        asyncTask.execute(SetupFragment.location);

//        MobileAds.initialize(getActivity(), "ca-app-pub-8581814417027345~4827575101");
//
//        mAdView = v.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
    }
}

