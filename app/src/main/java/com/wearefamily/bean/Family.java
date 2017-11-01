package com.wearefamily.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/10/18.
 */
public class Family extends BmobObject{
    //一个家庭含有一个创建者和多个成员，属于一对多的关系
    private String familyName;//家庭名
    private User familyCreator;//创建者
    private BmobFile familyIcon;//家庭头像

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public User getFamilyCreator() {
        return familyCreator;
    }

    public void setFamilyCreator(User familyCreator) {
        this.familyCreator = familyCreator;
    }

    public BmobFile getFamilyIcon() {
        return familyIcon;
    }

    public void setFamilyIcon(BmobFile familyIcon) {
        this.familyIcon = familyIcon;
    }
}
