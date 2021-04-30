package com.example.workout.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.workout.Model.Schedule;
import com.example.workout.R;

import java.util.List;

//public class SchedulerAdapter extends RecyclerView.Adapter<SchedulerAdapter.SchedulerViewHolder>{
//    private List<Schedule> scheduleList;
//
//    public SchedulerAdapter(List<Schedule> scheduleList){
//        this.scheduleList = scheduleList;
//    }
//
//    @Override
//    public SchedulerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.activity_training_scheduler, parent, false);
//        return new SchedulerAdapter.ScheduleViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(SchedulerViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return (workoutRecordList != null) ? workoutRecordList.size() : 0;
//    }
//
//    public class SchedulerViewHolder extends RecyclerView.ViewHolder{
//
//        public SchedulerViewHolder(View itemView){
//            super(itemView);
//
//        }
//    }
//}
