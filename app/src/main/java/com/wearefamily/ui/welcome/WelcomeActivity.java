package com.wearefamily.ui.welcome;

import android.util.Log;
import android.view.View;

import com.wearefamily.MainActivity;
import com.wearefamily.R;
import com.wearefamily.base.BaseActivity;
import com.wearefamily.bean.Family;
import com.wearefamily.bean.User;
import com.wearefamily.common.AppManager;
import com.wearefamily.constant.AppConfig;
import com.wearefamily.constant.FamilyConfig;
import com.wearefamily.ui.family.GuideFamilyActivity;
import com.wearefamily.ui.user.LoginActivity;
import com.wearefamily.utils.SPUtils;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/10/17.
 */

public class WelcomeActivity extends BaseActivity{
    private BmobUser mBmobUser;
    private boolean isFirst;//是否第一次进入
    private boolean isExist;//是否存在family
    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep( 5 * 1000);
                    isFirst = SPUtils.getInstance().getBolean(AppConfig.SPFIRST,true);
                    if (isFirst){
                        AppManager.getInstance().finishActivity();
                        nextActivity(GuidActivity.class);
                        Log.i("============", "进入引导界面");
                    }
                    else
                    {
                        mBmobUser = BmobUser.getCurrentUser(User.class);
                        if (mBmobUser != null){
                            isExist = SPUtils.getInstance().getBolean(FamilyConfig.SPEXIST, false);
                            if (isExist) {
                                AppManager.getInstance().finishActivity();
                                nextActivity(MainActivity.class);
                                Log.i("============", "进入主界面");
                            }else {
                                AppManager.getInstance().finishActivity();
                                nextActivity(LoginActivity.class);
                                Log.i("============", "进入登录界面");
                            }
                        }else {
                            AppManager.getInstance().finishActivity();
                            nextActivity(LoginActivity.class);
                            Log.i("============", "进入登录界面");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    public void processClick(View v) {

    }
}
