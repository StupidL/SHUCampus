package me.stupideme.shucampus.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import me.stupideme.shucampus.R;

public class NotifyChatFragment extends Fragment {

    public NotifyChatFragment() {
        // Required empty public constructor
    }

    public static NotifyChatFragment newInstance(String param1, String param2) {
        NotifyChatFragment fragment = new NotifyChatFragment();
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
        View view = inflater.inflate(R.layout.fragment_notify_chat, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_view);

        return view;
    }

}
