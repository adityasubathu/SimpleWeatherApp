package com.simple.weatherapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;

public class SetupFragment extends Fragment {

    View v;

    public static String owmKey, location, units;
    int unitsID = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_setup, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final EditText apiKey = v.findViewById(R.id.apiKeyField);
        final EditText loc = v.findViewById(R.id.locationField);
        final RadioGroup unitsGroup = v.findViewById(R.id.unitsRadioGroup);
        unitsID = unitsGroup.getCheckedRadioButtonId();
        final RadioButton selectedButton = v.findViewById(unitsID);

//        AdView mAdView;
//        MobileAds.initialize(getActivity(), "ca-app-pub-8581814417027345~4827575101");
//
//        mAdView = v.findViewById(R.id.adViewLauncher);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        Button b = v.findViewById(R.id.enterKeyButton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                owmKey = apiKey.getText().toString();
                location = loc.getText().toString();
                String CheckUnits = selectedButton.getText().toString();

                if (CheckUnits.equals("Retarded")) {

                    units = "imperial";

                } else if (CheckUnits.equals("Metric")) {

                    units = "metric";
                }

                if (owmKey.equals("") && location.equals("")) {

                    Toast.makeText(getActivity(), "Please enter the API Key and Location. The app won't work without them", Toast.LENGTH_SHORT).show();

                } else if (owmKey.equals("")) {

                    Toast.makeText(getActivity(), "Please enter the API Key", Toast.LENGTH_SHORT).show();

                } else if (location.equals("")) {

                    Toast.makeText(getActivity(), "Please enter the Location", Toast.LENGTH_SHORT).show();

                } else {

                    WeatherDisplayFragment weatherDisplayFragment = new WeatherDisplayFragment();

                    FragmentManager fm = getFragmentManager();
                    assert fm != null;
                    FragmentTransaction ft = fm.beginTransaction();

                    ft.replace(R.id.fragmentHolder, weatherDisplayFragment, "weatherDisplayFragment");
                    ft.addToBackStack("SetupFragment");
                    ft.commit();

                }

            }
        });
    }
}

