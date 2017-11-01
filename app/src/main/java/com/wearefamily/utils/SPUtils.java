package com.wearefamily.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wearefamily.base.BaseApplication;

/**
 * Created by Administrator on 2017/10/18.
 */

public class SPUtils {
    private static final String CONFIG = "CONFIG";
    private static SharedPreferences sharedPreferences;
    private static  SPUtils sp ;
    private SPUtils(){}
    public static SPUtils getInstance(){
        if (sp == null){
            sp = SPInstance.spUtils;
        }
        return sp;
    }
    private static class SPInstance{
        private final static SPUtils spUtils = new SPUtils();
    }
    public static void clear(){
        if (sharedPreferences == null){
            getSharePrefrence();
        }
        sharedPreferences.edit().clear().apply();
    }

    public static void getSharePrefrence() {
        sharedPreferences = getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    }

    public static Context getContext() {
        return BaseApplication.getContext();
    }
    public static boolean getBolean(String key , boolean defaultValue){
        if (sharedPreferences == null){
            getSharePrefrence();
        }
        return sharedPreferences.getBoolean(key, defaultValue);
    }
    public static void putBoolean(String key , boolean value){
        if (sharedPreferences == null){
            getSharePrefrence();
        }
        sharedPreferences.edit().putBoolean(key , value).apply();
    }
    public static String getString(String key , String defaultValue){
        if (sharedPreferences == null){
            getSharePrefrence();
        }
        return sharedPreferences.getString(key, defaultValue);
    }
    public static void putString(String key , String value){
        if (sharedPreferences == null){
            getSharePrefrence();
        }
        sharedPreferences.edit().putString(key , value).apply();
    }
    public static int getInt(String key , int defaultValue){
        if (sharedPreferences == null){
            getSharePrefrence();
        }
        return sharedPreferences.getInt(key, defaultValue);
    }
    public static void putInt(String key , int value){
        if (sharedPreferences == null){
            getSharePrefrence();
        }
        sharedPreferences.edit().putInt(key , value).apply();
    }
    public static float getDouble(String key , float defaultValue){
        if (sharedPreferences == null){
            getSharePrefrence();
        }
        return sharedPreferences.getFloat(key, defaultValue);
    }
    public static void putDouble(String key , float value){
        if (sharedPreferences == null){
            getSharePrefrence();
        }
        sharedPreferences.edit().putFloat(key , value).apply();
    }
}
