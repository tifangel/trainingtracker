package com.example.workout.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.workout.Model.News;
import com.example.workout.Model.WorkoutRecord;
import com.example.workout.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    private List<WorkoutRecord> workoutRecordList;

    public HistoryAdapter(List<WorkoutRecord> workoutRecordList){
        this.workoutRecordList = workoutRecordList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_view, parent, false);
        return new HistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
//        String title = newsList.get(position).getTitle();
//        String author = "Author: " + newsList.get(position).getAuthor();
//        String desc = newsList.get(position).getDescription();
//        holder.txtNewsTitle.setText(title);
//        holder.txtNewsAuthor.setText(author);
//        holder.txtNewsDesc.setText(desc);
    }

    @Override
    public int getItemCount() {
        return (workoutRecordList != null) ? workoutRecordList.size() : 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        public HistoryViewHolder(View itemView){
            super(itemView);

        }
    }
}
