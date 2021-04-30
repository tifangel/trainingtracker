package com.example.workout.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workout.Activity.SportsNewsActivity;
import com.example.workout.Activity.SportsNewsWebViewActivity;
import com.example.workout.Adapter.HistoryAdapter;
import com.example.workout.Adapter.SportsNewsAdapter;
import com.example.workout.Model.News;
import com.example.workout.Model.RecyclerItemClickListener;
import com.example.workout.Model.WorkoutRecord;
import com.example.workout.R;

import java.util.ArrayList;
import java.util.List;

public class LogListFragment extends Fragment {
    RecyclerView rView;
    List<WorkoutRecord> historyList;
    HistoryAdapter adapter;

    public LogListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fetch_history_workout();

        try {
            for (int i = 0; i < historyList.size(); i++){
                Log.d("Isi historyList", historyList.get(i).getJenis());
            }
            } catch (Exception e) {
            Log.d("Error historyList: ", e.getLocalizedMessage());
//            e.printStackTrace();
        }

        View v = inflater.inflate(R.layout.fragment_log_list, container, false);
        rView = v.findViewById(R.id.recyclerHistory);
        adapter = new HistoryAdapter(historyList);
        Log.d("Jumlah isi list", String.valueOf(historyList.size()));
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SportsNewsActivity.this, 3);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);
//        rView.addOnItemTouchListener(
//                new RecyclerItemClickListener(getContext(), rView ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                         selected_news = historyList.get(position);
//                        Intent i = new Intent(SportsNewsActivity.this, SportsNewsWebViewActivity.class);
//                        i.putExtra("selected_news", selected_news);
//                        startActivity(i);
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );
        return v;
    }

    private void fetch_history_workout() {
        historyList = new ArrayList<>();
        historyList.add(new WorkoutRecord("Bersepeda", 50.0, 3000, "27/03/2000"));
        historyList.add(new WorkoutRecord("running", 50.0, 3000, "27/03/2000"));
        historyList.add(new WorkoutRecord("cycling", 50.0, 3000, "27/03/2000"));
        historyList.add(new WorkoutRecord("Berpacaran", 50.0, 3000, "27/03/2000"));
        historyList.add(new WorkoutRecord("Bercumbu Rayu", 50.0, 3000, "27/03/2000"));
    }
}