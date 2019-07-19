package com.campaignup.memubgsvc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.campaignup.memubgsvc.service.RunAfterBootService;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    //for memu
    /*
    private static final String TAG_BOOT_MAIN = "BOOT_MAIN";
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    */
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "Main Activiy Works", Toast.LENGTH_LONG).show();

        //for mwmu
        /*
        Log.d(TAG_BOOT_MAIN, getIntent().getAction());
        startServiceByAlarm(this);
        */
        //

    }

    //for memu
    /*
    private void startServiceByAlarm(Context context)
    {
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, RunAfterBootService.class);
        pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long startTime = System.currentTimeMillis();
        long intervalTime = 60*1000;

        String message = "Start service use repeat alarm. ";

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        Log.d(TAG_BOOT_MAIN, message);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
    }

    @Override
    protected void onDestroy() {
        alarmManager.cancel(pendingIntent);
        super.onDestroy();
    }
    */
    //
}
