package me.stupideme.shucampus.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import me.stupideme.shucampus.CampusApp;
import me.stupideme.shucampus.R;

public class EditClassActivity extends AppCompatActivity {

    private List<ClassModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditClassActivity.this.finish();
            }
        });

        ListView listView = (ListView) findViewById(R.id.classes_list_view);
        list = CampusApp.manager.getAllClass();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.classes_fab);
        fab.attachToListView(listView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditClassActivity.this, AddClassActivity.class));
            }
        });

        ClassListViewAdapter adapter = new ClassListViewAdapter(EditClassActivity.this, list);
        listView.setAdapter(adapter);
    }


}
