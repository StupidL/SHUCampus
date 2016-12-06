package me.stupideme.shucampus.model;

/**
 * Created by 56211 on 2016/8/3.
 */

public class ReminderModel {
    private long id = -1;
    private String title;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
