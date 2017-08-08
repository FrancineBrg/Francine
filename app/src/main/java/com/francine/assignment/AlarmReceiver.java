package com.francine.assignment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int MINUTE = 60;

    @Override
    public void onReceive(Context context, Intent intent) {
        scheduleAlarm(context);
        context.sendBroadcast(new Intent("com.francine.assignment.MainActivity"));
    }

    private void scheduleAlarm(Context context) {
        AlarmManager manager =  (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int seconds2Minute = calendar.get(Calendar.SECOND);

        if (seconds2Minute < (MINUTE/2))
        {
            seconds2Minute = (MINUTE/2) - seconds2Minute;
        } else {
            seconds2Minute = MINUTE - seconds2Minute;
        }

        long inicio = calendar.getTimeInMillis() + seconds2Minute * 1000;
        manager.set(AlarmManager.RTC_WAKEUP, inicio, alarmIntent);
    }
}
