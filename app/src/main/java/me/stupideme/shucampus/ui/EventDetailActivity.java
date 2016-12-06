package me.stupideme.shucampus.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import me.stupideme.shucampus.R;
import me.stupideme.shucampus.model.Comment;

public class EventDetailActivity extends AppCompatActivity{

    List<Comment> list = new ArrayList<>();

    Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);



        mRunnable = new Runnable() {
            @Override
            public void run() {
                loadComments(list);
            }
        };

    }

    private void loadComments(List<Comment> list) {

    }
}
