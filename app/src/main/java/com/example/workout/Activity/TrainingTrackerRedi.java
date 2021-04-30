package com.example.workout.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workout.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class TrainingTrackerRedi extends AppCompatActivity {
    private Boolean alreadyStarted = false;

    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    double lng, lat;

    Button btnStartStop;

    TextView posisiTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_tracker);

        posisiTextView = findViewById(R.id.tv_posisi);

        btnStartStop = findViewById(R.id.btn_start_stop);
        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!alreadyStarted){
                    alreadyStarted = !alreadyStarted;
                    btnStartStop.setText("Stop");
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(TrainingTrackerRedi.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE_LOCATION_PERMISSION);
                    } else{
                        getCurrentLocation();
                    }
//                    String pos = "Posisi: "+ Double.toString(lng) + ", " + Double.toString(lat);
//                    posisiTextView.setText(pos);
//                    Log.d("Posisi", Double.toString(lng) + ", " + Double.toString(lat));

                } else{
                    alreadyStarted = !alreadyStarted;
                    btnStartStop.setText("Start");
//                    String pos = "Posisi: "+ Double.toString(lng) + ", " + Double.toString(lat);
//                    posisiTextView.setText(pos);
//                    Log.d("Posisi", Double.toString(lng) + ", " + Double.toString(lat));
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            getCurrentLocation();
        } else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(TrainingTrackerRedi.this)
                .requestLocationUpdates(locationRequest, new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(TrainingTrackerRedi.this)
                                .removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size() > 0){
                            int latestLocationIndex = locationResult.getLocations().size()-1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            String pos = "Posisi: "+ Double.toString(longitude) + ", " + Double.toString(latitude);
                            posisiTextView.setText(pos);
                        }
                    }
                }, Looper.getMainLooper());
    }
}