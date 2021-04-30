package com.example.workout.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.workout.Model.WorkoutRecord;

@Database(entities = {WorkoutRecord.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WorkoutRecordDao getDao();
    private static AppDatabase instance;

    public static AppDatabase getDatabase(final Context context){
        if(instance == null){
            synchronized(AppDatabase.class){
                instance = Room.databaseBuilder(context, AppDatabase.class, "DATABASE").build();
            }
        }
        return instance;
    }
}
