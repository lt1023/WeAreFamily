package com.wearefamily.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wearefamily.R;
import com.wearefamily.ui.user.LoginActivity;

/**
 * Created by Administrator on 2017/10/19.
 */
public class ToastUtils {

    private static final int DURATION = Toast.LENGTH_SHORT;
    private static View toastRoot;
    private static Toast toast;
    private static TextView tv;

    public static void showToast(Context context, String message) {
        if (toast == null){
            toast = new Toast(context);
            toastRoot = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
            tv = (TextView) toastRoot.findViewById(R.id.tv_toast_tips);
            toast.setView(toastRoot);
            toast.setDuration(DURATION);
        }
        tv.setText(message);
        toast.show();
    }
}
