package me.stupideme.shucampus.remind;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.db.DBManager;

public class RemindersActivity extends AppCompatActivity {

    private List<ReminderModel> list;
    private ReminderRecyclerAdapter adapter;
    private DBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemindersActivity.this.finish();
            }
        });


        manager = DBManager.getInstance(RemindersActivity.this);

        list = manager.getAllReminder();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(RemindersActivity.this, AddReminderActivity.class);
                intent.putExtra("title", "");
                intent.putExtra("content", "");
                intent.putExtra("id", 0L);
                startActivityForResult(intent, 0x100);
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reminders_activity_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ReminderRecyclerAdapter(list, R.layout.item_reminder, RemindersActivity.this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == 0x102) {
            list.clear();
            list = manager.getAllReminder();
            adapter.notifyDataSetChanged();
        }
    }

}
