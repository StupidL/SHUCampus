package me.stupideme.shucampus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.adapter.ClassListViewAdapter;
import me.stupideme.shucampus.db.DBManager;
import me.stupideme.shucampus.model.CourseBean;
import me.stupideme.shucampus.view.CourseAddActivity;

public class CourseEditActivity extends AppCompatActivity {

    private List<CourseBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseEditActivity.super.onBackPressed();
            }
        });

        ListView listView = (ListView) findViewById(R.id.classes_list_view);
        list = DBManager.getInstance(CourseEditActivity.this).getAllClass();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.classes_fab);
        fab.attachToListView(listView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CourseEditActivity.this, CourseAddActivity.class));
            }
        });

        ClassListViewAdapter adapter = new ClassListViewAdapter(CourseEditActivity.this, list);
        listView.setAdapter(adapter);
    }


}
