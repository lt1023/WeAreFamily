package com.wearefamily;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.wearefamily.adapter.ViewPagerAdapter;
import com.wearefamily.common.AppManager;
import com.wearefamily.utils.ToastUtils;
import com.wearefamily.view.ChatFragment;
import com.wearefamily.view.FindFragment;
import com.wearefamily.view.HomeFragment;
import com.wearefamily.view.MineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *                                                    __----~~~~~~~~~~~------___
 *                                   .  .   ~~//====......          __--~ ~~
 *                   -.            \_|//     |||\\  ~~~~~~::::... /~
 *                ___-==_       _-~o~  \/    |||  \\            _/~~-
 *        __---~~~.==~||\=_    -_--~/_-~|-   |\\   \\        _/~
 *    _-~~     .=~    |  \\-_    '-~7  /-   /  ||    \      /
 *  .~       .~       |   \\ -_    /  /-   /   ||      \   /
 * /  ____  /         |     \\ ~-_/  /|- _/   .||       \ /
 * |~~    ~~|--~~~~--_ \     ~==-/   | \~--===~~        .\
 *          '         ~-|      /|    |-~\~~       __--~~
 *                      |-~~-_/ |    |   ~\_   _-~            /\
 *                           /  \     \__   \/~                \__
 *                       _--~ _/ | .-~~____--~-/                  ~~==.
 *                      ((->/~   '.|||' -_|    ~~-/ ,              . _||
 *                                 -_     ~\      ~~---l__i__i__i--~~_/
 *                                 _-~-__   ~)  \--______________--~~
 *                               //.-~~~-~_--~- |-------~~~~~~~~
 *                                      //.-~~~--\
 *                               神兽保佑
 *                              代码无BUG!
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private BottomBar mBottomBar;
    private TextView mTitle;
    private List<Fragment> mFragList;
    private long lastTime ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getInstance().addActivity(this);
        initView();
        initData();
        initViewPager();
    }
    private void initView() {
        mTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
        mBottomBar = (BottomBar) findViewById(R.id.bottombar_main);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_home:
                        mViewPager.setCurrentItem(0);
                        mTitle.setText("首页");
                        break;
                    case R.id.tab_chat:
                        mViewPager.setCurrentItem(1);
                        mTitle.setText("消息");
                        break;
                    case R.id.tab_find:
                        mViewPager.setCurrentItem(2);
                        mTitle.setText("发现");
                        break;
                    case R.id.tab_person:
                        mViewPager.setCurrentItem(3);
                        mTitle.setText("我的");
                        break;
                    default:
                        mViewPager.setCurrentItem(0);
                        mTitle.setText("首页");
                        break;
                }
            }
        });
    }
    private void initData() {
        mBottomBar.selectTabAtPosition(0);
    }
    private void initViewPager() {
        mFragList = new ArrayList<>();
        mFragList.add(new HomeFragment());
        mFragList.add(new ChatFragment());
        mFragList.add(new FindFragment());
        mFragList.add(new MineFragment());

        ViewPagerAdapter vpadapter = new ViewPagerAdapter(getSupportFragmentManager() , mFragList);
        mViewPager.setAdapter(vpadapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBar.selectTabAtPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis() - lastTime >2000){
                ToastUtils.showToast(this , "再次点击退出");
                lastTime = System.currentTimeMillis();
            }else {
                AppManager.getInstance().finishAllActivity();
                AppManager.getInstance().appExit(this);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
