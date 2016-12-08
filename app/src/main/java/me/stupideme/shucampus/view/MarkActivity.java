package me.stupideme.shucampus.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.model.MarkEvent;
import me.stupideme.shucampus.presenter.MarkPresenter;

public class MarkActivity extends AppCompatActivity implements MarkView{

    private RecyclerView mRecyclerView;
    private List<MarkEvent> mMarkEvents;

    private MarkPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMarkEvents = new ArrayList<>();
        mPresenter = MarkPresenter.getInstance(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.mark_recycler_view);

    }

    @Override
    public void remove() {

    }

    private class MarkRecyclerAdapter extends RecyclerView.Adapter<MarkRecyclerAdapter.ViewHolder>{

        @Override
        public MarkRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(MarkRecyclerAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
