package com.wearefamily.wigdet;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;

/**
 * Created by Administrator on 2017/10/20.
 */
public class CountDownButtonHelper {
    //倒计时timer
    private CountDownTimer mCountDownTimer;
    //倒计时结束回调接口
    private OnFinishListener mListener;
    private Button mButton;

    public CountDownButtonHelper(final Button codeBtn, final String defaultString, int max, int interval) {
        this.mButton = codeBtn;
        mCountDownTimer = new CountDownTimer(max * 1000 ,interval * 1000 -10) {
            @Override
            public void onTick(long time) {
                mButton.setText(defaultString + "(" + ((time + 15) / 1000) + "秒");
                Log.d("CountDownButtonHelper", "time=" + time + "text="+
                        ((time + 15) / 1000));
            }

            @Override
            public void onFinish() {
                mButton.setEnabled(true);
                mButton.setText(defaultString);
                if (mListener != null){
                    mListener.finish();
                }
            }
        };
    }

    //开始倒计时
    public void start(){
        mButton.setEnabled(false);
        mCountDownTimer.start();
    }

    public void setOnFinishListener(OnFinishListener listener){
        this.mListener = listener;
    }
    public interface OnFinishListener {
        void finish();
    }
}
