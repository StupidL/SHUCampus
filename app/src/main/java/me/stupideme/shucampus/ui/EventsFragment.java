package me.stupideme.shucampus.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.melnykov.fab.FloatingActionButton;
import me.stupideme.shucampus.CampusApp;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.adapter.RecyclerViewAdapter;
import me.stupideme.shucampus.adapter.SpaceItemDecoration;

public class EventsFragment extends Fragment {

    private RecyclerViewAdapter mAdapter;
    private EventTask mEventTask;

    public EventsFragment() {
    }

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RecyclerViewAdapter(CampusApp.eventList);
        mEventTask = new EventTask();
        mEventTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setProgressViewEndTarget(true,100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CampusApp.eventList.clear();
                new EventTask().execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20));
        recyclerView.setAdapter(mAdapter);

        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EventEditActivity.class));
            }
        });
        return view;
    }

    public class EventTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            //TODO: get data from server by bmob sdk and update CampusApp.eventList


            return null;
        }

        @Override
        public void onPostExecute(Void result){
            mAdapter.notifyDataSetChanged();
        }
    }


}
