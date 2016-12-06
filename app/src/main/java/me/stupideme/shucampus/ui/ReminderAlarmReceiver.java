package me.stupideme.shucampus.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.List;

import me.stupideme.shucampus.db.DBManager;
import me.stupideme.shucampus.model.AlarmModel;

/**
 * Created by 56211 on 2016/8/3.
 */

public class ReminderAlarmReceiver extends BroadcastReceiver {
    private DBManager manager;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("me.stupidme.action.UPDATE_ALARM")) {
            manager = DBManager.getInstance(context);
            cancelAlarms(context);
            resetAlarms(context);
        }
    }

    private void resetAlarms(Context context) {
        List<AlarmModel> list = manager.getAllAlarm();
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (!list.isEmpty()) {
            for (AlarmModel model : list) {
                Calendar calendar = Calendar.getInstance();
                long currentTime = calendar.getTimeInMillis();
                calendar.set(Calendar.YEAR, model.getTimeYear());
                calendar.set(Calendar.MONTH, model.getTimeMonth());
                calendar.set(Calendar.HOUR_OF_DAY, model.getTimeHour());
                calendar.set(Calendar.MINUTE, model.getTimeMinute());
                long alarmTime = calendar.getTimeInMillis();
                if (currentTime < alarmTime) {
                    PendingIntent pi = createPendingIntent(context, model);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        am.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pi);
                    } else {
                        am.set(AlarmManager.RTC_WAKEUP, alarmTime, pi);
                    }
                }
            }
        }
    }

    private  void cancelAlarms(Context context) {

        List<AlarmModel> list = manager.getAllAlarm();
        if (!list.isEmpty()) {
            for (AlarmModel model : list) {
                PendingIntent pendingIntent = createPendingIntent(context, model);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            }
        }
    }

    private static PendingIntent createPendingIntent(Context context, AlarmModel model) {
        Intent intent = new Intent(context, ReminderNotificationService.class);
        intent.putExtra("reminderId", model.getReminderId());   //reminderId是唯一的

        return PendingIntent.getService(context, (int) model.getReminderId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //return PendingIntent.getBroadcast(context, 0, intent, 0);
        //return PendingIntent.getActivity(context,0,intent,0);
    }
}
