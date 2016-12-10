package me.stupideme.shucampus.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.adapter.SpaceItemDecoration;
import me.stupideme.shucampus.model.ReminderBean;
import me.stupideme.shucampus.presenter.ReminderPresenter;
import me.stupideme.shucampus.view.custom.RecyclerItemCallback;

public class ReminderActivity extends AppCompatActivity implements ReminderView {

    private static final String TAG = "ReminderActivity";
    private static final int REQUEST_CODE_ADD_REMINDER = 0x20;
    private static final int REQUEST_CODE_SET_REMINDER_ALARM = 0x30;
    private static final int REQUEST_CODE_CANCEL_REMINDER_ALARM = 0x40;
    private RecyclerView mRecyclerView;
    private ReminderRecyclerAdapter2 mAdapter;
    private ReminderPresenter mPresenter;
    private List<ReminderBean> mItems = new ArrayList<>();

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("提醒");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderActivity.super.onBackPressed();
            }
        });

//        ReminderBean model = new ReminderBean();
//        model.setTitle("hello");
//        model.setContent("Hello,World.............................");
//        for (int i = 0; i < 10; i++) {
//            mItems.add(i, model);
//        }

        mAdapter = new ReminderRecyclerAdapter2(mItems);
        mRecyclerView = (RecyclerView) findViewById(R.id.reminder_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFab.show();
            }
        });
        ItemTouchHelper.Callback callback = new RecyclerItemCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);

        mFab = (FloatingActionButton) findViewById(R.id.fab_add);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReminderActivity.this, ReminderAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_REMINDER);
            }
        });

        mPresenter = ReminderPresenter.getInstance(this);
        mPresenter.autoLoadReminder();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void autoLoadReminder(List<ReminderBean> list) {
        mItems.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addReminder(ReminderBean bean) {
        mItems.add(0, bean);
        mAdapter.notifyDataSetChanged();
        Log.v(TAG, "add reminder");
    }

    @Override
    public void removeReminder(ReminderBean bean) {

    }

    @Override
    public void setReminderAlarm(long millis) {
//        Calendar calendar = Calendar.getInstance();
//        long millisNow = calendar.getTimeInMillis();
//        if (millis > millisNow) {
//            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//            Intent intent = new Intent(this, ReminderAlarmReceiver.class);
//            intent.setAction("me.stupidme.shucampus.ACTION_SET_REMINDER_ALARM_" + millis);
//            PendingIntent pi = PendingIntent.getBroadcast(this, REQUEST_CODE_SET_REMINDER_ALARM, intent, 0);
//            am.set(AlarmManager.RTC_WAKEUP, millis, pi);
//        }
    }

    @Override
    public void cancelReminderAlarm(long millis) {
//        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent intent = new Intent(this, ReminderAlarmReceiver.class);
//        intent.setAction("me.stupidme.shucampus.ACTION_SET_REMINDER_ALARM_" + millis);
//        PendingIntent pi = PendingIntent.getBroadcast(this, REQUEST_CODE_CANCEL_REMINDER_ALARM, intent, 0);
//        am.cancel(pi);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ADD_REMINDER:
                switch (resultCode) {
                    case RESULT_OK:
                        Bundle bundle = data.getExtras();
                        String reminderTitle = bundle.getString("reminderTitle");
                        String reminderContent = bundle.getString("reminderContent");
                        String reminderTime = bundle.getString("reminderTime");
                        StringBuilder builder = new StringBuilder();
                        builder.append(reminderTitle).append("%")
                                .append(reminderContent).append("%")
                                .append(reminderTime);
                        mPresenter.addReminder(builder.toString());
                        Log.v(TAG, "info : " + builder.toString());

                        break;
                }
                break;
        }
    }

    private class ReminderRecyclerAdapter2 extends RecyclerView.Adapter<ReminderRecyclerAdapter2.ViewHolder>
            implements ReminderItemTouchListener {

        private List<ReminderBean> mItems;

        ReminderRecyclerAdapter2(List<ReminderBean> list) {
            mItems = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            ReminderBean model = mItems.get(position);
            holder.title.setText(model.getTitle());
            holder.content.setText(model.getContent());
            holder.time.setText(model.getTime());
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mItems, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mItems, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onItemDismiss(int position) {
            mItems.remove(position);
            notifyItemRemoved(position);
            mPresenter.remove(mItems.get(position));
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView title;
            private TextView content;
            private TextView time;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.reminder_title);
                content = (TextView) itemView.findViewById(R.id.reminder_content);
                time = (TextView) itemView.findViewById(R.id.reminder_time);
            }
        }
    }
}
