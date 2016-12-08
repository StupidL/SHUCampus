package me.stupideme.shucampus.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.model.CourseBean;
import me.stupideme.shucampus.db.DBManager;

/**
 * Created by StupidL on 2016/8/11.
 */

public class ClassListViewAdapter extends BaseAdapter {

    private List<CourseBean> list;
    private Context mContext;

    public ClassListViewAdapter(Context context, List<CourseBean> list) {
        mContext = context;
        this.list = list;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_class, null);
            viewHolder = new ViewHolder();
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.card_class);
            viewHolder.weekdayTextView = (TextView) convertView.findViewById(R.id.text_weekday);
            viewHolder.beginTextView = (TextView) convertView.findViewById(R.id.text_begin);
            viewHolder.endTextView = (TextView) convertView.findViewById(R.id.text_end);
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.text_class_name);
            viewHolder.locationTextView = (TextView) convertView.findViewById(R.id.text_class_location);
            viewHolder.teacherTextView = (TextView) convertView.findViewById(R.id.text_class_teacher);
            viewHolder.modTextView = (TextView) convertView.findViewById(R.id.text_mod);
            viewHolder.delete = (ImageButton) convertView.findViewById(R.id.img_button_delete_class);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        switch (list.get(i).getWeekday()){
            case 0:
                viewHolder.weekdayTextView.setText("周一");
                break;
            case 1:
                viewHolder.weekdayTextView.setText("周二");
                break;
            case 2:
                viewHolder.weekdayTextView.setText("周三");
                break;
            case 3:
                viewHolder.weekdayTextView.setText("周四");
                break;
            case 4:
                viewHolder.weekdayTextView.setText("周五");
                break;
            case 5:
                viewHolder.weekdayTextView.setText("周六");
                break;
            case 6:
                viewHolder.weekdayTextView.setText("周日");
                break;
        }

        viewHolder.weekdayTextView.setText(String.valueOf(list.get(i).getWeekday()));
        viewHolder.beginTextView.setText(String.valueOf(list.get(i).getBegin()));
        viewHolder.endTextView.setText(String.valueOf(list.get(i).getEnd()));
        viewHolder.nameTextView.setText(String.valueOf(list.get(i).getName()));
        viewHolder.locationTextView.setText(String.valueOf(list.get(i).getLocation()));
        viewHolder.teacherTextView.setText(String.valueOf(list.get(i).getTeacher()));
        viewHolder.modTextView.setText(String.valueOf(list.get(i).getMod()));

        viewHolder.cardView.setCardBackgroundColor(list.get(i).getColor());
        viewHolder.cardView.setRadius(4);
        viewHolder.cardView.setCardElevation(3);

        final long classId = list.get(i).getClassId();
        final int position = i;
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBManager.getInstance(mContext).deleteClass(classId);
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        CardView cardView;
        TextView weekdayTextView;
        TextView beginTextView;
        TextView endTextView;
        TextView nameTextView;
        TextView locationTextView;
        TextView teacherTextView;
        TextView modTextView;
        ImageButton delete;
    }
}

