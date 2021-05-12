package com.example.workout.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.workout.Model.Schedule;

import java.util.List;

@Dao
public interface ScheduleDao {

    @Query("SELECT * FROM schedule")
    List<Schedule> getAllSchedule();

    @Insert
    void insertSchedule(Schedule schedule);
}
