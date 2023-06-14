package com.example.repeated_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onReceive(Context context, Intent intent) {

        MainActivity.getInstance().startNotification();

    }
}
