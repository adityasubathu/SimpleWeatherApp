package com.simple.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class SetupFragment extends Fragment {

    View v;

    public static String owmKey, location, units = "metric";
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

        SharedPreferences mySharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("saveKey", Context.MODE_PRIVATE);

        if (mySharedPreferences.contains("owmKey")) {

            Toast.makeText(getContext(), "Key Found", Toast.LENGTH_SHORT).show();
            String storedOwmKey = mySharedPreferences.getString("owmKey", null);
            apiKey.setText(storedOwmKey);

        }

        unitsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("InflateParams")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton selectedButton = v.findViewById(checkedId);
                String CheckUnits = selectedButton.getText().toString();

                if (CheckUnits.equals("Retarded")) {

                    units = "imperial";

                } else if (CheckUnits.equals("Metric")) {

                    units = "metric";

                }

            }
        });

        Button b = v.findViewById(R.id.enterKeyButton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                owmKey = apiKey.getText().toString();
                location = loc.getText().toString();

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

                    ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.slide_out_right, R.anim.slide_in_right, android.R.anim.fade_out);

                    ft.replace(R.id.fragmentHolder, weatherDisplayFragment, "weatherDisplayFragment");
                    ft.addToBackStack("SetupFragment");
                    ft.commit();

                    SharedPreferences mySharedPrefs = Objects.requireNonNull(getActivity()).getSharedPreferences("saveKey", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = mySharedPrefs.edit();

                    editor.putString("owmKey", SetupFragment.owmKey);
                    editor.apply();

                }

            }
        });
    }
}

