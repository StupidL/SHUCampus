package me.stupideme.shucampus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.stupideme.shucampus.model.AlarmModel;
import me.stupideme.shucampus.model.CourseBean;
import me.stupideme.shucampus.model.ReminderBean;


/**
 * Created by 56211 on 2016/8/3.
 */

public class DBManager {

    private static SQLiteDatabase db;
    private static final String TAG = DBManager.class.getSimpleName();
    private static DBManager INSTANCE;
    public static Context mContext;


    private DBManager() {
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        db = dbHelper.getReadableDatabase();
        Log.i(TAG, "DBManager constructed");
    }

    public static DBManager getInstance(Context context) {

        if (null == INSTANCE) {
            INSTANCE = new DBManager();
        }
        return INSTANCE;
    }

    public static DBManager getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new DBManager();
        }
        return INSTANCE;
    }

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * operations about reminder model
     */
    public void insertReminder(ContentValues values) {
        db.insert("reminder", null, values);
    }

    public void insertReminder(ReminderBean model) {
        ContentValues values = reminderToContentValues(model);
        db.insert("reminder", null, values);
    }

    public void deleteReminder(long reminderId) {
        db.delete("reminder", DBHelper.REMINDER_ID + "=?", new String[]{String.valueOf(reminderId)});
    }

    public void deleteReminder(ReminderBean bean) {
        db.delete("reminder", "id = ? and title = ?", new String[]{String.valueOf(bean.getId()), bean.getTitle()});
    }

    public void deleteAllReminder() {
        db.execSQL("DELETE FROM reminder");
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ = 0 WHERE NAME ='reminder'");
    }

    public void updateReminder(ContentValues values) {
        db.update("reminder", values, DBHelper.REMINDER_ID + "= ?", new String[]{values.getAsString(DBHelper.REMINDER_ID)});
    }

    public Cursor queryReminder(long reminderId) {
        return db.rawQuery("SELECT * FROM reminder WHERE " + DBHelper.REMINDER_ID + " = ?", new String[]{String.valueOf(reminderId)});
    }

    public Cursor queryAllReminder() {

        Cursor cursor = db.rawQuery("SELECT * FROM reminder WHERE _id>=0", null);
        Log.i("======cursor======", cursor.toString());
        return cursor;
    }

    public ReminderBean contentValuesToReminder(ContentValues values) {
        ReminderBean reminder = new ReminderBean();
        reminder.setId(values.getAsLong(DBHelper.REMINDER_ID));
        reminder.setTitle(values.getAsString(DBHelper.REMINDER_TITLE));
        reminder.setContent(values.getAsString(DBHelper.REMINDER_CONTENT));
        return reminder;
    }

    public ContentValues reminderToContentValues(ReminderBean reminder) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.REMINDER_ID, reminder.getId());
        values.put(DBHelper.REMINDER_TITLE, reminder.getTitle());
        values.put(DBHelper.REMINDER_CONTENT, reminder.getContent());
        return values;
    }

    public ReminderBean cursorToReminder(Cursor cursor) {
        ReminderBean reminder = new ReminderBean();
        reminder.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.REMINDER_ID)));
        reminder.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.REMINDER_TITLE)));
        reminder.setContent(cursor.getString(cursor.getColumnIndex(DBHelper.REMINDER_CONTENT)));
        return reminder;
    }

    public ReminderBean getReminderModel(long reminderId) {
        return cursorToReminder(queryReminder(reminderId));
    }

    public List<ReminderBean> getAllReminder() {
        List<ReminderBean> list = new ArrayList<>();
        Cursor cursor = queryAllReminder();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursorToReminder(cursor));
            cursor.moveToNext();
        }
        return list;
    }


    /**
     * operations about alarm model
     */

    public void insertAlarm(ContentValues values) {
        db.insert("alarm", null, values);
    }

    public void insertAlarm(AlarmModel model) {
        ContentValues values = alarmToContentValues(model);
        db.insert("alarm", null, values);
    }

    public void deleteAlarm(long reminderId) {
        db.delete("alarm", "reminderId=?", new String[]{String.valueOf(reminderId)});
    }

    public void deleteAllAlarm() {
        db.execSQL("DELETE FROM alarm");
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ = 0 WHERE NAME ='alarm'");
    }

    public void updateAlarm(ContentValues values) {
        db.update("alarm", values, "reminderId = ?", new String[]{values.getAsString("reminderId")});
    }

    public Cursor queryAlarm(long reminderId) {
        return db.rawQuery("SELECT * FROM alarm WHERE reminderId = ?", new String[]{String.valueOf(reminderId)});
    }

    public Cursor queryAllAlarm() {
        return db.rawQuery("SELECT * FROM alarm", null);
    }


    public ContentValues alarmToContentValues(AlarmModel alarmModel) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.ALARM_REMINDER_ID, alarmModel.getReminderId());
        values.put(DBHelper.ALARM_TIME_YEAR, alarmModel.getTimeYear());
        values.put(DBHelper.ALARM_TIME_MONTH, alarmModel.getTimeMonth());
        values.put(DBHelper.ALARM_TIME_DAY, alarmModel.getTimeDay());
        values.put(DBHelper.ALARM_TIME_HOUR, alarmModel.getTimeHour());
        values.put(DBHelper.ALARM_TIME_MINUTE, alarmModel.getTimeMinute());
        values.put(DBHelper.ALARM_REPEAT_WEEKLY, alarmModel.isRepeatWeekly());

        String repeatingDays = null;
        for (boolean b : alarmModel.getRepeatingDays()) {
            repeatingDays = repeatingDays + b + ",";
        }
        values.put(DBHelper.ALARM_REPEAT_DAYS, repeatingDays);

        values.put(DBHelper.ALARM_NAME, alarmModel.getName());
        values.put(DBHelper.ALARM_TONE, String.valueOf(alarmModel.getAlarmTone()));//  type cast!!!
        values.put(DBHelper.ALARM_ENABLED, alarmModel.isEnabled());
        return values;
    }
