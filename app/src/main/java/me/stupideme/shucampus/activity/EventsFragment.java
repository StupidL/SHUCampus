package me.stupideme.shucampus.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.adapter.RecyclerViewAdapter;
import me.stupideme.shucampus.model.Event;

public class EventsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance(String param1, String param2) {
        EventsFragment fragment = new EventsFragment();
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
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        BmobQuery<Event> query = new BmobQuery<>();
        query.setLimit(100);
        query.addWhereMatches("objectId", "^[a-z0-9]+");
        query.findObjects(new FindListener<Event>() {
            @Override
            public void done(List<Event> list, BmobException e) {
                if (e == null) {
                    recyclerView.setAdapter(new RecyclerViewAdapter(list));
                } else {
                    Snackbar.make(fab, "请求数据错误", Snackbar.LENGTH_LONG)
                            .setAction("OK", null).show();
                }
            }
        });

        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),EditEventActivity.class));
            }
        });


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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
