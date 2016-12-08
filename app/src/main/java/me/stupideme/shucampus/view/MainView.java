package me.stupideme.shucampus.view;

import java.util.List;

import me.stupideme.shucampus.model.StupidEvent;

/**
 * Created by StupidL on 2016/12/7.
 */

public interface MainView {
    void onStartRefresh();

    void onRefreshSuccess(List<StupidEvent> list);

    void onRefreshFailed();

    void onStartLoadMore();

    void onLoadMoreSuccess(List<StupidEvent> list);

    void onLoadMoreFailed();

    void showNoMore();

    void onLogout();
}
