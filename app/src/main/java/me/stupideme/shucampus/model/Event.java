package me.stupideme.shucampus.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.print.PrintAttributes;
import android.provider.ContactsContract;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 56211 on 2016/8/3.
 */

public class Event extends BmobObject{


    //这些域对应着数据库中Event表的所有字段
    private MyUser author;          //发布动态的作者
    private BmobDate time;            //发布动态的时间
    private String content;         //动态的内容
    private BmobFile image;         //动态的配图
    private BmobRelation marks;     //多对多关系，用于存储所有标记该动态的用户
    private BmobRelation share;     //多对多关系，用于存储所有分享该动态的用户


    public Event(){
        this.setTableName("Event");
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public BmobDate getTime() {
        return time;
    }

    public void setTime(BmobDate time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public BmobRelation getMarks() {
        return marks;
    }

    public void setMarks(BmobRelation marks) {
        this.marks = marks;
    }

    public BmobRelation getShare() {
        return share;
    }

    public void setShare(BmobRelation share) {
        this.share = share;
    }
}
