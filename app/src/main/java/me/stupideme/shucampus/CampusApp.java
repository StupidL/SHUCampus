package me.stupideme.shucampus;

import android.app.Application;

import cn.bmob.v3.Bmob;
import me.stupideme.shucampus.API.APIs;
import me.stupideme.shucampus.db.DBManager;

/**
 * Created by StupidL on 2016/8/3.
 */

public class CampusApp extends Application {

    @Override
    public void onCreate(){
        Bmob.initialize(getApplicationContext(), APIs.APPLICATION_ID);      //init bmob sdk

    }
}
