package me.stupideme.shucampus.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.stupideme.shucampus.R;

public class NotifyCommentFragment extends Fragment {

    public NotifyCommentFragment() {
        // Required empty public constructor
    }

    public static NotifyCommentFragment newInstance(String param1, String param2) {
        NotifyCommentFragment fragment = new NotifyCommentFragment();
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
        return inflater.inflate(R.layout.fragment_notify_comment, container, false);
    }

}
