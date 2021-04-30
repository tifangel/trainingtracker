package com.example.workout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.workout.Adapter.HistoryAdapter;
import com.example.workout.Adapter.SchedulerAdapter;
import com.example.workout.Model.Schedule;
import com.example.workout.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TrainingSchedulerActivity extends AppCompatActivity {
    private List<Schedule> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_scheduler);

        add_data();

        BottomNavigationView bottomNav = findViewById(R.id.navigationView);
        bottomNav.setSelectedItemId(R.id.training_scheduler);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        FloatingActionButton addSchedule = findViewById(R.id.add_activity);

        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingSchedulerActivity.this, AddScheduleActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView rView = findViewById(R.id.recyclerSchedule);
        SchedulerAdapter adapter = new SchedulerAdapter(scheduleList);




        navListener.onNavigationItemSelected(bottomNav.getMenu().findItem(R.id.training_scheduler));
    }

    private void add_data() {
        scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule("Cycling", "30/03/2000", "17.00", "20.00"));
        scheduleList.add(new Schedule("Running", "31/03/2000", "18.00", "20.00"));
        scheduleList.add(new Schedule("Cycling", "20/03/2000", "19.00", "20.00"));
        scheduleList.add(new Schedule("Walking", "10/03/2000", "16.00", "20.00"));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {

                switch (item.getItemId()){
                    case R.id.sports_news:
                        Intent intent_sport_news = new Intent(TrainingSchedulerActivity.this, SportsNewsActivity.class);
                        startActivity(intent_sport_news);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.training_tracker:
                        Intent intent_training_tracker = new Intent(TrainingSchedulerActivity.this, TrainingTrackerActivity.class);
                        startActivity(intent_training_tracker);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.training_history:
                        Intent intent_training_history = new Intent(TrainingSchedulerActivity.this, TrainingHistoryActivity.class);
                        startActivity(intent_training_history);
                        overridePendingTransition(0, 0);
                        return true;

//                    case R.id.training_scheduler:
//                        Intent intent_training_schedule = new Intent(TrainingSchedulerActivity.this, TrainingSchedulerActivity.class);
//                        startActivity(intent_training_schedule);
//                        return true;
                }
                return false;
            };
}