package me.stupideme.shucampus.ui;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.model.CourseBean;
import me.stupideme.shucampus.db.DBManager;

public class CourseAddActivity extends AppCompatActivity {
    private CourseBean model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        setContentView(R.layout.activity_add_course);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_class_toolbar);
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

        model = new CourseBean();

        final Spinner weekday = (Spinner) findViewById(R.id.spinner_weekday);
        final AppCompatEditText begin = (AppCompatEditText) findViewById(R.id.edit_begin);
        final AppCompatEditText end = (AppCompatEditText) findViewById(R.id.edit_end);
        final AppCompatEditText name = (AppCompatEditText) findViewById(R.id.edit_name);
        final AppCompatEditText location = (AppCompatEditText) findViewById(R.id.edit_location);
        final AppCompatEditText teacher = (AppCompatEditText) findViewById(R.id.edit_teacher);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_mod);
        final Button colorButton = (Button) findViewById(R.id.button_color);
        Button clear = (Button) findViewById(R.id.button_clear);
        final Button save = (Button) findViewById(R.id.button_save);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CourseAddActivity.this,
                R.array.class_weekdays, R.layout.support_simple_spinner_dropdown_item);
        weekday.setAdapter(adapter);

        weekday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                model.setWeekday(i);
                System.out.println("model weekday" + model.getWeekday());
                adapterView.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(CourseAddActivity.this,
                R.array.class_mod, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                model.setMod(i);
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
                model.setColor(color);
                colorButton.setBackgroundColor(color);
            }
        });

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show(getFragmentManager(), "color_dialog");
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                begin.setText(null);
                end.setText(null);
                name.setText(null);
                location.setText(null);
                teacher.setText(null);
                begin.setHint("1");
                end.setHint("13");
                name.setHint("课程名称");
                location.setHint("上课地点");
                teacher.setHint("上课教师");
                colorButton.setBackgroundColor(0x808080);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Integer.parseInt(begin.getText().toString()) > 13 || Integer.parseInt(end.getText().toString()) > 13) {
                    Snackbar.make(save, "课程不能大于13", Snackbar.LENGTH_SHORT).show();
                } else {

                    model.setBegin(Integer.parseInt(begin.getText().toString()));
                    model.setEnd(Integer.parseInt(end.getText().toString()));
                    model.setName("@" + name.getText().toString() + "\n");
                    model.setLocation("@" + location.getText().toString() + "\n");
                    model.setTeacher("@" + teacher.getText().toString() + "\n");

                    DBManager.getInstance(CourseAddActivity.this).insertClass(model);
                }
                CourseAddActivity.this.finish();
            }
        });

    }

}
