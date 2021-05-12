package com.example.workout.Activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.text.format.DateFormat;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workout.Database.AppDatabase;
import com.example.workout.Model.Schedule;
import com.example.workout.R;
import com.example.workout.Services.ReminderBroadcast;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddScheduleActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView dateResult;
    private Button datePicker;
    private TextView startTimeText;
    private Button startTime;
    private TimePickerDialog startTimePickerDialog;
    private TextView finishTimeText;
    private Button finishTime;
    private TimePickerDialog finishTimePickerDialog;
    private String jenis;
    private Calendar scheduleCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        createNotificationChannel();
        scheduleCalender = Calendar.getInstance();

        MaterialButtonToggleGroup btnTypeTrack = (MaterialButtonToggleGroup) findViewById(R.id.toggleButton);
        btnTypeTrack.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    if(checkedId == R.id.btnCycling){
                        jenis = "Cycling";
                    }else if(checkedId == R.id.btnWalkingRunning){
                        jenis = "Walking/Running";
                    }
                }
            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT);

        dateResult = (TextView) findViewById(R.id.dateresult);
        datePicker = (Button) findViewById(R.id.datepicker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        startTimeText = (TextView) findViewById(R.id.start_time_text);
        startTime = (Button) findViewById(R.id.start_time);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartTimeDialog();
            }
        });
        finishTimeText = (TextView) findViewById(R.id.finish_time_text);
        finishTime = (Button) findViewById(R.id.finish_time);
        finishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFinishTimeDialog();
            }
        });

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScheduleToDb();
                showNotification(scheduleCalender.getTimeInMillis());
                Intent intent = new Intent(AddScheduleActivity.this, TrainingSchedulerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveScheduleToDb(){
        Schedule schedule = new Schedule(
                jenis,
                String.valueOf(dateResult.getText()),
                String.valueOf(startTimeText.getText()),
                String.valueOf(finishTimeText.getText())
        );
        AppDatabase.getDatabase(getApplicationContext()).getScheduleDao().insertSchedule(schedule);
    }

    private void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                scheduleCalender.set(year, monthOfYear, dayOfMonth);

                Log.d("DATE SCHEDULE ", dateFormatter.format(scheduleCalender.getTime()));
                dateResult.setText(dateFormatter.format(scheduleCalender.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    private void showStartTimeDialog() {

        Calendar calendar = Calendar.getInstance();

        startTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                scheduleCalender.set(
                        scheduleCalender.getTime().getYear(),
                        scheduleCalender.getTime().getMonth(),
                        scheduleCalender.getTime().getDate(),
                        hourOfDay,
                        minute,
                        0
                );

                startTimeText.setText(hourOfDay+":"+minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));

        startTimePickerDialog.show();
    }

    private void showFinishTimeDialog() {

        Calendar calendar = Calendar.getInstance();

        finishTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                finishTimeText.setText(hourOfDay+":"+minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));

        finishTimePickerDialog.show();
    }

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "WorkoutScheduleReminderChannel";
            String description = "Channel for Workout Schedule Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifySchedule", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(long timeInMillis){
        Intent intent = new Intent(AddScheduleActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddScheduleActivity.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }

}
