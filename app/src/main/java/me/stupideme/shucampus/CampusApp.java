package me.stupideme.shucampus;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.Bmob;
import me.stupideme.shucampus.db.DBManager;
import me.stupideme.shucampus.model.Comment;
import me.stupideme.shucampus.model.Event;

/**
 * Created by StupidL on 2016/8/3.
 */

public class CampusApp extends Application {

    public volatile static List<Event> eventList = new ArrayList<>();
    public volatile static List<Comment> commentList = new ArrayList<>();
    public volatile static List<Event> markedEventList = new ArrayList<>();
    public volatile static boolean hasMaarkedEventsCHanded = false;

    @Override
    public void onCreate(){

        Bmob.initialize(getApplicationContext(), APIs.APPLICATION_ID);      //init bmob sdk
        DBManager.init(getApplicationContext());
    }
}
