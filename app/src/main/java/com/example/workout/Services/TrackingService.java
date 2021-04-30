package com.example.workout.Services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.LifecycleService;

import com.example.workout.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class TrackingService extends LifecycleService {

    private boolean isFirstRun = true;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    public static MutableLiveData<Boolean> isTracking = new MutableLiveData<Boolean>();
    public static MutableLiveData<ArrayList<ArrayList<LatLng>>> pathPoints = new MutableLiveData<ArrayList<ArrayList<LatLng>>>();


    private void postInitialValues(){
        isTracking.postValue(false);
        pathPoints.postValue(new ArrayList<ArrayList<LatLng>>());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        postInitialValues();
        fusedLocationProviderClient = new FusedLocationProviderClient(this);

        isTracking.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@NonNull Boolean aBoolean) {
                updateLocationTracking(aBoolean);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void updateLocationTracking(Boolean isTracking){
        if(isTracking){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                mLocationRequest = new LocationRequest()
                        .setInterval(UPDATE_INTERVAL)
                        .setFastestInterval(FASTEST_INTERVAL)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                fusedLocationProviderClient.requestLocationUpdates(
                        mLocationRequest,
                        locationCallBack,
                        Looper.getMainLooper()
                );
            }
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
        }
    }

    private LocationCallback locationCallBack = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if(isTracking.getValue()){
                if(locationResult != null){
                    if(locationResult.getLocations() != null){
                        for(Location location : locationResult.getLocations()){
                            addPathPoint(location);
                            Log.e("NEW_LOCATION", location.getLatitude() + " , " + location.getLongitude());
                        }
                    }
                }
            }
        }
    };

    private void addPathPoint(Location location){
        if(location != null){
            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
            ArrayList<ArrayList<LatLng>> polylines = pathPoints.getValue();
            if(polylines != null){
                polylines.get(polylines.size()-1).add(pos);
                pathPoints.postValue(polylines);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getStringExtra("ACTION_START_OR_RESUME_SERVICE") != null) {
            String input = intent.getStringExtra("ACTION_START_OR_RESUME_SERVICE");

            if (isFirstRun) {
                startForegroundService(input);
                isFirstRun = false;
            } else {
//                startForegroundService(input);
                addEmptyPolyline();
            }
        } else if (intent.getStringExtra("ACTION_PAUSE_SERVICE") != null) {
            pauseService();
        } else if (intent.getStringExtra("ACTION_STOP_SERVICE") != null) {
            Log.d("STOPPED : ", "Stopped services");
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void addEmptyPolyline(){
        ArrayList<ArrayList<LatLng>> polylines = pathPoints.getValue();
        if(polylines != null){
            polylines.add(new ArrayList<LatLng>());
            pathPoints.postValue(polylines);
        }else {
            polylines = new ArrayList<ArrayList<LatLng>>();
            polylines.add(new ArrayList<LatLng>());
            pathPoints.postValue(polylines);
        }
    }

    private void startForegroundService(String input){

        addEmptyPolyline();
        isTracking.postValue(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager);
        }

        Notification notification = new NotificationCompat.Builder(this, "ChannelId1")
                .setContentTitle("Training Tracker")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_action_running)
                .setContentIntent(getMainActivityPendingIntent())
                .build();

        startForeground(1, notification);
    }

    private void pauseService(){
        isTracking.postValue(false);
    }

    private PendingIntent getMainActivityPendingIntent(){
        Intent notificationIntent = new Intent(this, TrackingService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        return pendingIntent;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager notificationManager) {
        NotificationChannel notificationChannel = new NotificationChannel(
                    "ChannelId1", "foreground notification", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }
}
