package com.example.workout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.workout.Adapter.HistoryAdapter;
import com.example.workout.Database.AppDatabase;
import com.example.workout.Model.News;
import com.example.workout.Model.RecyclerItemClickListener;
import com.example.workout.Model.WorkoutRecord;
import com.example.workout.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LogListActivity extends AppCompatActivity {
    RecyclerView rView;
    List<WorkoutRecord> historyList;
    HistoryAdapter adapter;
    String date;
    TextView tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);

        BottomNavigationView bottomNav = findViewById(R.id.navigationView);
        bottomNav.setSelectedItemId(R.id.training_history);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        date = getIntent().getStringExtra("tanggalTerpilih");

        tanggal = findViewById(R.id.tv_tanggalTerpilih);
        tanggal.setText(date);

        fetch_history_workout(date);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "workout-record").build();

        rView = findViewById(R.id.recyclerHistory);
        adapter = new HistoryAdapter(historyList);
        Log.d("Jumlah isi list", String.valueOf(historyList.size()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LogListActivity.this);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        rView.addOnItemTouchListener(
                new RecyclerItemClickListener(LogListActivity.this, rView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        WorkoutRecord selected_workout = historyList.get(position);

                        Gson gson = new Gson();
                        String workout = gson.toJson(selected_workout);

                        Intent i = new Intent(LogListActivity.this, LogDetailActivity.class);
                        i.putExtra("selected_workout", workout);
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    @SuppressLint("LongLogTag")
    private void fetch_history_workout(String tanggal) {
        historyList = new ArrayList<>();
        historyList = AppDatabase.getDatabase(getApplicationContext()).getDao().findByDate(tanggal);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {

                switch (item.getItemId()){
                    case R.id.sports_news:
                        Intent intent_sport_news = new Intent(LogListActivity.this, SportsNewsActivity.class);
                        startActivity(intent_sport_news);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.training_tracker:
                        Intent intent_training_tracker = new Intent(LogListActivity.this, TrainingTrackerActivity.class);
                        startActivity(intent_training_tracker);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.training_history:
                        Intent intent_training_history = new Intent(LogListActivity.this, TrainingHistoryActivity.class);
                        startActivity(intent_training_history);
                        return true;

                    case R.id.training_scheduler:
                        Intent intent_training_schedule = new Intent(LogListActivity.this, TrainingSchedulerActivity.class);
                        startActivity(intent_training_schedule);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            };
}