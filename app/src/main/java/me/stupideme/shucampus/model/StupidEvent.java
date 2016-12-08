package me.stupideme.shucampus.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by StupidL on 2016/12/7.
 */

public class StupidEvent extends BmobObject {

    private String name;
    private String time;
    private String phone;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
