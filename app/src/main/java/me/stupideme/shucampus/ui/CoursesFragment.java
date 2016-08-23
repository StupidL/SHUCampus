package me.stupideme.shucampus.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

import java.util.List;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.model.ClassModel;
import me.stupideme.shucampus.db.DBManager;

public class CoursesFragment extends Fragment {


    // private OnFragmentInteractionListener mListener;
    private RelativeLayout[] layouts;
    private int width, height;
    private List<ClassModel> list;

    public CoursesFragment() {
        // Required empty public constructor
    }

    public static CoursesFragment newInstance() {
        return new CoursesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.show_class_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), CourseEditActivity.class), 0x200);
            }
        });

        ObservableScrollView scrollView = (ObservableScrollView) view.findViewById(R.id.show_class_scroll_col);
        fab.attachToScrollView(scrollView);

        layouts = new RelativeLayout[7];
        layouts[0] = (RelativeLayout) view.findViewById(R.id.Monday);
        layouts[1] = (RelativeLayout) view.findViewById(R.id.Tuesday);
        layouts[2] = (RelativeLayout) view.findViewById(R.id.Wednesday);
        layouts[3] = (RelativeLayout) view.findViewById(R.id.Thursday);
        layouts[4] = (RelativeLayout) view.findViewById(R.id.Friday);
        layouts[5] = (RelativeLayout) view.findViewById(R.id.Saturday);
        layouts[6] = (RelativeLayout) view.findViewById(R.id.Sunday);

        width = layouts[0].getWidth();
        height = layouts[0].getHeight() / 13;

        //list = CampusApp.app.manager.getAllClass();
        DBManager manager = DBManager.getInstance(getActivity());
        list = manager.getAllClass();
            createAllClass(list);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0x201) {
            freshClasses();
        }
    }

    private void freshClasses() {
        for (RelativeLayout layout : layouts) {
            layout.removeAllViews();
        }

        createAllClass(DBManager.getInstance(getActivity()).getAllClass());
    }

    private void createAllClass(List<ClassModel> list) {
        if (!list.isEmpty()) {
            for (ClassModel model : list) {

                createClass(model);

            }
        }
    }

    private void createClass(ClassModel model) {

        TextView tv = new TextView(getActivity().getBaseContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height * (model.getEnd() - model.getBegin() + 1));
        tv.setY(height * (model.getBegin() - 1));
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.NO_GRAVITY);
        layouts[model.getWeekday() - 1].addView(tv);

    }


}
