package me.stupideme.shucampus.view;

import java.util.List;

import me.stupideme.shucampus.model.ReminderBean;

/**
 * Created by StupidL on 2016/12/10.
 */

public interface ReminderView {

    void autoLoadReminder(List<ReminderBean> list);

    void addReminder(ReminderBean bean);

    void removeReminder(ReminderBean bean);

    void setReminderAlarm(long millis);

    void cancelReminderAlarm(long millis);
}
