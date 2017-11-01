package com.wearefamily.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户实体类
 * Created by Administrator on 2017/10/18.
 */
public class User extends BmobUser {
    //默认含有的字段为；username ， password , email , sessionToken , mobilePhoneNumber
    private String nick;//昵称
    private int age;//年龄
    private int type;//0：子女    | 1 ： 父母
    private boolean isCreate;//创建家庭
    private boolean isJoin;//加入家庭
    private Family mFamily;//加入的家庭
    private BmobFile userPhoto;//用户头像
    private String location;//地理位置

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(boolean create) {
        isCreate = create;
    }

    public boolean getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(boolean join) {
        isJoin = join;
    }

    public Family getFamily() {
        return mFamily;
    }

    public void setFamily(Family family) {
        mFamily = family;
    }

    public BmobFile getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(BmobFile userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
