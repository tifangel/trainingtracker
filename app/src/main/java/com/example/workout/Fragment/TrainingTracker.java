package com.example.workout.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workout.R;
import com.example.workout.Services.TrackingService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class TrainingTracker extends AppCompatActivity implements OnMapReadyCallback{
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int POLYLINE_COLOR = Color.RED;
    private static final float POLYLINE_WIDTH = 8f;
    private static final float MAP_ZOOM = 15f;
//    GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
//    Marker mCurrLocationMarker;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private MapView mapView;

    private boolean walkingRunning;
    private boolean cycling;

    private Boolean isTracking = false;
    private ArrayList<ArrayList<LatLng>> pathPoints = new ArrayList<ArrayList<LatLng>>();

    Button btnStartService;
    Button btnStopService;

    public TrainingTracker() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_training_tracker);

        checkLocationPermission();

        btnStartService = findViewById(R.id.btnToggleRun);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startService("ACTION_START_OR_RESUME_SERVICE");
                toggleRun();
            }
        });

        Button btnCycling = findViewById(R.id.btnCyclingChoose);
        Button btnWalkingRunning = findViewById(R.id.btnWalkingRunningChoose);
        btnStopService = findViewById(R.id.btnFinishRun);
        TextView textDistanceStep = findViewById(R.id.distanceStep);
        btnCycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cycling = true;
                walkingRunning = false;
                btnCycling.setVisibility(View.GONE);
                btnWalkingRunning.setVisibility(View.GONE);
                btnStartService.setVisibility(View.VISIBLE);
                textDistanceStep.setVisibility(View.VISIBLE);
            }
        });
        btnWalkingRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cycling = false;
                walkingRunning = true;
                btnCycling.setVisibility(View.GONE);
                btnWalkingRunning.setVisibility(View.GONE);
                btnStartService.setVisibility(View.VISIBLE);
                textDistanceStep.setVisibility(View.VISIBLE);
            }
        });

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        subscribeToObservers();

    }

    private void startService(String action){
        Intent serviceIntent = new Intent(this, TrackingService.class);
        serviceIntent.putExtra("ACTION_START_OR_RESUME_SERVICE", action);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void toggleRun(){
        if(isTracking){
            startService("ACTION_PAUSE_SERVICE");
        }else {
            startService("ACTION_START_OR_RESUME_SERVICE");
        }
    }

    private void subscribeToObservers(){
        TrackingService.isTracking.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                updateTracking(aBoolean);
            }
        });
        TrackingService.pathPoints.observe(this, new Observer<ArrayList<ArrayList<LatLng>>>() {
            @Override
            public void onChanged(ArrayList<ArrayList<LatLng>> arrayLists) {
                pathPoints = arrayLists;
                addLatestPolyline();
                moveCameraToUser();
            }
        });
    }

    private void addAllPolylines(){
        for(ArrayList<LatLng> polyline : pathPoints){
            PolylineOptions polylineOptions = new PolylineOptions()
                    .color(POLYLINE_COLOR)
                    .width(POLYLINE_WIDTH)
                    .addAll(polyline);
            if(mMap != null){
                mMap.addPolyline(polylineOptions);
            }
        }
    }

    private void addLatestPolyline(){
        ArrayList<LatLng> lastPolylines = pathPoints.get(pathPoints.size()-1);
        if(!pathPoints.isEmpty() && lastPolylines.size() > 1){
            LatLng preLastLatLng = lastPolylines.get(lastPolylines.size() - 2);
            LatLng lastLatLng = lastPolylines.get(lastPolylines.size()-1);
            PolylineOptions polylineOptions = new PolylineOptions()
                    .color(POLYLINE_COLOR)
                    .width(POLYLINE_WIDTH)
                    .add(preLastLatLng)
                    .add(lastLatLng);
            if(mMap != null){
                mMap.addPolyline(polylineOptions);
            }
        }
    }

    private void moveCameraToUser(){
        ArrayList<LatLng> lastPolylines = pathPoints.get(pathPoints.size()-1);
        if(!pathPoints.isEmpty() && !lastPolylines.isEmpty()){
            if(mMap != null){
                LatLng lastLatLng = lastPolylines.get(lastPolylines.size()-1);
               mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                lastLatLng,
                                MAP_ZOOM
                        )
                );
            }
        }
    }

    private void updateTracking(Boolean isTracking){
        this.isTracking = isTracking;
        if(!isTracking){
            btnStartService.setText("Start");
            btnStopService.setVisibility(View.VISIBLE);
        }else {
            btnStartService.setText("Stop");
            btnStopService.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addAllPolylines();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
//                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient();
//                        }
//                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                    checkLocationPermission();
                }
                return;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mapView != null){
            mapView.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mapView != null){
            mapView.onResume();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mapView != null){
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mapView != null){
            mapView.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mapView != null){
            mapView.onStop();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mapView != null){
            mapView.onLowMemory();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView != null){
            mapView.onDestroy();
        }
    }
}