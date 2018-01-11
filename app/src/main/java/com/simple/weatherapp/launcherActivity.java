package com.simple.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
/*import com.google.firebase.analytics.FirebaseAnalytics;*/


/*
 * Created by Aditya on 07/01/2018.
 */

public class launcherActivity extends AppCompatActivity {

    public static String owmKey, location;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getSupportActionBar().hide();*/
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

        Intent intent = new Intent(launcherActivity.this, MainActivity.class);
        startActivity(intent);
    }

}

