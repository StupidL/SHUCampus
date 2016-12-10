package me.stupideme.shucampus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.db.DBManager;
import me.stupideme.shucampus.model.ReminderBean;
import me.stupideme.shucampus.ui.ReminderAlarmReceiver;
import me.stupideme.shucampus.ui.ReminderDetailActivity;

/**
 * Created by 56211 on 2016/8/6.
 * This adapter is unused now because the ReminderListViewAdapter is in use.
 * It will be used to replace ReminderListViewAdapter someday.
 */

public class ReminderRecyclerAdapter extends RecyclerView.Adapter<ReminderRecyclerAdapter.ViewHolder> {

    private List<ReminderBean> dataSet;
    private int itemLayout;
    private Calendar calendar;
    private Context context;

    public ReminderRecyclerAdapter(List<ReminderBean> dataSet, int itemLayout, Context context) {
        this.dataSet = dataSet;
        this.itemLayout = itemLayout;
        calendar = Calendar.getInstance();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ReminderBean model = dataSet.get(position);
        holder.title.setText(model.getTitle());
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                remove(model);
//            }
//        });
        holder.content.setText(model.getContent());
        calendar.setTimeInMillis(model.getId());
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String time = year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分";
        holder.time.setText(time);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ReminderDetailActivity.class);
                intent.putExtra("title",model.getTitle());
                intent.putExtra("id",model.getId());
                intent.putExtra("content",model.getContent());
                context.startActivity(intent);
            }
        });
    }

    public void add(ReminderBean model, int position){

    }

    public void remove(ReminderBean model){
        int position = dataSet.indexOf(model);
        DBManager manager = DBManager.getInstance(context);
        manager.deleteReminder(model.getId());
        manager.deleteAlarm(model.getId());
        Intent intent = new Intent(context,ReminderAlarmReceiver.class);
        intent.setAction("me.stupidme.action.UPDATE_ALARM");
        context.sendBroadcast(intent);
        dataSet.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
//        public ImageButton delete;
        public TextView content;
        public TextView time;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.reminder_title);
//            delete = (ImageButton) itemView.findViewById(R.id.reminder_item_delete);
            content = (TextView) itemView.findViewById(R.id.reminder_content);
            time = (TextView) itemView.findViewById(R.id.reminder_time);
            cardView = (CardView) itemView.findViewById(R.id.reminder_card);
        }
    }
}
