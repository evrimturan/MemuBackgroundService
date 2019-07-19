package com.campaignup.memubgsvc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.util.Log;
import android.widget.Toast;

import com.campaignup.memubgsvc.service.RunAfterBootService;

public class BootDeviceReceiver extends BroadcastReceiver {

    private static final String TAG_BOOT_BROADCAST_RECEIVER = "BOOT_BROADCAST_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        String message = "BootDeviceReceiver onReceive, action is " + action;

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        Log.d(TAG_BOOT_BROADCAST_RECEIVER, action);

        if(Intent.ACTION_BOOT_COMPLETED.equals(action))
        {
            startServiceByAlarm(context);
        }
    }

    private void startServiceByAlarm(Context context)
    {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, RunAfterBootService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long startTime = System.currentTimeMillis();
        long intervalTime = 60*1000;

        String message = "Start service use repeat alarm. ";

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        Log.d(TAG_BOOT_BROADCAST_RECEIVER, message);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
    }
}
