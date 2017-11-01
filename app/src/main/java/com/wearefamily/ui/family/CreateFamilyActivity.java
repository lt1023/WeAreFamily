package com.wearefamily.ui.family;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.wearefamily.MainActivity;
import com.wearefamily.R;
import com.wearefamily.application.BombApplication;
import com.wearefamily.base.BaseActivity;
import com.wearefamily.base.BaseApplication;
import com.wearefamily.bean.Family;
import com.wearefamily.bean.User;
import com.wearefamily.common.AppManager;
import com.wearefamily.constant.FamilyConfig;
import com.wearefamily.ui.user.LoginActivity;
import com.wearefamily.utils.SPUtils;
import com.wearefamily.utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/10/20.
 */
public class CreateFamilyActivity extends BaseActivity{

    private Toolbar mToolbar;
    private EditText mEdFamilyName;
    private User mCurrentUser;

    @Override
    public int getLayoutId() {
        return R.layout.activity_createfamily;
    }

    @Override
    public void initView() {
        mToolbar = findView(R.id.common_toolbar);
        initToolBar(mToolbar , "创建家庭" ,true);
        mEdFamilyName = findView(R.id.createFamily_edt_name);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_operate , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_ok:
                mCurrentUser = BmobUser.getCurrentUser(User.class);
                String familyName = mEdFamilyName.getText().toString().trim();

                if (!TextUtils.isEmpty(familyName)){
                    if (mCurrentUser != null){
                        final Family family = new Family();
                        family.setFamilyName(familyName);
                        family.setFamilyCreator(mCurrentUser);
                        family.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null){
                                    if (family != null){
                                        User user = new User();
                                        user.setIsCreate(true);
                                        user.setIsJoin(false);
                                        user.setFamily(family);
                                        user.setNick("用户"+ mCurrentUser.getObjectId());
                                        user.update(mCurrentUser.getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null){
                                                    SPUtils.putBoolean(FamilyConfig.SPEXIST , true);
                                                    ToastUtils.showToast(CreateFamilyActivity.this , "家庭创建成功");
                                                    AppManager.getInstance().finishActivity(GuideFamilyActivity.class);
                                                    AppManager.getInstance().finishActivity();
                                                    nextActivity(MainActivity.class);
                                                }
                                            }
                                        });
                                    }
                                }else {
                                    ToastUtils.showToast(BombApplication.getContext() , "创建家庭失败" + e.getMessage());
                                }
                            }
                        });
                    }else {
                        ToastUtils.showToast(BombApplication.getContext() , "用户已失效，请重新登录");
                        AppManager.getInstance().finishActivity();
                        nextActivity(LoginActivity.class);
                    }
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
