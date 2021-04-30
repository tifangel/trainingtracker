package com.example.workout.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.workout.Model.Schedule;
import com.example.workout.Model.WorkoutRecord;
import com.example.workout.R;

import java.util.List;

public class SchedulerAdapter extends RecyclerView.Adapter<SchedulerAdapter.SchedulerViewHolder>{
    private List<Schedule> scheduleList;

    public SchedulerAdapter(List<Schedule> scheduleList){
        this.scheduleList = scheduleList;
    }

    @Override
    public SchedulerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_view, parent, false);
        return new SchedulerAdapter.SchedulerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SchedulerViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);
        String jenis = schedule.getJenis();
        Log.d("Jenis", jenis);
        String desc = "Tanggal: " + schedule.getTanggal() + "\nPukul: " + schedule.getJamAwal() + " - " + schedule.getJamAkhir();
        Log.d("Desc" , desc);
        holder.txtJenisWorkout.setText(jenis);
        holder.txtWorkoutDesc.setText(desc);
    }

    @Override
    public int getItemCount() {
        return (scheduleList != null) ? scheduleList.size() : 0;
    }

    public class SchedulerViewHolder extends RecyclerView.ViewHolder{
        private TextView txtWorkoutDesc, txtJenisWorkout;
        private ImageView imgWorkout;

        public SchedulerViewHolder(View itemView){
            super(itemView);
            txtJenisWorkout = (TextView) itemView.findViewById(R.id.tv_desc_workout);
            txtWorkoutDesc = (TextView) itemView.findViewById(R.id.jarakTempuh);
            imgWorkout = (ImageView) itemView.findViewById(R.id.iv_workout);

        }
    }
}
