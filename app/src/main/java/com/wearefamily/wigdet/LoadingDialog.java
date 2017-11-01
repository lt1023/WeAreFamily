package com.wearefamily.wigdet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.wearefamily.R;

/**
 * Created by Administrator on 2017/10/18.
 */
public class LoadingDialog extends Dialog{
    private AlertDialog mAlertDialog;
    private Context mContext;
    public LoadingDialog(Context context) {
        super(context);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_alert,null);
        mAlertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        mAlertDialog.setCancelable(false);
    }
    public void showDialog(){
        mAlertDialog.show();
    }
    public void dissmissDialog(){
        mAlertDialog.dismiss();
    }
}
