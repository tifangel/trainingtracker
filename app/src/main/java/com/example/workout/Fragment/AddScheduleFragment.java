package com.example.workout.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.workout.Activity.LogListActivity;
import com.example.workout.R;

public class AddScheduleFragment extends Fragment {
    CalendarView kalender;
    TextView date_view;

    public AddScheduleFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_add_schedule, container, false);

        kalender = (CalendarView) v.findViewById(R.id.kalender);
        date_view = (TextView) v.findViewById(R.id.date_view);
        // Add Listener in calendar
        kalender.setOnDateChangeListener(
                new CalendarView
                        .OnDateChangeListener() {
                    @Override

                    // In this Listener have one method
                    // and in this method we will
                    // get the value of DAYS, MONTH, YEARS
                    public void onSelectedDayChange(
                            @NonNull CalendarView view,
                            int year,
                            int month,
                            int dayOfMonth)
                    {

                        // Store the value of date with
                        // format in String type Variable
                        // Add 1 in month because month
                        // index is start with 0
                        String Date
                                = dayOfMonth + "-"
                                + (month + 1) + "-" + year;

                        // set this date in TextView for Display
                        date_view.setText(Date);

                        Intent i = new Intent(getContext(), LogListActivity.class);
                        i.putExtra("tanggalTerpilih", Date);
                        startActivity(i);
//                                Pindah Intent disini, tanggal ditaro diextra biar bisa dicari nantinya
                    }
                });

        return v;
    }


}
