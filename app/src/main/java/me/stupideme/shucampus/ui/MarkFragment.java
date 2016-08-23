package me.stupideme.shucampus.ui;


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
import me.stupideme.shucampus.CampusApp;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.adapter.RecyclerViewAdapter;
import me.stupideme.shucampus.adapter.SpaceItemDecoration;

public class MarkFragment extends Fragment {

    private RecyclerViewAdapter mAdapter;
    private MarkedEventTask mMarkedEventTask;

    public MarkFragment() {
    }

    public static MarkFragment newInstance() {
        return new MarkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RecyclerViewAdapter(CampusApp.markedEventList);
        mMarkedEventTask = new MarkedEventTask();
        checkUpdate();
    }

    private void checkUpdate() {
        if(CampusApp.hasMaarkedEventsCHanded){
            CampusApp.hasMaarkedEventsCHanded = false;
            CampusApp.markedEventList.clear();
            mMarkedEventTask.execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mark, container, false);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setProgressViewEndTarget(true,100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CampusApp.eventList.clear();
                mMarkedEventTask.execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mark_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    public class MarkedEventTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            //TODO: get data from server by bmob sdk and update Campus.MarkedEventList

            return null;
        }

        @Override
        public void onPostExecute(Void result){
            mAdapter.notifyDataSetChanged();
        }
    }


}
