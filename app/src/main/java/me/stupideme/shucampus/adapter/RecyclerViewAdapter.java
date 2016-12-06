package me.stupideme.shucampus.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.stupideme.shucampus.CampusApp;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.model.Event;

/**
 * Created by StupidL on 2016/8/23.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Event> mList;

    public RecyclerViewAdapter(List<Event> list) {
        this.mList = list;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Event event = mList.get(position);
        Log.i("===position===", String.valueOf(position));
        holder.head.setImageResource(R.drawable.head_male);
        holder.name.setText(event.getAuthor().getUsername());
        holder.time.setText(event.getCreatedAt());
        holder.content.setText(event.getContent());
        holder.image.setImageResource(R.drawable.nav_header);
        holder.mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: mark this event as marked and update table in server

                CampusApp.hasMaarkedEventsCHanded = true;

            }
        });
        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        CircleImageView head;
        public TextView name;
        public TextView time;
        public TextView content;
        public ImageView image;
        ImageButton mark;
        ImageButton comments;
        ImageButton share;

        ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.event_card);
            head = (CircleImageView) itemView.findViewById(R.id.head_image);
            name = (TextView) itemView.findViewById(R.id.head_name);
            time = (TextView) itemView.findViewById(R.id.head_time);
            content = (TextView) itemView.findViewById(R.id.content);
            image = (ImageView) itemView.findViewById(R.id.event_image);
            mark = (ImageButton) itemView.findViewById(R.id.foot_mark);
            comments = (ImageButton) itemView.findViewById(R.id.foot_comment);
            share = (ImageButton) itemView.findViewById(R.id.foot_share);
        }
    }

}
