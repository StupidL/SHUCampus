package me.stupideme.shucampus.view;

import java.util.List;

import me.stupideme.shucampus.model.CourseBean;

/**
 * Created by StupidL on 2016/12/7.
 */

public interface CourseView {
    void autoLoad(List<CourseBean> list);

    void addCourse(CourseBean model);

}
