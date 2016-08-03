package me.stupideme.shucampus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.stupideme.shucampus.remind.AlarmModel;
import me.stupideme.shucampus.model.ClassModel;
import me.stupideme.shucampus.remind.ReminderModel;


/**
 * Created by 56211 on 2016/8/3.
 */

public class DBManager {

    private static SQLiteDatabase db;
    private static final String TAG = DBManager.class.getSimpleName();
    private static DBManager INSTANCE;


    private DBManager(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getReadableDatabase();
        Log.i(TAG, "DBManager constructed");
    }

    public static DBManager getInstance(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new DBManager(context);
        }
        return INSTANCE;
    }

    /**
     * operations about reminder model
     */
    public static void insertReminder(ContentValues values) {
        db.insert("reminder", null, values);
    }

    public static void deleteReminder(long reminderId) {
        db.delete("reminder", "reminderId=?", new String[]{String.valueOf(reminderId)});
    }

    public static void deleteAllReminder() {
        db.execSQL("DELETE FROM reminder");
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ = 0 WHERE NAME ='reminder'");
    }

    public static void updateReminder(ContentValues values) {
        db.update("reminder", values, "reminderId = ?", new String[]{values.getAsString("reminderId")});
    }

    public static Cursor queryReminder(long reminderId) {
        return db.rawQuery("SELECT * FROM reminder WHERE reminderId = ?", new String[]{String.valueOf(reminderId)});
    }

    public static Cursor queryAllReminder() {
        return db.rawQuery("SELECT * FROM reminder", null);
    }

    public static ReminderModel contentValuesToReminder(ContentValues values) {
        ReminderModel reminder = new ReminderModel();
        reminder.setId(values.getAsLong("reminderId"));
        reminder.setTitle(values.getAsString("title"));
        reminder.setContent(values.getAsString("content"));
        return reminder;
    }

    public static ContentValues reminderToContentValues(ReminderModel reminder) {
        ContentValues values = new ContentValues();
        values.put("reminderId", reminder.getId());
        values.put("title", reminder.getTitle());
        values.put("content", reminder.getContent());
        return values;
    }

    public static ReminderModel cursorToReminder(Cursor cursor) {
        ReminderModel reminder = new ReminderModel();
        reminder.setId(cursor.getLong(cursor.getColumnIndex("reminderId")));
        reminder.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        reminder.setContent(cursor.getString(cursor.getColumnIndex("content")));
        return reminder;
    }

    public static ReminderModel getReminderModel(long reminderId) {
        return cursorToReminder(queryReminder(reminderId));
    }

    public static List<ReminderModel> getAllReminder() {
        List<ReminderModel> list = new ArrayList<>();
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

    public static void insertAlarm(ContentValues values) {
        db.insert("alarm", null, values);
    }

    public static void deleteAlarm(long reminderId) {
        db.delete("alarm", "reminderId=?", new String[]{String.valueOf(reminderId)});
    }

    public static void deleteAllAlarm() {
        db.execSQL("DELETE FROM alarm");
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ = 0 WHERE NAME ='alarm'");
    }

    public static void updateAlarm(ContentValues values) {
        db.update("alarm", values, "reminderId = ?", new String[]{values.getAsString("reminderId")});
    }

    public static Cursor queryAlarm(long reminderId) {
        return db.rawQuery("SELECT * FROM alarm WHERE reminderId = ?", new String[]{String.valueOf(reminderId)});
    }

    public static Cursor queryAllAlarm() {
        return db.rawQuery("SELECT * FROM alarm", null);
    }


    public static ContentValues alarmToContentValues(AlarmModel alarmModel) {
        ContentValues values = new ContentValues();
        values.put("reminderId", alarmModel.getReminderId());
        values.put("timeYear",alarmModel.getTimeYear());
        values.put("timeMonth",alarmModel.getTimeMonth());
        values.put("timeHour", alarmModel.getTimeHour());
        values.put("timeMinute", alarmModel.getTimeMinute());
        values.put("repeatWeekly", alarmModel.isRepeatWeekly());

        String repeatingDays = null;
        for (boolean b : alarmModel.getRepeatingDays()) {
            repeatingDays = repeatingDays + b + ",";
        }
        values.put("repeatingDays", repeatingDays);

        values.put("name", alarmModel.getName());
        values.put("alarmTone", String.valueOf(alarmModel.getAlarmTone()));//  type cast!!!
        values.put("isEnabled", alarmModel.isEnabled());
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

    public static AlarmModel cursorToAlarm(Cursor cursor) {
        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setReminderId(cursor.getLong(cursor.getColumnIndex("reminderId")));
        alarmModel.setTimeYear(cursor.getInt(cursor.getColumnIndex("timeYear")));
        alarmModel.setTimeMonth(cursor.getInt(cursor.getColumnIndex("timeMonth")));
        alarmModel.setTimeHour(cursor.getInt(cursor.getColumnIndex("timeHour")));
        alarmModel.setTimeMinute(cursor.getInt(cursor.getColumnIndex("timeMinute")));
        alarmModel.setRepeatWeekly(cursor.getInt(cursor.getColumnIndex("repeatWeekly")) == 1);

        String[] repeatDays = cursor.getString(cursor.getColumnIndex("repeatingDays")).split(",");
        for (int i = 0; i < repeatDays.length; ++i) {
            alarmModel.setRepeatingDays(i, !repeatDays[i].equals("false"));
        }

        alarmModel.setName(cursor.getString(cursor.getColumnIndex("name")));
        alarmModel.setAlarmTone(Uri.parse(cursor.getString(cursor.getColumnIndex("alarmTone"))));
        alarmModel.setEnabled(cursor.getInt(cursor.getColumnIndex("isEnabled")) != 0);
        return alarmModel;
    }

    public static AlarmModel getAlarmModel(long reminderId) {
        return cursorToAlarm(queryAlarm(reminderId));
    }

    public static List<AlarmModel> getAllAlarm() {
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

    public static void insertClass(ContentValues values) {
        db.insert("class", null, values);
    }

    public static void deleteClass(long classId) {
        db.delete("class", "classId=?", new String[]{String.valueOf(classId)});
    }

    public static void deleteAllClass() {
        db.execSQL("DELETE FROM class");
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ = 0 WHERE NAME ='class'");
    }

    public static void updateClass(ContentValues values) {
        db.update("class", values, "classId=?", new String[]{values.getAsString("classId")});
    }

    public static Cursor queryClass(long classId) {
        return db.rawQuery("SELECT * FROM class WHERE classId=?", new String[]{String.valueOf(classId)});
    }

    public static Cursor queryAllClass() {
        return db.rawQuery("SELECT * FROM class", null);
    }

    public static ContentValues classToContentValues(ClassModel classModel) {
        ContentValues values = new ContentValues();
        values.put("classId", classModel.getClassId());
        values.put("weekday", classModel.getWeekday());
        values.put("begin", classModel.getBegin());
        values.put("end", classModel.getEnd());
        values.put("name", classModel.getName());
        values.put("location", classModel.getLocation());
        values.put("teacher", classModel.getTeacher());
        values.put("mod", classModel.getMod());
        values.put("color", classModel.getColor());
        return values;
    }

    public static ClassModel contentValuesToClass(ContentValues values) {
        ClassModel classModel = new ClassModel();
        classModel.setClassId(values.getAsLong("classId"));
        classModel.setWeekday(values.getAsInteger("weekday"));
        classModel.setBegin(values.getAsInteger("begin"));
        classModel.setEnd(values.getAsInteger("end"));
        classModel.setName(values.getAsString("name"));
        classModel.setLocation(values.getAsString("location"));
        classModel.setTeacher(values.getAsString("teacher"));
        classModel.setMod(values.getAsInteger("mod"));
        classModel.setColor(values.getAsInteger("color"));
        return classModel;
    }

    public static ClassModel cursorToClass(Cursor cursor) {
        ClassModel classModel = new ClassModel();
        classModel.setClassId(cursor.getLong(cursor.getColumnIndex("classId")));
        classModel.setWeekday(cursor.getInt(cursor.getColumnIndex("weekday")));
        classModel.setBegin(cursor.getInt(cursor.getColumnIndex("begin")));
        classModel.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
        classModel.setName(cursor.getString(cursor.getColumnIndex("name")));
        classModel.setLocation(cursor.getString(cursor.getColumnIndex("location")));
        classModel.setTeacher(cursor.getString(cursor.getColumnIndex("teacher")));
        classModel.setMod(cursor.getInt(cursor.getColumnIndex("mod")));
        classModel.setColor(cursor.getInt(cursor.getColumnIndex("color")));
        return classModel;
    }

    public static ClassModel getClassModel(long classId) {
        return cursorToClass(queryClass(classId));
    }

    public static List<ClassModel> getAllClass() {
        List<ClassModel> list = new ArrayList<>();
        Cursor cursor = queryAllClass();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursorToClass(cursor));
            cursor.moveToNext();
        }
        return list;
    }
}
