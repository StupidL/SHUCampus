package me.stupideme.shucampus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.db.DBManager;
import me.stupideme.shucampus.model.ReminderModel;
import me.stupideme.shucampus.ui.ReminderAlarmReceiver;

/**
 * Created by StupidL on 2016/8/11.
 */

public class ReminderListViewAdapter extends BaseAdapter {

    private List<ReminderModel> list;
    private Context mContext;

    public ReminderListViewAdapter(Context context, List<ReminderModel> list) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_reminder, null);
            viewHolder = new ViewHolder();
            viewHolder.delete = (ImageButton) convertView.findViewById(R.id.reminder_item_delete);
            viewHolder.title = (TextView) convertView.findViewById(R.id.reminder_title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.reminder_content);
            viewHolder.time = (TextView) convertView.findViewById(R.id.reminder_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.content.setText(list.get(position).getContent());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(list.get(position).getId());
        viewHolder.time.setText(calendar.getTime().toString());
        final int pos = position;
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(pos);
                notifyDataSetChanged();
                DBManager.getInstance(mContext).deleteReminder(list.get(pos).getId());

                Intent intent = new Intent(mContext,ReminderAlarmReceiver.class);
                intent.setAction("me.stupidme.action.UPDATE_ALARM");
                mContext.sendBroadcast(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        public ImageButton delete;
        public TextView title;
        public TextView content;
        public TextView time;
    }
}
