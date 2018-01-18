package com.simple.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
/*import com.google.firebase.analytics.FirebaseAnalytics;*/

/*
 * Created by Aditya on 07/01/2018.
 */

public class launcherActivity extends AppCompatActivity {

    public static String owmKey, location, units;
    int unitsID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        AdView mAdView;
        MobileAds.initialize(this, "ca-app-pub-8581814417027345~4827575101");

        mAdView = findViewById(R.id.adViewLauncher);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void setApiLocation(View view) {
        EditText apiKey = findViewById(R.id.apiKeyField);
        owmKey = apiKey.getText().toString();

        EditText loc = findViewById(R.id.locationField);
        location = loc.getText().toString();

        RadioGroup unitsGroup = findViewById(R.id.unitsRadioGroup);

        unitsID = unitsGroup.getCheckedRadioButtonId();
        RadioButton selectedButton = findViewById(unitsID);


        String CheckUnits = selectedButton.getText().toString();

        if (CheckUnits.equals("Retarded")) {
            units = "imperial";
        } else if (CheckUnits.equals("Metric")) {
            units = "metric";
        }

        if (owmKey.equals("") && location.equals("")) {
            Toast.makeText(this, "Please enter the API Key and Location. The app won't work without them.", Toast.LENGTH_SHORT).show();
        } else if (owmKey.equals("")) {
            Toast.makeText(this, "Please enter the API Key.", Toast.LENGTH_SHORT).show();
        } else if (location.equals("")) {
            Toast.makeText(this, "Please enter the Location", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(launcherActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

}

