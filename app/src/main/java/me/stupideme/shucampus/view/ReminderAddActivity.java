package me.stupideme.shucampus.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import me.stupideme.shucampus.R;

public class ReminderAddActivity extends AppCompatActivity {

    private static final String TAG = "ReminderAddActivity";
    private TextInputEditText mTitleEdit;
    private TextInputEditText mContentEdit;

    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private int mHour;
    private int mMinute;

    private DatePickerDialog mDateDialog;
    private TimePickerDialog mTimeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("编辑提醒");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderAddActivity.super.onBackPressed();
            }
        });

        mTitleEdit = (TextInputEditText) findViewById(R.id.edit_title);
        mContentEdit = (TextInputEditText) findViewById(R.id.edit_content);

        Calendar calendar = Calendar.getInstance();
        mDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mYear = i;
                mMonth = i1;
                mDayOfMonth = i2;
                mTimeDialog.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        mTimeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mHour = i;
                mMinute = i1;

                Log.v(TAG, "year: " + mYear);
                Log.v(TAG, "month: " + mMonth);
                Log.v(TAG, "day: " + mDayOfMonth);
                Log.v(TAG, "hour: " + mHour);
                Log.v(TAG, "minute: " + mMinute);

            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actions_save:

                saveReminder();

                break;

            case R.id.actions_alarm:

                mDateDialog.show();

                break;
        }
        return true;
    }

    private void saveReminder() {
        if (!isTitleEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append(mYear).append("+").append(mMonth).append("+")
                    .append(mDayOfMonth).append("+").append(mHour).append("+").append(mMinute);
            Log.v(TAG, "info = " + builder.toString());
            Intent intent = new Intent(ReminderAddActivity.this, ReminderActivity.class);
            Bundle bundle = new Bundle();
            String title = mTitleEdit.getText().toString();
            String content = mContentEdit.getText().toString();
            if (content.isEmpty())
                content = "无内容";
            bundle.putString("reminderTitle", title);
            bundle.putString("reminderContent", content);
            bundle.putString("reminderTime", builder.toString());
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            ReminderAddActivity.this.finish();
        } else
            Toast.makeText(this, "Title can not be empty!", Toast.LENGTH_SHORT).show();
    }

    private boolean isTitleEmpty() {
        String title = mTitleEdit.getText().toString();
        return title.isEmpty();
    }

}
