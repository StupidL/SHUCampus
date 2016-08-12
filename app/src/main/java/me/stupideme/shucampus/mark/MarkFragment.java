package me.stupideme.shucampus.mark;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.event.EventsRecyclerViewAdapter;
import me.stupideme.shucampus.model.Event;

public class MarkFragment extends Fragment {

    private RecyclerView recyclerView;

    public MarkFragment() {
        // Required empty public constructor
    }

    public static MarkFragment newInstance(String param1, String param2) {
        MarkFragment fragment = new MarkFragment();
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
        View view = inflater.inflate(R.layout.fragment_mark, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.mark_recycler_view);
        BmobQuery<Event> query = new BmobQuery<>();

        //query.addWhereEqualTo("")         //important!!!need to find those events that marked by current user.

        query.findObjects(new FindListener<Event>() {
            @Override
            public void done(List<Event> list, BmobException e) {
                if (e == null) {
                    recyclerView.setAdapter(new EventsRecyclerViewAdapter(list));
                }
            }
        });


        return view;
    }

}
