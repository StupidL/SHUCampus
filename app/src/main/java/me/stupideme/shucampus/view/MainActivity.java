package me.stupideme.shucampus.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.adapter.SpaceItemDecoration;
import me.stupideme.shucampus.model.StupidEvent;
import me.stupideme.shucampus.presenter.MainPresenter;
import me.stupideme.shucampus.ui.NavProfileActivity;
import me.stupideme.shucampus.ui.NotifyActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {


    private static final int REQUEST_EVENT_ADD = 0x01;
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private MainPresenter mPresenter;
    private List<StupidEvent> mEvents;
    private EventRecyclerAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View view = navigationView.getHeaderView(0);
        View head = view.findViewById(R.id.imageView);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NavProfileActivity.class));
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventAddActivity.class);
                startActivityForResult(intent, REQUEST_EVENT_ADD);
            }
        });

        mEvents = new ArrayList<>();
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        fab.attachToRecyclerView(mRecyclerView);

        mPresenter = MainPresenter.getInstance(this);
        mAdapter = new EventRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.autoRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EVENT_ADD) {
            if (resultCode == RESULT_OK) {
                addEvent(data);
            }
        }

    }

    private void addEvent(Intent data) {
        Bundle bundle = data.getExtras();
        String title = bundle.getString("title");
        String phone = bundle.getString("phone");
        String content = bundle.getString("content");

        final StupidEvent event = new StupidEvent();
        event.setName("Allen");
        event.setTime("2016-12-07");
        event.setContent(title + "\n" + content);
        event.setPhone(phone);

        event.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    mEvents.add(0, event);
                    mAdapter.notifyDataSetChanged();
                    Log.v("MainActivity", "saved...");
                } else {
                    Log.v("MainActivity", e.toString());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }
        if (id == R.id.action_notification) {
            startActivity(new Intent(MainActivity.this, NotifyActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_hot) {
            mDrawerLayout.closeDrawers();
        } else if (id == R.id.nav_mark) {
            startActivity(new Intent(MainActivity.this, MarkActivity.class));

        } else if (id == R.id.nav_class) {

            startActivity(new Intent(MainActivity.this, CourseActivity.class));

        } else if (id == R.id.nav_reminder) {
            startActivity(new Intent(MainActivity.this, ReminderActivity.class));
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("WhereFrom", "MainActivity");
            startActivity(intent);
            MainActivity.this.finish();
        }

        return true;
    }


    @Override
    public void onStartRefresh() {
        mSwipeLayout.setRefreshing(true);
    }

    @Override
    public void onRefreshSuccess(List<StupidEvent> list) {
        mEvents.clear();
        mEvents.addAll(list);
        mAdapter.notifyDataSetChanged();
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshFailed() {
        mSwipeLayout.setRefreshing(false);
        Snackbar.make(fab, "Refresh failed!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onStartLoadMore() {

    }

    @Override
    public void onLoadMoreSuccess(List<StupidEvent> list) {
        mEvents.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreFailed() {

    }

    @Override
    public void showNoMore() {

    }

    @Override
    public void onLogout() {

    }

    private class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {

        EventRecyclerAdapter() {

        }

        @Override
        public EventRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_event, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(EventRecyclerAdapter.ViewHolder holder, int position) {
            StupidEvent event = mEvents.get(position);
            holder.name.setText(event.getName());
            holder.time.setText(event.getTime());
            holder.content.setText(event.getContent());
            holder.phone.setText(event.getPhone());
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView name;
            private TextView time;
            private TextView content;
            private TextView phone;

            private CircleImageView image;
            private ImageButton mark;
            private ImageButton comment;
            private ImageButton share;

            public ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.head_name);
                time = (TextView) itemView.findViewById(R.id.head_time);
                content = (TextView) itemView.findViewById(R.id.content);
                phone = (TextView) itemView.findViewById(R.id.foot_phone);

                image = (CircleImageView) findViewById(R.id.head_image);
                mark = (ImageButton) findViewById(R.id.foot_mark);
                comment = (ImageButton) findViewById(R.id.foot_comment);
                share = (ImageButton) findViewById(R.id.foot_share);
            }
        }
    }

}
