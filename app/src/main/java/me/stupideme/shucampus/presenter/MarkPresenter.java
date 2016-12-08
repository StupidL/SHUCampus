package me.stupideme.shucampus.presenter;

import me.stupideme.shucampus.view.MarkView;

/**
 * Created by StupidL on 2016/12/7.
 */

public class MarkPresenter {

    private MarkView mMarkView;
    private static MarkPresenter INSTANCE;

    private MarkPresenter(MarkView view) {
        mMarkView = view;
    }

    public static MarkPresenter getInstance(MarkView view) {
        if (INSTANCE == null) {
            synchronized (MarkPresenter.class) {
                if (INSTANCE == null)
                    INSTANCE = new MarkPresenter(view);
            }
        }
        return INSTANCE;
    }

    public void remove(){
        mMarkView.remove();
    }
}
