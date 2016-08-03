package me.stupideme.shucampus;

import android.app.Application;

import me.stupideme.shucampus.db.DBManager;

/**
 * Created by 56211 on 2016/8/3.
 */

public class CampusApp extends Application {

    public static DBManager dbManager;
    @Override
    public void onCreate(){
        dbManager = DBManager.getInstance(getApplicationContext());
    }
}
