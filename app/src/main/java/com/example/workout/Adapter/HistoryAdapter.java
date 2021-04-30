package com.example.workout.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        WorkoutRecord workout = workoutRecordList.get(position);
        String jenis = workout.getJenis();
        Log.d("Jenis", jenis);
        String desc = "Tanggal: " + workout.getTanggal() + "\n Jarak Tempuh: "+ workout.getJarakTempuh()+ "km";
        Log.d("Desc" , desc);
        holder.txtJenisWorkout.setText(jenis);
        holder.txtWorkoutDesc.setText(desc);
//        holder.txtNewsDesc.setText(desc);
    }

    @Override
    public int getItemCount() {
        return (workoutRecordList != null) ? workoutRecordList.size() : 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView txtWorkoutDesc, txtJenisWorkout;
        private ImageView imgWorkout;

        public HistoryViewHolder(View itemView){
            super(itemView);
            txtJenisWorkout = (TextView) itemView.findViewById(R.id.tv_desc_workout);
            txtWorkoutDesc = (TextView) itemView.findViewById(R.id.jarakTempuh);
            imgWorkout = (ImageView) itemView.findViewById(R.id.iv_workout);
        }
    }
}
