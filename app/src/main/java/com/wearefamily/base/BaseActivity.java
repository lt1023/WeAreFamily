package com.wearefamily.base;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wearefamily.R;
import com.wearefamily.common.AppManager;
import com.wearefamily.wigdet.LoadingDialog;

/**
 * Created by Administrator on 2017/10/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    private SparseArray<View> mViews;
    public LoadingDialog dialog;
    //将Activity共有的部分抽离出来
    public abstract int getLayoutId();
    public abstract void initView();
    public abstract void initListener();
    public abstract void initData();

    public abstract void processClick(View v);
    @Override
    public void onClick(View v) {
        processClick(v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViews = new SparseArray<>();
        setContentView(getLayoutId());
        AppManager.getInstance().addActivity(this);
        dialog = new LoadingDialog(this);
        initView();
        initData();
        initListener();
    }
    //通过view的Id实例化View
    public <E extends View> E findView(int viewId){
        E view = (E) mViews.get(viewId);
        if (view == null){
            view = (E) findViewById(viewId);
            mViews.put(viewId,view);
        }
        return view;
    }
    //view设置onclick事件
    public <E extends View> void setOnClick(E view){
        view.setOnClickListener(this);
    }
    //初始化ToolBar
    public void initToolBar(Toolbar toolbar , String title , boolean isBack){
        TextView toolbar_tv_title = (TextView) toolbar.findViewById(R.id.toolbar_tv_title);
        toolbar.setTitle("");
        toolbar_tv_title.setText(title);
        setSupportActionBar(toolbar);
        if (isBack){
            try
            {
                ActionBar actbar = getSupportActionBar();
                if (null != actbar) {
                    actbar.setDisplayHomeAsUpEnabled(true);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppManager.getInstance().finishActivity();
                    }
                });

        }
    }
    //跳转Activity
    public void nextActivity(Class cls){
        nextActivity(cls,null);
    }

    public void nextActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent(this,cls);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
    }
}
