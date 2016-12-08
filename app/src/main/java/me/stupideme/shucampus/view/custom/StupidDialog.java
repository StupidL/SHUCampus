package me.stupideme.shucampus.view.custom;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.view.DialogListener;

/**
 * Created by StupidL on 2016/12/8.
 */

public class StupidDialog extends AlertDialog implements View.OnClickListener{

    private Spinner weekday;
    private TextView begin;
    private TextView end;
    private TextView name;
    private TextView location;
    private TextView teacher;
    private Spinner mod;

    private int mWeekdayPos;
    private int mModPos;

//    private Button delete;
    private Button cancel;
    private Button save;

    private DialogListener mListener;

    public StupidDialog(Context context, DialogListener listener) {
        super(context);

        mListener = listener;
        setContentView(R.layout.dialog_add_course);

        weekday = (Spinner) findViewById(R.id.spinner_weekday);
        begin = (TextView) findViewById(R.id.text_begin);
        end = (TextView) findViewById(R.id.text_end);
        name = (TextView) findViewById(R.id.text_class_name);
        location = (TextView) findViewById(R.id.text_class_location);
        teacher = (TextView) findViewById(R.id.text_class_teacher);
        mod = (Spinner) findViewById(R.id.text_mod);

//        delete = (Button) findViewById(R.id.delete);
        cancel = (Button) findViewById(R.id.cancel);
        save= (Button) findViewById(R.id.save);

//        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);

        weekday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mWeekdayPos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mModPos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void setWeekdaySelection(int position) {
        weekday.setSelection(position);
    }

    public int getWeekdayPosition() {
        return mWeekdayPos;
    }

    public void setModSelection(int position) {
        mod.setSelection(position);
    }

    public int getModPos() {
        return mModPos;
    }

    public void setBegin(int b) {
        begin.setText(b + "");
    }

    public int getBegin() {
        String txt = begin.getText().toString();
        if (txt.isEmpty())
            return 0;
        int val = Integer.parseInt(txt);
        if (val < 0 || val > 13)
            return 0;
        return val;
    }

    public void setEnd(int e) {
        end.setText(e + "");
    }

    public int getEnd() {
        String txt = begin.getText().toString();
        if (txt.isEmpty())
            return 0;
        int val = Integer.parseInt(txt);
        if (val < 1 || val > 13)
            return 0;
        return val;
    }

    public void setName(String n) {
        name.setText(n);
    }

    public String getName() {
        return name.getText().toString();
    }

    public void setLocation(String loc) {
        location.setText(loc);
    }

    public String getLocation() {
        return location.getText().toString();
    }

    public void setTeacher(String t) {
        teacher.setText(t);
    }

    public String getTeacher() {
        return teacher.getText().toString();
    }

    public void setBeginErr(String err) {
        begin.setError(err);
    }

    public void setEndErr(String err) {
        end.setError(err);
    }

    @Override
    public String toString() {
        return mWeekdayPos + "+" + begin + "+" + end + "+" + mModPos + "+" + name + "+" + location + "+" + teacher;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
//            case R.id.delete:
//                break;
            case R.id.cancel:
                mListener.onCancel();
                break;
            case R.id.save:
                mListener.onSave(this.toString());
                break;
        }
    }
}
