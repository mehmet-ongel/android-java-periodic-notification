package com.example.repeated_notification;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Notification.Builder builder;
    NotificationManagerCompat compat;
    public static final String CHANNEL_ID = "1";
    PendingIntent pendingIntent;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,14);
        calendar.set(Calendar.MINUTE,30);
        calendar.set(Calendar.SECOND,0);

        Intent i = new Intent(getApplicationContext(),NotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, i, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }

    //to access the StartNotification method from the Receiver Class we need an instance of the MainActivity
    public static MainActivity getInstance() {
        return instance;
    }

    /*
    The POST_NOTIFICATION permission requires API 33 and above.
    For this we add the @RequiresApi annotation
    Instead, you can handle this with an if statement below
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void startNotification(){

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        builder = new Notification.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.baseline_add_alert_24)
                .setContentTitle("Title")
                .setContentText("Notification Text");

        compat = NotificationManagerCompat.from(this);

        //check the POST_NOTIFICATION permission
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){

            //if there is no given permission, request it
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},1);
        }else {
            compat.notify(1, builder.build());
        }
    }

    //handle the permission result
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            startNotification();
        }
    }
}