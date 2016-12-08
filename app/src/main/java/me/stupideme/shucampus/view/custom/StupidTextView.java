package me.stupideme.shucampus.view.custom;

import android.content.Context;
import android.view.ContextMenu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.stupideme.shucampus.view.DialogListener;

/**
 * Created by StupidL on 2016/12/8.
 */

public class StupidTextView extends TextView{

    public StupidTextView(Context context) {
        super(context);

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                RelativeLayout layout = (RelativeLayout) getParent();
                if (layout != null)
                    layout.removeView(view);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu) {

    }

}
