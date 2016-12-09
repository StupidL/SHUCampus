package me.stupideme.shucampus.view;

import android.content.Intent;
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
    private int cellWidth;
    private int cellHeight;

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

        mPresenter.autoLoadCourses();
    }

    @Override
    public void onWindowFocusChanged(boolean changed) {
        super.onWindowFocusChanged(changed);

        cellWidth = layouts.get(0).getWidth();
        cellHeight = layouts.get(0).getHeight() / 13;

        Log.i("width", cellWidth + " " + layouts.get(0).getWidth());
        Log.i("height", cellHeight + " " + layouts.get(0).getHeight());
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

    private void createCourse(final CourseBean model) {

        final TextView textView = new TextView(this);
//
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cellWidth, cellHeight * (model.getEnd() - model.getBegin() + 1));
//        textView.setLayoutParams(params);
//        textView.setX(0);
//        textView.setY(cellHeight * (model.getBegin() - 1));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cellWidth, cellHeight * (model.getEnd() - model.getBegin() + 1));
        params.width = cellWidth;
        params.height = cellHeight * (model.getEnd() - model.getBegin() + 1);
        textView.setId((int) model.getClassId());
        textView.setLayoutParams(params);
        textView.setY(cellHeight * (model.getBegin() - 1));
        textView.setGravity(Gravity.NO_GRAVITY);

        String text = model.getName() + "\n" + model.getLocation() + "\n" + model.getMod();
        textView.setText(text);
        textView.setBackgroundColor(model.getColor());

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mPresenter.removeCourse(model);
                layouts.get(0).removeView(textView);
                layouts.get(0).invalidate();
                return false;
            }
        });

        Log.v(TAG, "weekday: " + model.getWeekday());
        layouts.get(model.getWeekday()).addView(textView);
        Log.v("CourseActivity", "Created a TextView...");
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
