package com.example.workout.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.workout.Model.WorkoutRecord;

import java.util.List;

@Dao
public interface WorkoutRecordDao {
    @Query("SELECT * FROM WorkoutRecord")
    List<WorkoutRecord> getAll();

    @Query("SELECT * FROM WorkoutRecord WHERE wid IN (:workoutIds)")
    List<WorkoutRecord> loadAllByIds(int[] workoutIds);

//    @Query("SELECT * FROM WorkoutRecord WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
//    WorkoutRecord findByName(String first, String last);

    @Query("SELECT * FROM WorkoutRecord WHERE tanggal = (:tanggal)")
    List<WorkoutRecord> findByDate(String tanggal);

    @Insert
    void insertAll(WorkoutRecord... workoutRecords);

    @Delete
    void delete(WorkoutRecord user);
}
