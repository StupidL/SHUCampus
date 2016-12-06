package me.stupideme.shucampus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import me.stupideme.shucampus.model.Event;
import me.stupideme.shucampus.model.MyUser;

/**
 * Created by StupidL on 2016/8/24.
 */

public class DummyData {

    Random random;
    Calendar calendar = Calendar.getInstance();

    public static final String[] USER_NAMES = {"Allen", "John", "Lucy", "Bryant", "Ray", "James", "Wade", "Jordan", "Magic",
            "Johnson", "Marry", "Williams", "Sherlock", "Smith"};
    public static final String[] EVENT_CONTENTS = {
            "分享一张图片",
            "我有一本《算法中文版》，有人需要吗？微信：BigZhang",
            "求一本《通信原理》，感谢！QQ：2119895792",
            "《Java核心技术卷一卷二》出手，有需要的联系我！QQ：562117676",
            "求一辆二手自行车！联系方式：13382234563",
            "出手二手自行车，九成新，价格可面议！联系方式：17723467432"
    };
    public static List<Event> DummyEvent = new ArrayList<>();
    public DummyData(){
        for(int i=0;i<20;i++){
            Event event = new Event();
            String name = USER_NAMES[random.nextInt(USER_NAMES.length)];
            MyUser author = new MyUser();
            author.setUsername(name);
            author.setPassword("123");
            event.setAuthor(author);
            Date time = calendar.getTime();
        }
    }
}
