package me.stupideme.shucampus.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.model.CourseBean;
import me.stupideme.shucampus.presenter.CoursePresenter;
import me.stupideme.shucampus.view.custom.StupidDialog;

public class CourseActivity extends AppCompatActivity implements CourseView, DialogListener {

    private static final String TAG = "CourseActivity";
    private static final int REQUEST_CODE_ADD_COURSE = 0x01;
    private CoursePresenter mPresenter;
    private StupidDialog mDialog;


    private final List<RelativeLayout> layouts = new ArrayList<>(7);

    private RelativeLayout layout0;
    private RelativeLayout layout1;
    private RelativeLayout layout2;
    private RelativeLayout layout3;
    private RelativeLayout layout4;
    private RelativeLayout layout5;
    private RelativeLayout layout6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("课程表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseActivity.this.finish();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.show_class_scroll_col);
        fab.attachToScrollView(scrollView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseActivity.this, CourseAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_COURSE);
            }
        });


        layout0 = (RelativeLayout) findViewById(R.id.Monday);
        layout1 = (RelativeLayout) findViewById(R.id.Tuesday);
        layout2 = (RelativeLayout) findViewById(R.id.Wednesday);
        layout3 = (RelativeLayout) findViewById(R.id.Thursday);
        layout4 = (RelativeLayout) findViewById(R.id.Friday);
        layout5 = (RelativeLayout) findViewById(R.id.Saturday);
        layout6 = (RelativeLayout) findViewById(R.id.Sunday);
        layouts.add(layout0);
        layouts.add(layout1);
        layouts.add(layout2);
        layouts.add(layout3);
        layouts.add(layout4);
        layouts.add(layout5);
        layouts.add(layout6);
        Log.v("CourseActivity", "layouts created");

        mPresenter = CoursePresenter.getInstance(this);

        mDialog = new StupidDialog(this, this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.autoLoadCourses();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ADD_COURSE:
                switch (resultCode) {
                    case RESULT_OK:
                        String info = data.getStringExtra("info");
                        mPresenter.addCourse(info);
                        break;
                }
                break;
        }
    }

    @Override
    public void autoLoad(List<CourseBean> list) {
        Log.v(TAG, "list size: " + list.size());
        for (CourseBean bean : list) {
            createCourse(bean);
        }
        Log.v("CourseActivity", "auto load ...");
    }

    @Override
    public void addCourse(CourseBean model) {
        createCourse(model);
    }

    private void createCourse(CourseBean model) {
        TextView textView = new TextView(this);
        textView.setBackgroundColor(model.getColor());
        textView.setText(model.getName() + "\n" + model.getLocation() + "\n" + model.getTeacher());
        textView.setTextColor(Color.WHITE);

        RelativeLayout layout = layouts.get(model.getWeekday());
        int w = layout.getWidth();
        int h = layout.getHeight() / 13;
        Log.v(TAG, "layout w : " + w);
        Log.v(TAG, "layout h : " + h);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(198, 161 * (model.getEnd() - model.getBegin() + 1));
        params.setMargins(0, 161 * (model.getBegin() - 1), 0, 0);
        textView.setLayoutParams(params);
        Log.v(TAG, "margin top : " + h * (model.getBegin() - 1));
        layout.addView(textView);

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public void onCancel() {
        mDialog.dismiss();
    }

    @Override
    public void onSave(String info) {
        mPresenter.addCourse(info);
    }

}
