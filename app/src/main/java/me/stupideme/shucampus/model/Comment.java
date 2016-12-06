package me.stupideme.shucampus.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by 56211 on 2016/8/3.
 */

public class Comment extends BmobObject {
    private MyUser author;      //该条评论的作者
    private String content;     //该条评论的内容
    private Event event;        //该条评论属于的事件
    private String time;        //发布该条评论的时间

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
