package com.wearefamily.ui.family;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.wearefamily.MainActivity;
import com.wearefamily.R;
import com.wearefamily.application.BombApplication;
import com.wearefamily.base.BaseActivity;
import com.wearefamily.bean.Family;
import com.wearefamily.bean.User;
import com.wearefamily.common.AppManager;
import com.wearefamily.constant.FamilyConfig;
import com.wearefamily.utils.SPUtils;
import com.wearefamily.utils.ToastUtils;
import java.util.List;
import cc.duduhuo.dialog.smartisan.NormalDialog;
import cc.duduhuo.dialog.smartisan.SmartisanDialog;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/18.
 */
public class GuideFamilyActivity extends BaseActivity{

    private Toolbar mToolbar;
    private EditText mEdtSearch;
    private TextView mTvCreate;
    private User mCurrentUser;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guidefamily;
    }

    @Override
    public void initView() {
        mToolbar = findView(R.id.common_toolbar);
        initToolBar(mToolbar , "Family" ,true);
        mEdtSearch = findView(R.id.familyGuide_edt_searchFamily);
        mTvCreate = findView(R.id.familyGuide_tv_create);
    }

    @Override
    public void initListener() {
        setOnClick(mTvCreate);
        mEdtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = mEdtSearch.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null){
                    return false;
                }
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP){
                    return false;
                }
                if (event.getX() > mEdtSearch.getWidth()
                        - mEdtSearch.getPaddingRight()
                        - drawable.getIntrinsicWidth()){
                    String searchCreateId = mEdtSearch.getText().toString();
                    if (!TextUtils.isEmpty(searchCreateId)){
                        BmobQuery<Family> queryFamily = new BmobQuery<Family>();
                        queryFamily.addWhereEqualTo("familyCreator" , searchCreateId);
                        queryFamily.findObjects(new FindListener<Family>() {
                            @Override
                            public void done(List<Family> list, BmobException e) {
                                if (e == null){
                                    if (list.size() != 0){
                                        final Family family = list.get(0);
                                        if (family != null){
                                            showJoinFamilyDialog(family);
                                        }
                                    }else {
                                        ToastUtils.showToast(BombApplication.getContext() , "未查询到此家庭");
                                    }
                                }else {
                                    ToastUtils.showToast(BombApplication.getContext() , e.getMessage());
                                }
                            }
                        });
                    }else {
                        ToastUtils.showToast(BombApplication.getContext() , "请输入创建者ID");
                    }
                }

                return false;
            }
        });
    }



    @Override
    public void initData() {
        mCurrentUser = BmobUser.getCurrentUser(User.class);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.familyGuide_tv_create:
                BmobQuery<Family> queryFamily = new BmobQuery<>();
                queryFamily.addWhereEqualTo("familyCreator",mCurrentUser);
                queryFamily.include("familyCreator");
                queryFamily.findObjects(new FindListener<Family>() {
                    @Override
                    public void done(List<Family> list, BmobException e) {
                        if (e == null){
                            if (list.size() == 0){
                                nextActivity(CreateFamilyActivity.class);
                            }else {
                                ToastUtils.showToast(BombApplication.getContext() , "您已经创建过家庭");
                            }
                        }else {
                            ToastUtils.showToast(BombApplication.getContext() , e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }

    }
    private void showJoinFamilyDialog(final Family family) {
        final NormalDialog dialog = SmartisanDialog.createNormalDialog(this);
        dialog.setTitle("查询结果")
                .setMsg(family.getFamilyName())
                .setMsgGravity(Gravity.CENTER)
                .setLeftBtnText("取消")
                .setRightBtnText("加入")
                .show();
        dialog.setOnSelectListener(new NormalDialog.OnSelectListener() {
            @Override
            public void onLeftSelect() {
                dialog.dismiss();
            }

            @Override
            public void onRightSelect() {
                User user = new User();
                user.setIsJoin(true);
                user.setIsCreate(false);
                user.setFamily(family);
                user.update(mCurrentUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            SPUtils.putBoolean(FamilyConfig.SPEXIST ,true);
                            ToastUtils.showToast(GuideFamilyActivity.this,"加入家庭成功");
                            AppManager.getInstance().finishActivity();
                            nextActivity(MainActivity.class);
                            dialog.dismiss();
                        }
                        else {
                            ToastUtils.showToast(GuideFamilyActivity.this , e.getErrorCode()+"");
                            dialog.dismiss();
                        }
                    }
                });
                dialog.dismiss();
            }
        });
    }
}
