package me.stupideme.shucampus.remind;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.List;

import me.stupideme.shucampus.db.DBManager;

/**
 * Created by 56211 on 2016/8/3.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    private static void setAlarms(Context context) {

        cancelAlarms(context);  //首先取消全部有效的闹钟

        List<AlarmModel> list = DBManager.getAllAlarm();    //获取所有的闹钟
        for (AlarmModel model : list) {
            if(model.isEnabled()){
                PendingIntent pIntent = createPendingIntent(context, model);

                //通过Calendar对象，设置闹钟时间
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, model.getTimeHour());
                calendar.set(Calendar.MINUTE, model.getTimeMinute());
                calendar.set(Calendar.SECOND, 00);      //没有考虑秒，可以在AlarmModel里面加上这个属性


                //Find next time to set
                final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                boolean alarmSet = false;

                //First check if it's later in the week
                //Calendar.SUNDAY = 1, 在repeatingDays[]数组里面下标为0
                for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                    if (model.ifRepeat(dayOfWeek - 1) && dayOfWeek >= nowDay && //处于今天之后的时间，并且重复
                            !(dayOfWeek == nowDay && model.getTimeHour() < nowHour) &&  //在今天，但是在现在的小时之后
                            !(dayOfWeek == nowDay && model.getTimeHour() == nowHour && model.getTimeMinute() <= nowMinute)) //在现在的小时，但是要在现在的分钟之后
                    {
                        //以上条件即：闹钟设置的时间在当前时间之后
                        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);  //根据闹钟设置day_of_week
                        setAlarm(context, calendar, pIntent);           //设置闹钟
                        alarmSet = true;
                        break;
                    }
                }

                //设置的时间在当前时间之前，但是设置了重复
                if (!alarmSet) {
                    for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                        //如果闹钟设置了重复，并且每周都重复，并且在当前周已经过去了
                        if (model.ifRepeat(dayOfWeek - 1) && dayOfWeek <= nowDay && model.isRepeatWeekly()) {
                            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                            calendar.add(Calendar.WEEK_OF_YEAR, 1);
                            setAlarm(context, calendar, pIntent);
                            alarmSet = true;
                            break;
                        }
                    }
                }
            }
        }

    }

    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }

    private static void cancelAlarms(Context context) {

        List<AlarmModel> list = DBManager.getAllAlarm();
        if (!list.isEmpty()) {
            for (AlarmModel model : list) {
                if (model.isEnabled()) {
                    PendingIntent pendingIntent = createPendingIntent(context, model);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent); //通过AlarmManager将pendingIntent发送出去
                }
            }
        }
    }

    private static PendingIntent createPendingIntent(Context context, AlarmModel model) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra("id", model.getReminderId());
        intent.putExtra("name", model.getName());
        intent.putExtra("timeHour", model.getTimeHour());
        intent.putExtra("timeMinute", model.getTimeMinute());
        intent.putExtra("alarmTone", model.getAlarmTone().toString());

        return PendingIntent.getService(context, (int) model.getReminderId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
