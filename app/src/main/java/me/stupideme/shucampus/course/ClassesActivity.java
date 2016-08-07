package me.stupideme.shucampus.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import me.stupideme.shucampus.CampusApp;
import me.stupideme.shucampus.R;

public class ClassesActivity extends AppCompatActivity {

    private List<ClassModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassesActivity.this.finish();
            }
        });

        ListView listView = (ListView) findViewById(R.id.classes_list_view);
        list = CampusApp.manager.getAllClass();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.classes_fab);
        fab.attachToListView(listView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClassesActivity.this, AddClassActivity.class));
            }
        });

        ListAdapter adapter = new ListAdapter(list);
        listView.setAdapter(adapter);
    }

    private class ListAdapter extends BaseAdapter {

        private List<ClassModel> list;

        ListAdapter(List<ClassModel> list) {
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = new ViewHolder();
            final int position = i;
            view = getLayoutInflater().inflate(R.layout.item_class, null);
            if (viewHolder == null) {

                viewHolder.cardView = (CardView) view.findViewById(R.id.card_class);
                viewHolder.weekdayTextView = (TextView) view.findViewById(R.id.text_weekday);
                viewHolder.beginTextView = (TextView) view.findViewById(R.id.text_begin);
                viewHolder.endTextView = (TextView) view.findViewById(R.id.text_end);
                viewHolder.nameTextView = (TextView) view.findViewById(R.id.text_class_name);
                viewHolder.locationTextView = (TextView) view.findViewById(R.id.text_class_location);
                viewHolder.teacherTextView = (TextView) view.findViewById(R.id.text_class_teacher);
                viewHolder.modTextView = (TextView) view.findViewById(R.id.text_mod);
                viewHolder.delete = (ImageButton) view.findViewById(R.id.img_button_delete_class);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }

            ClassModel model = list.get(i);
            viewHolder.weekdayTextView.setText(model.getWeekday());
            viewHolder.beginTextView.setText(model.getBegin());
            viewHolder.endTextView.setText(model.getEnd());
            viewHolder.nameTextView.setText(model.getName());
            viewHolder.locationTextView.setText(model.getLocation());
            viewHolder.teacherTextView.setText(model.getTeacher());
            viewHolder.modTextView.setText(model.getMod());

            viewHolder.cardView.setCardBackgroundColor(model.getColor());
            viewHolder.cardView.setRadius(4);
            viewHolder.cardView.setCardElevation(3);

            final long classId = model.getClassId();
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CampusApp.manager.deleteClass(classId);
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

            return view;
        }

         class ViewHolder {
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
}
