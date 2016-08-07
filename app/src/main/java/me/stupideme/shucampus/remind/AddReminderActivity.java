package me.stupideme.shucampus.remind;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.db.DBManager;

public class AddReminderActivity extends AppCompatActivity {

    private ReminderModel tmpModel;
    private EditText title;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("编辑提醒");

        title = (EditText) findViewById(R.id.edit_title);
        content = (EditText) findViewById(R.id.edit_content);

        Intent intent = getIntent();
        String iTitle = intent.getStringExtra("title");
        String iContent = intent.getStringExtra("content");
        long iId = intent.getLongExtra("id", 0L);
        tmpModel = new ReminderModel();
        tmpModel.setId(iId);
        tmpModel.setTitle(iTitle);
        tmpModel.setContent(iContent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final ReminderModel model = new ReminderModel();
        final AlarmModel alarmModel = new AlarmModel();
        switch (item.getItemId()) {
            case R.id.actions_save:

                if (model.getId() != 0L) {
                    model.setTitle(title.getText().toString());
                    model.setContent(content.getText().toString());
                    DBManager manager = DBManager.getInstance(AddReminderActivity.this);
                    manager.insertReminder(model);
                    manager.insertAlarm(alarmModel);
                    if (tmpModel.getId() != 0L) {
                        manager.deleteReminder(tmpModel.getId());
                        manager.deleteAlarm(tmpModel.getId());
                    }
                    //notify receiver
                    Intent intent = new Intent(AddReminderActivity.this, AlarmReceiver.class);
                    intent.setAction("me.stupidme.action.UPDATE_ALARM");
                    this.sendBroadcast(intent);
                    //notify data set
                    setResult(0x102);
                    AddReminderActivity.this.finish();

                } else {
                    Snackbar.make(findViewById(R.id.actions_save), "请设置提醒时间", Snackbar.LENGTH_SHORT).show();
                }

                break;

            case R.id.actions_alarm:

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                final int hour = c.get(Calendar.HOUR_OF_DAY);
                final int minute = c.get(Calendar.MINUTE);


                new DatePickerDialog(AddReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        final Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                        alarmModel.setTimeYear(year);
                        alarmModel.setTimeMonth(monthOfYear);
                        alarmModel.setTimeDay(dayOfMonth);

                        new TimePickerDialog(AddReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                alarmModel.setTimeHour(hourOfDay);
                                alarmModel.setTimeMinute(minute);
                            }
                        }, hour, minute, true).show();

                        model.setId(calendar.getTimeInMillis());
                        Log.i("======id====", String.valueOf(calendar.getTimeInMillis()));
                        alarmModel.setReminderId(calendar.getTimeInMillis());


                    }
                }, year, month, day).show();
                Log.i("====c====", String.valueOf(c.getTimeInMillis()));

                break;
        }
        return true;
    }

}
