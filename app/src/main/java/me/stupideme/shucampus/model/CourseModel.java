package me.stupideme.shucampus.model;

import java.util.List;

import me.stupideme.shucampus.db.DBManager;

/**
 * Created by StupidL on 2016/12/7.
 */

public class CourseModel {
    private static CourseModel INSTANCE;
    private DBManager manager;

    private CourseModel() {
        manager = DBManager.getInstance();
    }

    public static CourseModel getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CourseModel();

        return INSTANCE;
    }

    public void addCourse(CourseBean bean) {
        manager.insertClass(bean);
    }

    public void removeCourse(CourseBean bean) {
        long id = bean.getWeekday() << 6 + bean.getBegin() << 4 + bean.getEnd() << 2;
        manager.deleteClass(id);
    }

    public List<CourseBean> autoLoadCourses(){
        return manager.getAllClass();
    }
}
