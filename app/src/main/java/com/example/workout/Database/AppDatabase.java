package com.example.workout.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.workout.Model.PathPointsConverter;
import com.example.workout.Model.Schedule;
import com.example.workout.Model.WorkoutRecord;

@Database(entities = {WorkoutRecord.class, Schedule.class}, version = 1)
@TypeConverters({PathPointsConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract WorkoutRecordDao getDao();
    public abstract ScheduleDao getScheduleDao();
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
