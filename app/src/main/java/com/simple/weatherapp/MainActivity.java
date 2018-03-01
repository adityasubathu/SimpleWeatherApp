package com.simple.weatherapp;

import android.support.v4.app.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        SetupFragment setupFragment = new SetupFragment();

        ft.add(R.id.fragmentHolder, setupFragment, "SetupFragment");
        ft.commit();

    }
}
