package me.stupideme.shucampus.presenter;

import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import me.stupideme.shucampus.model.StupidEvent;
import me.stupideme.shucampus.view.MainView;

/**
 * Created by StupidL on 2016/12/7.
 */

public class MainPresenter {

    private MainView mMainView;

    private static MainPresenter INSTANCE;

    private MainPresenter(MainView view) {
        mMainView = view;
    }

    public static MainPresenter getInstance(MainView view) {
        if (INSTANCE == null) {
            synchronized (MainPresenter.class) {
                if (INSTANCE == null)
                    INSTANCE = new MainPresenter(view);
            }
        }
        return INSTANCE;
    }


    public void autoRefresh() {
        fetchData();
    }

    public void refresh() {
        fetchData();
    }

    private void fetchData() {
        mMainView.onStartRefresh();
        String bql = "SELECT * FROM StupidEvent";
        BmobQuery<StupidEvent> query = new BmobQuery<>();
        query.setLimit(20);
        query.order("createdAt");
        query.setMaxCacheAge(TimeUnit.DAYS.toMillis(1));
        query.setSQL(bql);

        boolean hasCache = query.hasCachedResult(StupidEvent.class);
        if (hasCache) {
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.doSQLQuery(new SQLQueryListener<StupidEvent>() {
            @Override
            public void done(BmobQueryResult<StupidEvent> bmobQueryResult, BmobException e) {

                if (e == null) {
                    List<StupidEvent> list = bmobQueryResult.getResults();
                    mMainView.onRefreshSuccess(list);
                    Log.v("MainPresenter", "load data success...");
                    Log.v("MainPresenter", "size : " + list.size());
                } else {
                    mMainView.onRefreshFailed();
                    Log.v("MainPresenter", e.toString());
                }
            }
        });
    }
}
