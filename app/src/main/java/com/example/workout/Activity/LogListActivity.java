package com.example.workout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.workout.Adapter.HistoryAdapter;
import com.example.workout.Model.News;
import com.example.workout.Model.RecyclerItemClickListener;
import com.example.workout.Model.WorkoutRecord;
import com.example.workout.R;

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

        date = getIntent().getStringExtra("tanggalTerpilih");

        tanggal = findViewById(R.id.tv_tanggalTerpilih);
        tanggal.setText(date);

        fetch_history_workout();

        rView = findViewById(R.id.recyclerHistory);
        adapter = new HistoryAdapter(historyList);
        Log.d("Jumlah isi list", String.valueOf(historyList.size()));
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SportsNewsActivity.this, 3);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LogListActivity.this);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
        rView.addOnItemTouchListener(
                new RecyclerItemClickListener(LogListActivity.this, rView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        WorkoutRecord selected_workout = historyList.get(position);
                        Intent i = new Intent(LogListActivity.this, LogDetailActivity.class);
                        i.putExtra("selected_workout", selected_workout);
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private void fetch_history_workout() {
        historyList = new ArrayList<>();
        historyList.add(new WorkoutRecord(1, "Bersepeda", 50.0, 3000, "27/03/2000"));
        historyList.add(new WorkoutRecord(2, "running", 50.0, 3000, "27/03/2000"));
        historyList.add(new WorkoutRecord(3, "cycling", 50.0, 3000, "27/03/2000"));
        historyList.add(new WorkoutRecord(4, "Berpacaran", 50.0, 3000, "27/03/2000"));
        historyList.add(new WorkoutRecord(5, "Bercumbu Rayu", 50.0, 3000, "27/03/2000"));
    }
}