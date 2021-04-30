package com.example.workout.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.workout.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TrainingSchedulerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_scheduler);



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



        navListener.onNavigationItemSelected(bottomNav.getMenu().findItem(R.id.training_scheduler));
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