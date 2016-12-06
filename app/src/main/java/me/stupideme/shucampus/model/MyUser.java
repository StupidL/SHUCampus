package me.stupideme.shucampus.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 56211 on 2016/7/31.
 */

public class MyUser extends BmobUser {
    private BmobFile head;      //用户头像

    public BmobFile getHead() {
        return head;
    }

    public void setHead(BmobFile head) {
        this.head = head;
    }
}
