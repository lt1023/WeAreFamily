package com.wearefamily.ui.welcome;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.wearefamily.R;
import com.wearefamily.adapter.GuideAdapter;
import com.wearefamily.base.BaseActivity;
import com.wearefamily.common.AppManager;
import com.wearefamily.constant.AppConfig;
import com.wearefamily.ui.user.LoginActivity;
import com.wearefamily.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */
public class GuidActivity  extends BaseActivity implements ViewPager.OnPageChangeListener{
    private ViewPager vp;
    private int[]imgIdArr;//图片资源数组
    private List<View> viewList;//图片资源集合

    private ImageView mFabStart;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        mFabStart = findView(R.id.guide_iv_start);
        initViewPager();
    }

    private void initViewPager() {
        vp = findView(R.id.guide_vp);
        //vp = (ViewPager) findViewById(R.id.guide_vp);
        //实例化图片资源
        imgIdArr = new int[]{
                R.drawable.bg_guide_family ,
                R.drawable.bg_guide_notes ,
                R.drawable.bg_guide_weather ,
                R.drawable.bg_guide_rebot
        };
        viewList = new ArrayList<>();
        //获取一个layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.MATCH_PARENT
        );
        //循环创建View并加入到集合
        int len = imgIdArr.length;
        for (int i = 0; i < len; i++) {
            //new ImageView并设置全屏和图片资源
            ImageView imgView = new ImageView(this);
            imgView.setLayoutParams(params);
            imgView.setBackgroundResource(imgIdArr[i]);
            //将imgView加入到集合中
            viewList.add(imgView);
        }
        //设置Adapter
        vp.setAdapter(new GuideAdapter(viewList));
        //设置滑动监听
        vp.addOnPageChangeListener(this);
    }

    @Override
    public void initListener() {
        setOnClick(mFabStart);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.guide_iv_start:
                AppManager.getInstance().finishActivity();
                SPUtils.getInstance().putBoolean(AppConfig.SPFIRST , false);
                nextActivity(LoginActivity.class);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //判断是否是最后一页，若是则显示按钮
        if (position == imgIdArr.length - 1){
            mFabStart.setVisibility(View.VISIBLE);
        }else {
            mFabStart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
