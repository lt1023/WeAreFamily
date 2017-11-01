package com.wearefamily.ui.user;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.wearefamily.R;
import com.wearefamily.application.BombApplication;
import com.wearefamily.base.BaseActivity;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.wearefamily.bean.User;
import com.wearefamily.common.AppManager;
import com.wearefamily.constant.BmobConfig;
import com.wearefamily.utils.ToastUtils;
import com.wearefamily.wigdet.CountDownButtonHelper;
import android.support.v7.widget.Toolbar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/10/19.
 */
public class RegisterResetActivity extends BaseActivity{

    private int mSign;
    private Toolbar mToolbar;
    private EditText mEdtNum;
    private EditText mEdtSmsCode;
    private EditText mEdtPassword;
    private EditText mEdtRepassword;
    private Button mBtn;
    private Button mBtnGetSmsCode;

    String smsCode;
    String password;
    String rePassword;
    String phoneNumber;
    @Override
    public int getLayoutId() {
        return R.layout.activity_registerreset;
    }

    @Override
    public void initView() {
        mSign = getIntent().getIntExtra("sign" , 3);
        mToolbar = findView(R.id.common_toolbar);
        mEdtNum = findView(R.id.edt_num);
        mEdtSmsCode = findView(R.id.edt_smsCode);
        mEdtPassword = findView(R.id.edt_password);
        mEdtRepassword = findView(R.id.edt_rePassword);

        mBtn = findView(R.id.btn);
        mBtnGetSmsCode = findView(R.id.btn_getSmsCode);

        if (mSign == 0){
            initToolBar(mToolbar , "注册" , true);
            mBtn.setText("注   册");
        }else if (mSign == 1){
            initToolBar(mToolbar , "重置密码" , true);
            mBtn.setText("重置密码");
        }
    }


    @Override
    public void initListener() {
        setOnClick(mBtnGetSmsCode);
        setOnClick(mBtn);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        boolean isPhoneNumber;
        boolean isTruePassword;
        switch (v.getId()){
            case R.id.btn_getSmsCode:
                phoneNumber = mEdtNum.getText().toString().trim();
                isPhoneNumber = verifyPhone(phoneNumber);
                if (!TextUtils.isEmpty(phoneNumber) && isPhoneNumber){
                    loadSMS();
                    BmobSMS.requestSMSCode(phoneNumber, BmobConfig.SMSTEMPLATE, new QueryListener<Integer>() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null){
                                ToastUtils.showToast(BombApplication.getContext(),"验证码已发送");
                            }else {
                                ToastUtils.showToast(BombApplication.getContext() , "验证码发送失败" + e.getMessage());
                            }
                        }
                    });
                }else {
                    ToastUtils.showToast(this,"请输入正确手机号");
                }
                break;
            case R.id.btn:
                phoneNumber = mEdtNum.getText().toString().trim();
                smsCode = mEdtSmsCode.getText().toString().trim();
                password = mEdtPassword.getText().toString().trim();
                rePassword = mEdtRepassword.getText().toString().trim();

                isPhoneNumber = verifyPhone(phoneNumber);
                isTruePassword = verifyPassword(password , rePassword);
                if (mSign == 0){
                    registerUser(isPhoneNumber , isTruePassword);
                }else if (mSign == 1){
                    resetPassword(isPhoneNumber , isTruePassword);
                }

        }
    }

    private void resetPassword(boolean isPhoneNumber, boolean isTruePassword) {
        if (isPhoneNumber && isTruePassword){
            dialog.showDialog();
            BmobUser.resetPasswordBySMSCode(smsCode, rePassword, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null){
                        dialog.dissmissDialog();
                        ToastUtils.showToast(BombApplication.getContext(),"密码重置成功");
                        AppManager.getInstance().finishActivity();
                    }else {
                        dialog.dissmissDialog();
                        ToastUtils.showToast(BombApplication.getContext() , e.getMessage());
                    }
                }
            });
        }else {
            dialog.dissmissDialog();
            ToastUtils.showToast(this , "请检查输入信息");
        }
    }


    private void registerUser(boolean isPhoneNumber , boolean isTruePassword) {
        if (isPhoneNumber && isTruePassword){
            dialog.showDialog();
            User user = new User();
            user.setPassword(rePassword);
            user.setMobilePhoneNumber(phoneNumber);
            user.signOrLogin(smsCode, new SaveListener<Object>() {
                @Override
                public void done(Object o, BmobException e) {
                    if (e == null){
                        dialog.dissmissDialog();
                        BmobUser.logOut();
                        AppManager.getInstance().finishActivity();
                    }else {
                        dialog.dissmissDialog();
                        ToastUtils.showToast(BombApplication.getContext() , e.getMessage());
                    }
                }
            });
        }else {
            dialog.dissmissDialog();
            ToastUtils.showToast(this , "请检查输入信息");
        }
    }

    private boolean verifyPassword(String password, String repassword) {
        boolean isPass1 = verifyPassword(password);
        boolean isPass2 = verifyPassword(repassword);
        return isPass1 && isPass2 && password.equals(repassword);
    }

    private boolean verifyPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }

    private void loadSMS() {
        CountDownButtonHelper helper = new CountDownButtonHelper(mBtnGetSmsCode , "倒计时", 60, 1);
        helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
            @Override
            public void finish() {
                mBtnGetSmsCode.setText("再次获取");
            }
        });
        helper.start();
    }

    private boolean verifyPhone(String number) {
        if (!TextUtils.isEmpty(number)){
            String reExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
            Pattern p = Pattern.compile(reExp);
            Matcher m = p.matcher(number);
            return m.find();
        }
        return false;
    }
}
