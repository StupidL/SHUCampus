package me.stupideme.shucampus.remind;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import me.stupideme.shucampus.CampusApp;
import me.stupideme.shucampus.R;

public class RemindersFragment extends Fragment {

    private List<ReminderModel> list;
    private ReminderListViewAdapter adapter;

    public RemindersFragment() {
        // Required empty public constructor
    }

    public static RemindersFragment newInstance(String param1, String param2) {
        RemindersFragment fragment = new RemindersFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);
        ListView listView = (ListView) view.findViewById(R.id.reminders_list_view);
        list = CampusApp.manager.getAllReminder();
        adapter = new ReminderListViewAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), AddReminderActivity.class), 0x400);
            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == 0x402) {
            //add an reminder item
            list.clear();
            list = CampusApp.manager.getAllReminder();
            adapter.notifyDataSetChanged();
        }
    }

}
