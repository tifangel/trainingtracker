package com.example.workout.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workout.Database.AppDatabase;
import com.example.workout.Model.WorkoutRecord;
import com.example.workout.R;
import com.example.workout.Services.TrackingService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.Date;

public class TrainingTrackerActivity extends AppCompatActivity implements OnMapReadyCallback{
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int POLYLINE_COLOR = Color.RED;
    private static final float POLYLINE_WIDTH = 8f;
    private static final float MAP_ZOOM = 15f;

    private boolean walkingRunning;
    private boolean cycling;

    private Boolean isTracking = false;
    private ArrayList<ArrayList<LatLng>> pathPoints = new ArrayList<ArrayList<LatLng>>();

    private GoogleMap mMap;
    private double currentDistance = 0;
    private int currentStep = 0;

    private Button btnStartService;
    private ConstraintLayout startLayout;
    private ConstraintLayout clInnerLayout;
    private TextView dateView;
    private MapView mapView;
    private TextView textDistanceStep;

    public TrainingTrackerActivity() {
        // Required empty public constructor
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {

                switch (item.getItemId()){
                    case R.id.sports_news:
                        Intent intent_sport_news = new Intent(TrainingTrackerActivity.this, SportsNewsActivity.class);
                        startActivity(intent_sport_news);
                        overridePendingTransition(0, 0);
                        return true;

//                    case R.id.training_tracker:
//                        Intent intent_training_tracker = new Intent(TrainingTrackerActivity.this, TrainingTrackerActivity.class);
//                        startActivity(intent_training_tracker);
//                        return true;
////
                    case R.id.training_history:
                        Intent intent_training_history = new Intent(TrainingTrackerActivity.this, TrainingHistoryActivity.class);
                        startActivity(intent_training_history);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.training_scheduler:
                        Intent intent_training_schedule = new Intent(TrainingTrackerActivity.this, TrainingSchedulerActivity.class);
                        startActivity(intent_training_schedule);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_training_tracker);

        BottomNavigationView bottomNav = findViewById(R.id.navigationView);
        bottomNav.setSelectedItemId(R.id.training_tracker);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        btnStartService = findViewById(R.id.btn_start_stop);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cycling || walkingRunning){
                    toggleRun();
                }
            }
        });

        MaterialButtonToggleGroup btnTypeTrack = (MaterialButtonToggleGroup) findViewById(R.id.toggleButton);
        btnTypeTrack.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    if(checkedId == R.id.btnCycling){
                        cycling = true;
                        walkingRunning = false;
                    }else if(checkedId == R.id.btnWalkingRunning){
                        cycling = false;
                        walkingRunning = true;
                    }
                }
            }
        });

        startLayout = (ConstraintLayout) findViewById(R.id.startLayout);
        clInnerLayout = (ConstraintLayout) findViewById(R.id.clInnerLayout);
        dateView = (TextView) findViewById(R.id.dateView);
        textDistanceStep = findViewById(R.id.distanceStep);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        subscribeToObservers();

    }

    private void startService(String action){
        Intent serviceIntent = new Intent(this, TrackingService.class);
        if(isTracking){
            serviceIntent.putExtra("ACTION_STOP_SERVICE", action);
        }else {
            serviceIntent.putExtra("ACTION_START_SERVICE", action);
        }
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void toggleRun(){
        if(isTracking){
            startService("ACTION_STOP_SERVICE");
            startLayout.setVisibility(View.GONE);
            clInnerLayout.setVisibility(View.VISIBLE);
            mapView.setVisibility(View.VISIBLE);
            dateView.setVisibility(View.VISIBLE);
            saveWorkRecordToDb();
        }else {
            checkLocationPermission();
            startService("ACTION_START_SERVICE");
        }
    }

    private void saveWorkRecordToDb(){
        Date currdate = new Date();
        WorkoutRecord workoutRecord = null;
        if(cycling){
            workoutRecord = new WorkoutRecord("Cycling", currentDistance, -1, currdate.toString());
        }else{
            workoutRecord = new WorkoutRecord("Walking", (double) -1, currentStep, currdate.toString());
        }
        AppDatabase.getDatabase(getApplicationContext()).getDao().insertAllData(workoutRecord);
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
        TrackingService.currDistance.observe(this, new Observer<Double>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onChanged(Double aDouble) {
                currentDistance = aDouble;
                Log.d("CURRENT DISTANCE LOCATION", currentDistance + " m");
                currentStep = (int) ((int) currentDistance/1609.34*2000);
                Log.d("CURRENT STEP", currentStep + " step");
                if(walkingRunning){
                    textDistanceStep.setText(currentStep + " step");
                }
                if(cycling) {
                    textDistanceStep.setText(currentDistance + " m");
                }
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

    @SuppressLint("LongLogTag")
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
        }else {
            btnStartService.setText("Stop");
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