package me.stupideme.shucampus.remind;

import android.net.Uri;

/**
 * Created by 56211 on 2016/8/3.
 */

public class AlarmModel {
    private int timeHour;
    private int timeMinute;
    private boolean repeatWeekly;
    private boolean repeatingDays[];
    private String name;
    private Uri alarmTone;
    private boolean isEnabled;
    private long reminderId;

    public AlarmModel(){
        repeatingDays = new boolean[7];
    }

    public void setRepeatingDays(int dayOfWeek, boolean value){
        repeatingDays[dayOfWeek] = value;
    }

    public void setRepeatingDays(boolean values[]){
        repeatingDays = values;
    }

    public boolean[] getRepeatingDays(){
        return repeatingDays;
    }
    public boolean ifRepeat(int dayOfweek){
        return repeatingDays[dayOfweek];
    }


    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }

    public boolean isRepeatWeekly() {
        return repeatWeekly;
    }

    public void setRepeatWeekly(boolean repeatWeekly) {
        this.repeatWeekly = repeatWeekly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getAlarmTone() {
        return alarmTone;
    }

    public void setAlarmTone(Uri alarmTone) {
        this.alarmTone = alarmTone;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public long getReminderId() {
        return reminderId;
    }

    public void setReminderId(long reminderId) {
        this.reminderId = reminderId;
    }
}
