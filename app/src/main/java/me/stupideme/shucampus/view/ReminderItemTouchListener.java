package me.stupideme.shucampus.view;

/**
 * Created by StupidL on 2016/12/9.
 */

public interface ReminderItemTouchListener {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
