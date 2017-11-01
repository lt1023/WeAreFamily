package com.wearefamily.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.wearefamily.common.CrashHandler;

/**
 * Created by Administrator on 2017/10/17.
 */

public abstract class BaseApplication extends Application {
    public abstract void initConfig();
    public static Context mContext = null;
    public static Handler mHandler = null;
    public static Thread mainThread = null;
    public static int mainThreadId = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mainThread = Thread.currentThread();
        //自定义异常捕获
        CrashHandler.getInstance().init(this);
        initConfig();
    }
    public static Context getContext(){
        return mContext;
    }
}
