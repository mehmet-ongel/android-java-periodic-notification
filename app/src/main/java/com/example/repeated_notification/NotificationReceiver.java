package com.example.repeated_notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {

    Notification.Builder builder;
    NotificationManagerCompat compat;
    public static final String CHANNEL_ID = "1";

    @Override
    public void onReceive(Context context, Intent intent) {

        createNotification(context);

    }

    @SuppressLint("MissingPermission")
    public void createNotification(Context context){

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        builder = new Notification.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.baseline_add_alert_24)
                .setContentTitle("Title")
                .setContentText("Notification Text");

        compat = NotificationManagerCompat.from(context);
        compat.notify(1, builder.build());

    }
}
