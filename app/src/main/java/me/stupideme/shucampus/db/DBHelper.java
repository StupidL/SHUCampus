package me.stupideme.shucampus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 56211 on 2016/8/3.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper INSTANCE;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "alarm_clock.db";

    public static final String ALARM_TABLE_NAME = "alarm";
    public static final String ALARM_NAME = "name";
    public static final String ALARM_REMINDER_ID = "reminderID";
    public static final String ALARM_TIME_YEAR = "year";
    public static final String ALARM_TIME_MONTH = "month";
    public static final String ALARM_TIME_DAY = "day";
    public static final String ALARM_TIME_HOUR = "hour";
    public static final String ALARM_TIME_MINUTE = "minute";
    public static final String ALARM_REPEAT_DAYS = "days";
    public static final String ALARM_REPEAT_WEEKLY = "weekly";
    public static final String ALARM_TONE = "tone";
    public static final String ALARM_ENABLED = "enabled";

    public static final String REMINDER_TABLE_NAME = "reminder";
    public static final String REMINDER_ID = "id";
    public static final String REMINDER_TITLE = "title";
    public static final String REMINDER_CONTENT = "content";

    private static final String CLASS_TABLE_NAME = "class";
    private static final String CLASS_ID = "classId";
    private static final String CLASS_WEEKDAY = "weekday";
    private static final String CLASS_BEGIN = "begin";
    private static final String CLASS_END = "end";
    private static final String CLASS_NAME = "name";
    private static final String CLASS_LOCATION = "location";
    private static final String CLASS_TEACHER = "teacher";
    private static final String CLASS_MOD = "mod";
    private static final String CLASS_COLOR = "color";

    private static final String SQL_CREATE_ALARM = "CREATE TABLE " + ALARM_TABLE_NAME + "("
            + "_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ALARM_NAME + ", " + ALARM_REMINDER_ID + ", " + ALARM_TIME_YEAR + ", " + ALARM_TIME_MONTH + ", "
            + ALARM_TIME_DAY + ", " + ALARM_TIME_HOUR + ", " + ALARM_TIME_MINUTE + ", "
            + ALARM_REPEAT_DAYS + ", " + ALARM_REPEAT_WEEKLY + ", " + ALARM_TONE + ", " + ALARM_ENABLED
            + ")";

    private static final String SQL_CREATE_REMINDER = "CREATE TABLE " + REMINDER_TABLE_NAME + "("
            + "_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + REMINDER_ID + ", " + REMINDER_TITLE + ", " + REMINDER_CONTENT
            + ")";

    private static final String SQL_CREATE_CLASS = "CREATE TABLE " + CLASS_TABLE_NAME + "("
            + "_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CLASS_ID + ", " + CLASS_WEEKDAY + ", " + CLASS_BEGIN + ", " + CLASS_END + ", "
            + CLASS_NAME + ", " + CLASS_LOCATION + ", " + CLASS_TEACHER + ", "
            + CLASS_MOD + ", " + CLASS_COLOR
            + ")";

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static DBHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DBHelper(context);
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALARM);
        db.execSQL(SQL_CREATE_REMINDER);
        db.execSQL(SQL_CREATE_CLASS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
