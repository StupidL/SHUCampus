package me.stupideme.shucampus.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.stupideme.shucampus.R;

public class NotifyReferenceFragment extends Fragment {

    public NotifyReferenceFragment() {
        // Required empty public constructor
    }

    public static NotifyReferenceFragment newInstance(String param1, String param2) {
        NotifyReferenceFragment fragment = new NotifyReferenceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notify_reference, container, false);
    }

}
