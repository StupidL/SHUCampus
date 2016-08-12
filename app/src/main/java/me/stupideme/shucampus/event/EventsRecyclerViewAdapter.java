package me.stupideme.shucampus.event;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.model.Event;


/**
 * Created by stupidl on 16-8-8.
 * This adapter is used in MarkFragment and EventsFragment because that two fragments could use the same item layout
 */

public class EventsRecyclerViewAdapter extends RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder> {

    private List<Event> list;

    public EventsRecyclerViewAdapter(List<Event> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = list.get(position);
        holder.head.setImageResource(R.drawable.head_male);
        holder.name.setText(event.getAuthor().getUsername());
        holder.time.setText(event.getTime().getDate());
        holder.content.setText(event.getContent());
        holder.mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement onClickListener

            }
        });
        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement onClickListener

            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO implement onClickListener

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView card;
        public CircleImageView head;
        public TextView name;
        public TextView time;
        public TextView content;
        public ImageView image;
        public ImageButton mark;
        public ImageButton comments;
        public ImageButton share;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.event_card);
            head = (CircleImageView) itemView.findViewById(R.id.head_image);
            name = (TextView) itemView.findViewById(R.id.head_name);
            time= (TextView) itemView.findViewById(R.id.head_time);
            content = (TextView) itemView.findViewById(R.id.content);
            mark = (ImageButton) itemView.findViewById(R.id.foot_mark);
            comments = (ImageButton) itemView.findViewById(R.id.foot_comment);
            share = (ImageButton) itemView.findViewById(R.id.foot_share);
        }
    }
}
