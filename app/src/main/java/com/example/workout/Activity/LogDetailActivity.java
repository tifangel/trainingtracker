package com.example.workout.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workout.Model.FetchURL;
import com.example.workout.Model.TaskLoadedCallback;
import com.example.workout.Model.WorkoutRecord;
import com.example.workout.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LogDetailActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    WorkoutRecord selected_workout;
    TextView tanggal, jarak_langkah;
    Button getRoute;

    private final String API_MAPS_KEY = "AIzaSyB_tTKXQxwwYxPK_gyEweFF5DYOM7bCCos";

    private GoogleMap mMap;

    MarkerOptions point1, point2;
    Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);

        BottomNavigationView bottomNav = findViewById(R.id.navigationView);
        bottomNav.setSelectedItemId(R.id.training_history);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        selected_workout = getIntent().getParcelableExtra("selected_workout");

        getRoute = findViewById(R.id.btn_getRoute);
        getRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchURL(LogDetailActivity.this).execute(getUrl(point1.getPosition(), point2.getPosition(), "driving"), "driving");
//                getRoute.setVisibility(View.GONE);
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.supportMapFragment);
        mapFragment.getMapAsync(this);

        point1 = new MarkerOptions().position(new LatLng(27.65, 85.3)).title("Location 1");
        point2 = new MarkerOptions().position(new LatLng(27.65, 100.3)).title("Location 2");

        String url = getUrl(point1.getPosition(), point2.getPosition(), "driving");

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

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(point1);
        mMap.addMarker(point2);
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
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
}