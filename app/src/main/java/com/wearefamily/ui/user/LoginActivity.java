package com.wearefamily.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wearefamily.MainActivity;
import com.wearefamily.R;
import com.wearefamily.base.BaseActivity;
import com.wearefamily.bean.User;
import com.wearefamily.common.AppManager;
import com.wearefamily.constant.FamilyConfig;
import com.wearefamily.ui.app.AboutActivity;
import com.wearefamily.ui.family.GuideFamilyActivity;
import com.wearefamily.utils.SPUtils;
import com.wearefamily.utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by Administrator on 2017/10/18.
 */
public class LoginActivity extends BaseActivity{

    private static final String NULLTEXTERR = "请输入账号或者密码！";
    private ImageView mIvAbout;
    private EditText mUserName;
    private EditText mUserPass;
    private Button mLoginBtn;
    private TextView mTVRegister;
    private TextView mTVForgetpwd;
    private BmobUser mBmobUser;
    private Bundle mBundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mIvAbout = findView(R.id.login_iv_about);
        mUserName = findView(R.id.login_edt_userName);
        mUserPass = findView(R.id.login_edt_userPass);
        mLoginBtn = findView(R.id.login_btn);
        mTVRegister = findView(R.id.login_tv_register);
        mTVForgetpwd = findView(R.id.login_tv_forgetPass);
    }

    @Override
    public void initListener() {
        setOnClick(mIvAbout);
        setOnClick(mLoginBtn);
        setOnClick(mTVRegister);
        setOnClick(mTVForgetpwd);
    }

    @Override
    public void initData() {
        mBmobUser = BmobUser.getCurrentUser();
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.login_iv_about:
                nextActivity(AboutActivity.class);
                break;
            case R.id.login_tv_register:
                mBundle = new Bundle();
                mBundle.putInt("sign" , 0);
                nextActivity(RegisterResetActivity.class,mBundle);
                break;
            case R.id.login_tv_forgetPass:
                mBundle = new Bundle();
                mBundle.putInt("sign" , 1);
                nextActivity(RegisterResetActivity.class,mBundle);
                break;
            case R.id.login_btn:
                dialog.showDialog();
                String userName = mUserName.getText().toString().trim();
                String userPswd = mUserPass.getText().toString().trim();
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPswd)){
                    mBmobUser.loginByAccount(userName, userPswd, new LogInListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (user != null){
                                if (user.getFamily() != null){
                                    boolean isJoin = user.getIsJoin();
                                    boolean isCreate = user.getIsCreate();
                                    if (isJoin || isCreate){
                                        dialog.dissmissDialog();
                                        AppManager.getInstance().finishActivity();
                                        SPUtils.putBoolean(FamilyConfig.SPEXIST , true);
                                        nextActivity(MainActivity.class);
                                    }
                                }else {
                                    dialog.dissmissDialog();
                                    AppManager.getInstance().finishActivity();
                                    nextActivity(GuideFamilyActivity.class);
                                }
                            }else {
                                dialog.dissmissDialog();
                                ToastUtils.showToast(LoginActivity.this , e.getMessage());
                            }
                        }
                    });
                }else {
                    dialog.dissmissDialog();
                    ToastUtils.showToast(this , NULLTEXTERR);
                }
                break;
        }
    }
}
