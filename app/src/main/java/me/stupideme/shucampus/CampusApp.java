package me.stupideme.shucampus;

import android.app.Application;
import android.util.Log;

import cn.bmob.v3.Bmob;
import me.stupideme.shucampus.API.APIs;
import me.stupideme.shucampus.db.DBManager;

/**
 * Created by StupidL on 2016/8/3.
 */

public class CampusApp extends Application {

    public static DBManager manager;

    @Override
    public void onCreate(){

        Bmob.initialize(getApplicationContext(), APIs.APPLICATION_ID);      //init bmob sdk
        Log.i("=====Bmob init=====","success");
        manager = DBManager.getInstance(getApplicationContext());

    }
}
