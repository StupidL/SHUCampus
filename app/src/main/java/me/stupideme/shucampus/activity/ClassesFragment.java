package me.stupideme.shucampus.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.util.List;

import me.stupideme.shucampus.CampusApp;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.course.AddClassActivity;
import me.stupideme.shucampus.course.ClassModel;

public class ClassesFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private RelativeLayout[] layouts;
    private int width, height;
    private List<ClassModel> list;

    public ClassesFragment() {
        // Required empty public constructor
    }

    public static ClassesFragment newInstance(String param1, String param2) {
        ClassesFragment fragment = new ClassesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            width = getArguments().getInt("width");
            height = getArguments().getInt("height");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_show_class, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.show_class_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), AddClassActivity.class), 0x200);
            }
        });
        layouts = new RelativeLayout[7];
        layouts[0] = (RelativeLayout) view.findViewById(R.id.Monday);
        layouts[1] = (RelativeLayout) view.findViewById(R.id.Tuesday);
        layouts[2] = (RelativeLayout) view.findViewById(R.id.Wednesday);
        layouts[3] = (RelativeLayout) view.findViewById(R.id.Thursday);
        layouts[4] = (RelativeLayout) view.findViewById(R.id.Friday);
        layouts[5] = (RelativeLayout) view.findViewById(R.id.Saturday);
        layouts[6] = (RelativeLayout) view.findViewById(R.id.Sunday);

        list = CampusApp.manager.getAllClass();
        createAllClass(list);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        createAllClass(CampusApp.manager.getAllClass());
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
