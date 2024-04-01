package com.example.studentscheduler.UI;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.studentscheduler.R;

public class ReminderBroadcast extends BroadcastReceiver {

    String content;

    @Override
    public void onReceive(Context context, Intent intent) {

        content = intent.getStringExtra("content");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify")
                .setSmallIcon(R.drawable.ic_baseline_add_alert_24)
                .setContentTitle("Student Scheduler")
                .setContentText(content + " tomorrow!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }

}
