package me.stupideme.shucampus.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.util.StupidTextUtil;

public class CourseAddActivity extends AppCompatActivity {

    private int mCourseColor;
    private int mCourseWeekday;
    private String mCourseBegin;
    private String mCourseEnd;
    private int mCourseMod;
    private String mCourseName;
    private String mCourseLocation;
    private String mCourseTeacher;

    private Spinner mWeekdaySpinner;
    private AppCompatEditText mBeginEdit;
    private AppCompatEditText mEndEdit;
    private AppCompatEditText mNameEdit;
    private AppCompatEditText mLocationEdit;
    private AppCompatEditText mTeacherEdit;
    private Spinner mModSpinner;
    private Button mColorButton;
    private Button mClearButton;
    private Button mSaveEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("编辑课表");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseAddActivity.super.onBackPressed();
            }
        });

        mWeekdaySpinner = (Spinner) findViewById(R.id.spinner_weekday);
        mBeginEdit = (AppCompatEditText) findViewById(R.id.edit_begin);
        mEndEdit = (AppCompatEditText) findViewById(R.id.edit_end);
        mNameEdit = (AppCompatEditText) findViewById(R.id.edit_name);
        mLocationEdit = (AppCompatEditText) findViewById(R.id.edit_location);
        mTeacherEdit = (AppCompatEditText) findViewById(R.id.edit_teacher);
        mModSpinner = (Spinner) findViewById(R.id.spinner_mod);
        mColorButton = (Button) findViewById(R.id.button_color);
        mClearButton = (Button) findViewById(R.id.button_clear);
        mSaveEdit = (Button) findViewById(R.id.button_save);

        mNameEdit.setHint("课程名称");
        mLocationEdit.setHint("上课地点");
        mTeacherEdit.setHint("上课教师");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CourseAddActivity.this,
                R.array.class_weekdays, R.layout.support_simple_spinner_dropdown_item);
        mWeekdaySpinner.setAdapter(adapter);

        mWeekdaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCourseWeekday = i;
                Log.v("CourseAddActivity", "mCourseWeekday = " + i);
                adapterView.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(CourseAddActivity.this,
                R.array.class_mod, R.layout.support_simple_spinner_dropdown_item);
        mModSpinner.setAdapter(adapter1);

        mModSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCourseMod = i;
                Log.v("CourseAddActivity", "mCourseMod = " + i);
                adapterView.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        int[] colors = getResources().getIntArray(R.array.default_rainbow);
        int selectedColor = ContextCompat.getColor(this, R.color.azure);
        final ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_dialog,
                colors,
                selectedColor,
                5,
                ColorPickerDialog.SIZE_SMALL);

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                mCourseColor = color;
                Log.v("CourseAddActivity", "mCourseColor = " + mCourseColor);
                mColorButton.setBackgroundColor(color);
            }
        });

        mColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show(getFragmentManager(), "color_dialog");
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBeginEdit.setText(null);
                mEndEdit.setText(null);
                mNameEdit.setText(null);
                mLocationEdit.setText(null);
                mTeacherEdit.setText(null);
                mBeginEdit.setHint("1");
                mEndEdit.setHint("13");
                mNameEdit.setHint("课程名称");
                mLocationEdit.setHint("上课地点");
                mTeacherEdit.setHint("上课教师");
                mColorButton.setBackgroundColor(getResources().getColor(R.color.tomato));
            }
        });

        mSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isComplete()) {

                    mCourseBegin = mBeginEdit.getText().toString();
                    mCourseEnd = mEndEdit.getText().toString();
                    mCourseName = mNameEdit.getText().toString();
                    mCourseLocation = mLocationEdit.getText().toString();
                    mCourseTeacher = mTeacherEdit.getText().toString();

                    String info = mCourseWeekday + "+" + mCourseBegin + "+" + mCourseEnd + "+"
                            + mCourseName + "+" + mCourseLocation + "+" + mCourseTeacher + "+"
                            + mCourseMod + "+" + mCourseColor;

                    Log.v("CourseAddActivity", "Course info = " + info);

                    Intent intent = new Intent(CourseAddActivity.this, CourseActivity.class);
                    intent.putExtra("info", info);
                    setResult(RESULT_OK, intent);
                    CourseAddActivity.this.finish();
                } else {
                    Toast.makeText(CourseAddActivity.this, "填写的信息缺失或者不正确！", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean isComplete() {
        String beginStr = mBeginEdit.getText().toString();
        if (beginStr.isEmpty())
            return false;
        if (!StupidTextUtil.isDigitalInRange(1, 13, beginStr))
            return false;

        String endStr = mEndEdit.getText().toString();
        if (endStr.isEmpty())
            return false;
        if (!StupidTextUtil.isDigitalInRange(1, 13, endStr))
            return false;

        int beginNum = Integer.parseInt(beginStr);
        int endNum = Integer.parseInt(endStr);
        if (beginNum > endNum)
            return false;
        if (TextUtils.isEmpty(mNameEdit.getText().toString()))
            return false;
        if (TextUtils.isEmpty(mLocationEdit.getText().toString()))
            return false;
        if (TextUtils.isEmpty(mTeacherEdit.getText().toString()))
            return false;

        if (mCourseColor == 0) {
            mCourseColor = Color.parseColor("#d50000");
            Log.v("CourseAddActivity", "mCourseColor = " + mCourseColor);
        }

        return true;
    }

}
