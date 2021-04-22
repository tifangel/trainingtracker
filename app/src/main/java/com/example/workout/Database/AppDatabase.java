package com.example.workout.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.workout.Model.WorkoutRecord;

@Database(entities = {WorkoutRecord.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WorkoutRecordDao workoutRecordDao();
}
