package com.dev.kh.connectionsever.helper.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.dev.kh.connectionsever.R;

/*
 * Created by ChanYouvita on 7/9/16.
 */
public class BizLoading {
    private Context mContext;
    private Dialog mProgressDialog;
    private AnimationDrawable mFrameAnimation;

    public BizLoading(Context context) {
        mContext = context;
    }

    public void showProgressDialog() {

        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!((Activity) mContext).isFinishing()) {

                    if (mProgressDialog == null) {
                        View loadingView = LayoutInflater.from(mContext).inflate(R.layout.comm_loading_activity, null);;

                        mProgressDialog = new Dialog(mContext);
                        mProgressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        mProgressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mProgressDialog.setContentView(loadingView);
                        mProgressDialog.setCanceledOnTouchOutside(false);
                    }

                    mProgressDialog.show();
                }
            }
        });
    }

    public void dismissProgressDialog() {

        if (mProgressDialog== null) {
            return;
        }

        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
