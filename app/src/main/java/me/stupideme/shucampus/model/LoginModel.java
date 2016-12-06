package me.stupideme.shucampus.model;

import me.stupideme.shucampus.db.DBManager;

/**
 * Created by StupidL on 2016/12/6.
 */

public class LoginModel {
    private static LoginModel INSTANCE;
    private DBManager manager;

    private LoginModel() {
        manager = DBManager.getInstance();
    }

    public static LoginModel getInstance() {
        if (INSTANCE == null) {
            synchronized (LoginModel.class) {
                if (INSTANCE == null)
                    INSTANCE = new LoginModel();
            }
        }
        return INSTANCE;
    }

    public void saveUserInfo(String name, String pwd) {
        manager.insertUserInfo(name, pwd);
    }

    public String getUserInfo() {
        return manager.queryUserInfo();
    }

}
