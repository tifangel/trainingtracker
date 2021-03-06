package com.example.workout.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workout.Model.WorkoutRecord;
import com.example.workout.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LogDetailActivity extends AppCompatActivity implements OnMapReadyCallback{
    private static final int POLYLINE_COLOR = Color.RED;
    private static final float POLYLINE_WIDTH = 8f;
    private static final float MAP_ZOOM = 15f;

    WorkoutRecord selected_workout;
    ArrayList<ArrayList<LatLng>> pathPoints = new ArrayList<ArrayList<LatLng>>();
    TextView tanggal, jarak_langkah;

    private GoogleMap mMap;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);

        BottomNavigationView bottomNav = findViewById(R.id.navigationView);
        bottomNav.setSelectedItemId(R.id.training_history);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Gson gson = new Gson();
        selected_workout = gson.fromJson(getIntent().getStringExtra("selected_workout"), WorkoutRecord.class);
        pathPoints = selected_workout.getPathPoints();

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        String txt_tanggal = selected_workout.getTanggal();
        tanggal = findViewById(R.id.tv_tanggalTerpilih);
        tanggal.setText(txt_tanggal);

        if(selected_workout.getJenis().equals("Cycling")){
            String txt_jarak_langkah = Double.toString(selected_workout.getJarakTempuh()) + "km";
            jarak_langkah = findViewById(R.id.tv_jarak_langkah);
            jarak_langkah.setText(txt_jarak_langkah);
        } else{
            String txt_jarak_langkah = Integer.toString(selected_workout.getJumlahStep()) + " Step";
            jarak_langkah = findViewById(R.id.tv_jarak_langkah);
            jarak_langkah.setText(txt_jarak_langkah);
        }
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(pathPoints != null) {
            moveCameraToUser();
            addAllPolylines();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {

                switch (item.getItemId()){
                    case R.id.sports_news:
                        Intent intent_sport_news = new Intent(LogDetailActivity.this, SportsNewsActivity.class);
                        startActivity(intent_sport_news);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.training_tracker:
                        Intent intent_training_tracker = new Intent(LogDetailActivity.this, TrainingTrackerActivity.class);
                        startActivity(intent_training_tracker);
                        overridePendingTransition(0, 0);
                        return true;
//
                    case R.id.training_history:
                        Intent intent_training_history = new Intent(LogDetailActivity.this, TrainingHistoryActivity.class);
                        startActivity(intent_training_history);
                        return true;

                    case R.id.training_scheduler:
                        Intent intent_training_schedule = new Intent(LogDetailActivity.this, TrainingSchedulerActivity.class);
                        startActivity(intent_training_schedule);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            };

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