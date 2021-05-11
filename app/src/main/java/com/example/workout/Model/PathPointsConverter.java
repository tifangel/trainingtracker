package com.example.workout.Model;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PathPointsConverter {
    @TypeConverter
    public static ArrayList<ArrayList<LatLng>> toPathPoints(String path){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ArrayList<LatLng>>>() {}.getType();
        return gson.fromJson(path,type);
    }
    @TypeConverter
    public static String fromPathPoints(ArrayList<ArrayList<LatLng>> pathPoints){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ArrayList<LatLng>>>() {}.getType();
        return gson.toJson(pathPoints,type);
    }
}
