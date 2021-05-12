package com.example.workout.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.workout.R;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        MediaPlayer mediaPlayer = MediaPlayer.create(context,
                Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifySchedule")
                .setSmallIcon(R.drawable.ic_schedule)
                .setContentTitle("Remind Schedule")
                .setContentText("Time to workout")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());
    }
}