//
//    public static AlarmModel contentValuesToAlarm(ContentValues values) {
//        AlarmModel alarm = new AlarmModel();
//        alarm.setReminderId(values.getAsLong("reminderId"));
//        alarm.setTimeHour(values.getAsInteger("timeHour"));
//        alarm.setTimeMinute(values.getAsInteger("timeMinute"));
//        alarm.setRepeatWeekly(values.getAsBoolean("repeatWeekly"));
//        alarm.setRepeatingDays(String.values.getAsByteArray("repeatingDays"));
//    }

    public AlarmModel cursorToAlarm(Cursor cursor) {
        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setReminderId(cursor.getLong(cursor.getColumnIndex(DBHelper.ALARM_REMINDER_ID)));
        alarmModel.setTimeYear(cursor.getInt(cursor.getColumnIndex(DBHelper.ALARM_TIME_YEAR)));
        alarmModel.setTimeMonth(cursor.getInt(cursor.getColumnIndex(DBHelper.ALARM_TIME_MONTH)));
        alarmModel.setTimeDay(cursor.getInt(cursor.getColumnIndex(DBHelper.ALARM_TIME_DAY)));
        alarmModel.setTimeHour(cursor.getInt(cursor.getColumnIndex(DBHelper.ALARM_TIME_HOUR)));
        alarmModel.setTimeMinute(cursor.getInt(cursor.getColumnIndex(DBHelper.ALARM_TIME_MINUTE)));
        alarmModel.setRepeatWeekly(cursor.getInt(cursor.getColumnIndex(DBHelper.ALARM_REPEAT_WEEKLY)) == 1);

        String[] repeatDays = cursor.getString(cursor.getColumnIndex(DBHelper.ALARM_REPEAT_DAYS)).split(",");
        for (int i = 0; i < repeatDays.length; ++i) {
            alarmModel.setRepeatingDays(i, !repeatDays[i].equals("false"));
        }

        alarmModel.setName(cursor.getString(cursor.getColumnIndex(DBHelper.ALARM_NAME)));
        alarmModel.setAlarmTone(Uri.parse(cursor.getString(cursor.getColumnIndex(DBHelper.ALARM_TONE))));
        alarmModel.setEnabled(cursor.getInt(cursor.getColumnIndex(DBHelper.ALARM_ENABLED)) != 0);
        return alarmModel;
    }

    public AlarmModel getAlarmModel(long reminderId) {
        return cursorToAlarm(queryAlarm(reminderId));
    }

    public List<AlarmModel> getAllAlarm() {
        List<AlarmModel> list = new ArrayList<>();
        Cursor cursor = queryAllAlarm();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursorToAlarm(cursor));
            cursor.moveToNext();
        }
        return list;
    }


    /**
     * operations about class model
     */

    public void insertClass(ContentValues values) {
        db.insert("class", null, values);
    }

    public void insertClass(CourseBean model) {
        ContentValues values = classToContentValues(model);
        db.insert("class", null, values);
    }

    public void deleteClass(long classId) {
        db.delete("class", "classId=?", new String[]{String.valueOf(classId)});
    }

    public void deleteAllClass() {
        db.execSQL("DELETE FROM class");
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ = 0 WHERE NAME ='class'");
    }

    public void updateClass(ContentValues values) {
        db.update("class", values, "classId=?", new String[]{values.getAsString("classId")});
    }

    public Cursor queryClass(long classId) {
        return db.rawQuery("SELECT * FROM class WHERE classId=?", new String[]{String.valueOf(classId)});
    }

    public Cursor queryAllClass() {
        return db.rawQuery("SELECT * FROM class", null);
    }

    public ContentValues classToContentValues(CourseBean courseBean) {
        ContentValues values = new ContentValues();
        values.put("classId", courseBean.getClassId());
        values.put("weekday", courseBean.getWeekday());
        values.put("begin", courseBean.getBegin());
        values.put("end", courseBean.getEnd());
        values.put("name", courseBean.getName());
        values.put("location", courseBean.getLocation());
        values.put("teacher", courseBean.getTeacher());
        values.put("mod", courseBean.getMod());
        values.put("color", courseBean.getColor());
        return values;
    }

    public CourseBean contentValuesToClass(ContentValues values) {
        CourseBean courseBean = new CourseBean();
        courseBean.setClassId(values.getAsLong("classId"));
        courseBean.setWeekday(values.getAsInteger("weekday"));
        courseBean.setBegin(values.getAsInteger("begin"));
        courseBean.setEnd(values.getAsInteger("end"));
        courseBean.setName(values.getAsString("name"));
        courseBean.setLocation(values.getAsString("location"));
        courseBean.setTeacher(values.getAsString("teacher"));
        courseBean.setMod(values.getAsInteger("mod"));
        courseBean.setColor(values.getAsInteger("color"));
        return courseBean;
    }

    public CourseBean cursorToClass(Cursor cursor) {
        CourseBean courseBean = new CourseBean();
        courseBean.setClassId(cursor.getLong(cursor.getColumnIndex("classId")));
        courseBean.setWeekday(cursor.getInt(cursor.getColumnIndex("weekday")));
        courseBean.setBegin(cursor.getInt(cursor.getColumnIndex("begin")));
        courseBean.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
        courseBean.setName(cursor.getString(cursor.getColumnIndex("name")));
        courseBean.setLocation(cursor.getString(cursor.getColumnIndex("location")));
        courseBean.setTeacher(cursor.getString(cursor.getColumnIndex("teacher")));
        courseBean.setMod(cursor.getInt(cursor.getColumnIndex("mod")));
        courseBean.setColor(cursor.getInt(cursor.getColumnIndex("color")));
        return courseBean;
    }

    public CourseBean getClassModel(long classId) {
        return cursorToClass(queryClass(classId));
    }

    public List<CourseBean> getAllClass() {
        List<CourseBean> list = new ArrayList<>();
        Cursor cursor = queryAllClass();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursorToClass(cursor));
            cursor.moveToNext();
        }
        return list;
    }

    public String queryUserInfo() {
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE _id >= ?", new String[]{"0"});
        StringBuilder builder = new StringBuilder();
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            builder.append(cursor.getString(cursor.getColumnIndex("name"))).append(",")
                    .append(cursor.getString(cursor.getColumnIndex("password")));
        }
        cursor.close();
        return builder.toString();
    }

    public void insertUserInfo(String name, String pwd) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("password", pwd);
        db.insert("user", null, values);
    }
}
