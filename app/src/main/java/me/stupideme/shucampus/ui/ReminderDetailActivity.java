package me.stupideme.shucampus.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.db.DBManager;
import me.stupideme.shucampus.model.ReminderBean;

public class ReminderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);

        Intent intent = getIntent();
        long id = intent.getLongExtra("reminderId", -1L);

        AppCompatTextView eventTitle = (AppCompatTextView) findViewById(R.id.alarm_title);
        AppCompatTextView eventContent = (AppCompatTextView) findViewById(R.id.alarm_content);

        if (id != -1L) {
            DBManager manager = DBManager.getInstance(ReminderDetailActivity.this);
            ReminderBean model = manager.getReminderModel(id);
            String title = model.getTitle();
            String content = model.getContent();
            eventTitle.setText(title);
            eventContent.setText(content);
            eventTitle.setFocusable(false);
            eventContent.setFocusable(false);
        }
    }
}
