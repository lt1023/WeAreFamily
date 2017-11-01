package com.wearefamily.application;

import com.wearefamily.base.BaseApplication;
import cn.bmob.v3.Bmob;
import com.wearefamily.constant.BmobConfig;

/**
 * Created by Administrator on 2017/10/17.
 */

public class BombApplication extends BaseApplication {

    @Override
    public void initConfig() {
        Bmob.initialize(this, BmobConfig.APPID);
    }
}
