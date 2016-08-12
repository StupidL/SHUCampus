package me.stupideme.shucampus.notify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.stupideme.shucampus.R;

public class CommitFragment extends Fragment {

    public CommitFragment() {
        // Required empty public constructor
    }

    public static CommitFragment newInstance(String param1, String param2) {
        CommitFragment fragment = new CommitFragment();
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
        return inflater.inflate(R.layout.fragment_commit, container, false);
    }

}
