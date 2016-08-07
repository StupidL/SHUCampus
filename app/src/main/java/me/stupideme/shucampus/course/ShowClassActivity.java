package me.stupideme.shucampus.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import me.stupideme.shucampus.CampusApp;
import me.stupideme.shucampus.R;

public class ShowClassActivity extends AppCompatActivity {

    private boolean isFirst = true;
    private int gridWidth;
    private int gridHeight;
    private List<ClassModel> list;
    private RelativeLayout tmpLayout;
    private RelativeLayout[] layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowClassActivity.super.onBackPressed();
            }
        });

        layouts = new RelativeLayout[7];
        layouts[0] = (RelativeLayout) findViewById(R.id.Monday);
        layouts[1] = (RelativeLayout) findViewById(R.id.Tuesday);
        layouts[2] = (RelativeLayout) findViewById(R.id.Wednesday);
        layouts[3] = (RelativeLayout) findViewById(R.id.Thursday);
        layouts[4] = (RelativeLayout) findViewById(R.id.Friday);
        layouts[5] = (RelativeLayout) findViewById(R.id.Saturday);
        layouts[6] = (RelativeLayout) findViewById(R.id.Sunday);
        tmpLayout = layouts[0];

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.show_class_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowClassActivity.this, AddClassActivity.class));
            }
        });

        list = CampusApp.manager.getAllClass();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst) {
            isFirst = false;
            gridWidth = tmpLayout.getWidth();
            gridHeight = tmpLayout.getHeight() / 13;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_class, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.show_class_edit) {
            startActivity(new Intent(ShowClassActivity.this, ClassesActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        list.clear();
        for (RelativeLayout l : layouts) {
            l.removeAllViews();
        }
        list = CampusApp.manager.getAllClass();
        createAllClass(list);
    }

    private void createAllClass(List<ClassModel> list) {
        if (!list.isEmpty()) {
            for (ClassModel model : list) {

                createClass(model);

            }
        }
    }

    private void createClass(ClassModel model) {

        gridWidth = tmpLayout.getWidth();
        gridHeight = tmpLayout.getHeight() / 13;

        TextView tv = new TextView(ShowClassActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, gridHeight * (model.getEnd() - model.getBegin() + 1));
        tv.setY(gridHeight * (model.getBegin() - 1));
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.NO_GRAVITY);
        layouts[model.getWeekday() - 1].addView(tv);

    }
}
