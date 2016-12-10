package me.stupideme.shucampus.presenter;

import java.util.Calendar;

import me.stupideme.shucampus.model.ReminderBean;
import me.stupideme.shucampus.model.ReminderModel;
import me.stupideme.shucampus.view.ReminderView;

/**
 * Created by StupidL on 2016/12/10.
 */

public class ReminderPresenter {
    private ReminderView mReminderView;
    private ReminderModel mReminderModel;
    private static ReminderPresenter INSTANCE;

    private ReminderPresenter(ReminderView view) {
        mReminderView = view;
        mReminderModel = ReminderModel.getInstance();
    }

    public static ReminderPresenter getInstance(ReminderView view) {
        if (INSTANCE == null) {
            synchronized (ReminderPresenter.class) {
                if (INSTANCE == null)
                    INSTANCE = new ReminderPresenter(view);
            }
        }
        return INSTANCE;
    }

    public void remove(ReminderBean reminderBean) {
        mReminderView.removeReminder(reminderBean);
        mReminderModel.removeReminder(reminderBean);
        mReminderView.cancelReminderAlarm(reminderBean.getId());
    }

    public void addReminder(String reminderInfo) {
        ReminderBean bean = new ReminderBean();
        String[] infos = reminderInfo.split("%");
        String title = infos[0];
        String content = infos[1];
        String time = infos[2];
        String[] times = time.split("\\+");

        String year = times[0];
        String month = times[1];
        String day = times[2];
        String hour = times[3];
        String minute = times[4];

        int yearNum = Integer.parseInt(year);
        int monthNum = Integer.parseInt(month) + 1;
        int dayNum = Integer.parseInt(day);
        int hourNum = Integer.parseInt(hour);
        int minuteNum = Integer.parseInt(minute);

        bean.setTitle(title);
        bean.setContent(content);

        if (times[0].equals("0")) {
            bean.setTime("");
        } else {
            StringBuilder builder = new StringBuilder();
            String timeText = builder.append(year).append("/")
                    .append(monthNum).append("/")
                    .append(day).append(" ")
                    .append(hour).append(":")
                    .append(minute).toString();
            bean.setTime(timeText);
        }
        mReminderView.addReminder(bean);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, yearNum);
        calendar.set(Calendar.MONTH, monthNum);
        calendar.set(Calendar.DAY_OF_MONTH, dayNum);
        calendar.set(Calendar.HOUR_OF_DAY, hourNum);
        calendar.set(Calendar.MINUTE, minuteNum);
        long timeInMillis = calendar.getTimeInMillis();
        bean.setId(timeInMillis);
        mReminderModel.addReminder(bean);
        mReminderView.setReminderAlarm(bean.getId());
    }

    public void autoLoadReminder() {
        mReminderView.autoLoadReminder(mReminderModel.autoLoadReminder());
    }
}
