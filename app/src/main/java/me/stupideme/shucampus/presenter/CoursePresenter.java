package me.stupideme.shucampus.presenter;

import android.util.Log;

import me.stupideme.shucampus.model.CourseBean;
import me.stupideme.shucampus.model.CourseModel;
import me.stupideme.shucampus.view.CourseView;

/**
 * Created by StupidL on 2016/12/7.
 */

public class CoursePresenter {

    private static final String TAG = "CoursePresenter";
    private CourseModel mCourseModel;
    private CourseView mCourseView;
    private static CoursePresenter INSTANCE;

    private CoursePresenter(CourseView view) {
        mCourseView = view;
        mCourseModel = CourseModel.getInstance();
    }

    public static CoursePresenter getInstance(CourseView view) {
        if (INSTANCE == null)
            INSTANCE = new CoursePresenter(view);
        return INSTANCE;
    }

    public void addCourse(String info) {
        if (!info.isEmpty()) {
            CourseBean bean = new CourseBean();
            String[] courseInfo = info.split("\\+");
            int weekday = Integer.parseInt(courseInfo[0]);
            int begin = Integer.parseInt(courseInfo[1]);
            int end = Integer.parseInt(courseInfo[2]);

            String name = courseInfo[3];
            String location = courseInfo[4];
            String teacher = courseInfo[5];
            int mod = Integer.parseInt(courseInfo[6]);
            int color = Integer.parseInt(courseInfo[7]);

            bean.setWeekday(weekday);
            bean.setBegin(begin);
            bean.setEnd(end);
            bean.setMod(mod);
            bean.setName(name);
            bean.setLocation(location);
            bean.setTeacher(teacher);
            bean.setColor(color);
            long id = weekday << 6 + begin << 4 + end << 2;
            Log.v("CoursePresenter", "course id = " + id);
            bean.setClassId(id);
            mCourseView.addCourse(bean);
            mCourseModel.addCourse(bean);
        }
    }

    public void removeCourse(CourseBean model) {
        mCourseModel.removeCourse(model);
        Log.v(TAG, "remove Course...");
    }

    public void autoLoadCourses() {
        mCourseView.autoLoad(mCourseModel.autoLoadCourses());
    }
}
