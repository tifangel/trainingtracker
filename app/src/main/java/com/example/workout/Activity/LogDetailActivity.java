package com.example.workout.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.workout.Model.WorkoutRecord;
import com.example.workout.R;

public class LogDetailActivity extends AppCompatActivity {
    WorkoutRecord selected_workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);

        selected_workout = getIntent().getParcelableExtra("selected_workout");
    }
}