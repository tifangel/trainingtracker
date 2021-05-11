package com.example.workout.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.workout.Model.PathPointsConverter;
import com.example.workout.Model.WorkoutRecord;

@TypeConverters(PathPointsConverter.class)
@Database(entities = {WorkoutRecord.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WorkoutRecordDao getDao();
    private static AppDatabase instance;

    public static AppDatabase getDatabase(final Context context){
        if(instance == null){
            synchronized(AppDatabase.class){
                instance = Room.databaseBuilder(context, AppDatabase.class, "DATABASE").allowMainThreadQueries().build();
            }
        }
        return instance;
    }
}
