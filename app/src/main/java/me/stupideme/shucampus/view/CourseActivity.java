package me.stupideme.shucampus.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

import java.util.List;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.model.CourseBean;
import me.stupideme.shucampus.presenter.CoursePresenter;
import me.stupideme.shucampus.view.custom.StupidDialog;

public class CourseActivity extends AppCompatActivity implements CourseView, DialogListener {

    private CoursePresenter mPresenter;
    private StupidDialog mDialog;

    RelativeLayout[] layouts = new RelativeLayout[7];
    private int cellWidth;
    private int cellHeight;

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
                mDialog.show();
            }
        });

        mPresenter = CoursePresenter.getInstance(this);

        mDialog = new StupidDialog(this, this);

        layouts[0] = (RelativeLayout) findViewById(R.id.Monday);
        layouts[1] = (RelativeLayout) findViewById(R.id.Tuesday);
        layouts[2] = (RelativeLayout) findViewById(R.id.Wednesday);
        layouts[3] = (RelativeLayout) findViewById(R.id.Thursday);
        layouts[4] = (RelativeLayout) findViewById(R.id.Friday);
        layouts[5] = (RelativeLayout) findViewById(R.id.Saturday);
        layouts[6] = (RelativeLayout) findViewById(R.id.Sunday);
        System.out.println(layouts[6].toString());
        cellWidth = layouts[0].getWidth();
        cellHeight = layouts[0].getHeight() / 13;

        Log.i("width", cellWidth + " " + layouts[0].getWidth());
        Log.i("height", cellHeight + " " + layouts[0].getHeight());

        mPresenter.autoLoadCourses();
    }

    @Override
    public void autoLoad(List<CourseBean> list) {
        for (CourseBean bean : list) {
            createCourse(bean);
        }
    }

    @Override
    public void addCourse(CourseBean model) {
        createCourse(model);
    }

    private void createCourse(final CourseBean model) {

        final TextView tv = new TextView(CourseActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cellWidth, cellHeight * (model.getEnd() - model.getBegin() + 1));
        tv.setY(cellHeight * (model.getBegin() - 1));
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.NO_GRAVITY);

        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mPresenter.removeCourse(model);
                layouts[model.getWeekday()].removeView(tv);
                return true;
            }
        });

        System.out.println(model.getWeekday());
        layouts[model.getWeekday()].addView(tv);
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
