package me.stupideme.shucampus.model;

import java.util.ArrayList;
import java.util.List;

import me.stupideme.shucampus.db.DBManager;

/**
 * Created by StupidL on 2016/12/10.
 */

public class ReminderModel {
    private static ReminderModel INSTANCE;
    private DBManager manager;

    private ReminderModel() {
        manager = DBManager.getInstance();
    }

    public static ReminderModel getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ReminderModel();

        return INSTANCE;
    }

    public void addReminder(ReminderBean bean) {
        manager.insertReminder(bean);
    }

    public void removeReminder(ReminderBean reminderBean) {
        manager.deleteReminder(reminderBean);
    }

    public List<ReminderBean> autoLoadReminder(){
       return manager.getAllReminder();
    }
}
