package me.stupideme.shucampus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.adapter.ReminderRecyclerAdapter;
import me.stupideme.shucampus.db.DBManager;
import me.stupideme.shucampus.model.ReminderModel;

public class RemindersFragment extends Fragment {

    private List<ReminderModel> list;
    private ReminderRecyclerAdapter adapter;

    public RemindersFragment() {
        // Required empty public constructor
    }

    public static RemindersFragment newInstance() {
        return new RemindersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        DBManager manager = DBManager.getInstance(getActivity());
        list = manager.getAllReminder();
        adapter = new ReminderRecyclerAdapter(list,R.layout.item_reminder,getActivity());
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), ReminderAddActivity.class), 0x400);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == 0x402) {
            list.clear();
            list = DBManager.getInstance(getActivity()).getAllReminder();
            adapter.notifyDataSetChanged();
        }
    }

}
