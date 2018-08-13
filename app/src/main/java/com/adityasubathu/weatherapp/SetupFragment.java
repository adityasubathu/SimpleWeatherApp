package com.adityasubathu.weatherapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class SetupFragment extends Fragment {

    public static String owmKey, location, units = "metric";
    View v;
    EditText loc = null;
    int unitsID = 0;
    boolean PERMISSION_DENIED = false;
    String UNITS = "";

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
        loc = v.findViewById(R.id.locationField);
        final RadioGroup unitsGroup = v.findViewById(R.id.unitsRadioGroup);
        unitsID = unitsGroup.getCheckedRadioButtonId();

        SharedPreferences mySharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("saveKey", Context.MODE_PRIVATE);

        if (mySharedPreferences.contains("owmKey")) {

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

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

            setLocation();

        }


        Button b = v.findViewById(R.id.enterKeyButton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                owmKey = apiKey.getText().toString();
                if (PERMISSION_DENIED) {

                    location = loc.getText().toString();

                }

                if (owmKey.equals("")) {

                    Toast.makeText(getActivity(), "Please enter the API Key", Toast.LENGTH_SHORT).show();


                } else if (location.isEmpty()) {

                    Toast.makeText(getActivity(), "Location Undetectable", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setLocation();
                    Log.d(TAG, "permission granted");
                } else {

                    loc.setVisibility(View.VISIBLE);
                    PERMISSION_DENIED = true;

                    Toast.makeText(getActivity(), "Location Permission Denied. Please Enter a manual location.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "permission denied");
                }
            }

        }
    }

    public void setLocation() {

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location loc) {
                // Got last known location. In some rare situations this can be null.
                if (loc != null) {
                    List<Address> addressList = null;

                    Geocoder geocoder = new Geocoder(getActivity());

                    try {
                        double latitude = loc.getLatitude();
                        double longitude = loc.getLongitude();

                        addressList = geocoder.getFromLocation(latitude, longitude, 1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Address address = Objects.requireNonNull(addressList).get(0);

                    location = address.getLocality();

                    if (location.isEmpty()) {
                        Toast.makeText(getActivity(), "Location Undetectable", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}

