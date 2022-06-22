package com.novice.base.uicore.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.novice.base.R;
import com.novice.base.uicore.utils.UICoreConfig;

/**
 * 加载中dialog
 * author novice
 */
public class LoadingProgressDialog extends ProgressDialog {

    private boolean onTouchOutsideCanceled = true;
    private boolean backCanceled = true;

    public LoadingProgressDialog(Context context) {
        this(context, R.style.CustomDialog);
    }

    public LoadingProgressDialog(Context context, int theme) {
        super(context, theme);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    private void init() {
        setCancel();
        setContentView(R.layout.dialog_common_progress);
        LottieAnimationView lottieProgress = findViewById(R.id.lottie_progress);
        lottieProgress.setAnimation(UICoreConfig.INSTANCE.getProgressLottie());
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
    }

    private void setCancel() {
        setCancelable(backCanceled);
        setCanceledOnTouchOutside(onTouchOutsideCanceled);
    }

    /**
     * @param onTouchOutside 点击空白区域
     * @param back           返回键
     */
    public void setCanceled(boolean onTouchOutside, boolean back) {
        this.onTouchOutsideCanceled = onTouchOutside;
        this.backCanceled = back;
    }

    public void show() {
        setCancel();
        super.show();
    }

}
