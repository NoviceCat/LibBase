package com.novice.base.uicore.view;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.novice.base.uicore.inner.ICommonViewDelegate;
import com.novice.base.uicore.statuslayout.DefaultStatusLayout;
import com.novice.base.uicore.statuslayout.OnStatusCustomClickListener;
import com.novice.base.uicore.statuslayout.OnStatusRetryClickListener;
import com.novice.base.uicore.statuslayout.StatusLayoutManager;
import com.novice.base.uicore.utils.UICoreConfig;

/**
 * 显示progressBar\statusLayout等。通用组件类实现。提供给BaseActivity和BaseFragment使用
 */
public class CommonViewDelegate implements ICommonViewDelegate {

    private LoadingProgressDialog mProgressDialog;

    public CommonViewDelegate(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new LoadingProgressDialog(context);
            setProgressDialogCanceled(true, true);
        }
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    public void setProgressDialogCanceled(boolean touchOutside, boolean back) {
        mProgressDialog.setCanceled(touchOutside, back);
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private StatusLayoutManager statusLayoutManager;
    private StatusLayoutManager.Builder builder;

    public StatusLayoutManager getStatusLayoutManager() {
        return statusLayoutManager;
    }

    public StatusLayoutManager.Builder getStatusLayoutManagerBuilder() {
        return builder;
    }

    public void initStatusLayout(View contentView) {
        builder = new StatusLayoutManager.Builder(contentView);
        builder.setDefaultThemeColor(UICoreConfig.INSTANCE.getDefaultThemeColor());
        builder.setDefaultEmptyImg(UICoreConfig.INSTANCE.getDefaultEmptyIcon());
        builder.setDefaultLoadErrorImg(UICoreConfig.INSTANCE.getLoadErrorIcon());
        builder.setDefaultNetDisconnectImg(UICoreConfig.INSTANCE.getNetDisconnectIcon());
        builder.setDefaultLoadingLottiePath(UICoreConfig.INSTANCE.getLoadingLottie());
    }


    public void setDefaultStatusListener(OnStatusRetryClickListener listener) {

//        statusLayoutManager = new StatusLayoutManager.Builder(contentView)

        // 设置默认布局属性
//                .setDefaultEmptyText("空白了，哈哈哈哈")
//                .setDefaultEmptyImg(R.mipmap.ic_launcher)
//                .setDefaultEmptyClickViewText("retry")
//                .setDefaultEmptyClickViewTextColor(getResources().getColor(R.color.colorAccent))
//                .setDefaultEmptyClickViewVisible(false)
//
//                .setDefaultErrorText(R.string.app_name)
//                .setDefaultErrorImg(R.mipmap.ic_launcher)
//                .setDefaultErrorClickViewText("重试一波")
//                .setDefaultErrorClickViewTextColor(getResources().getColor(R.color.colorPrimaryDark))
//                .setDefaultErrorClickViewVisible(true)
//
//                .setDefaultLayoutsBackgroundColor(Color.WHITE)

        // 自定义布局
//                .setLoadingLayout(inflate(R.layout.layout_loading))
//                .setEmptyLayout(inflate(R.layout.layout_empty))
//                .setErrorLayout(inflate(R.layout.layout_error))
//
//                .setLoadingLayout(R.layout.layout_loading)
//                .setEmptyLayout(R.layout.layout_empty)
//                .setErrorLayout(R.layout.layout_error)
//
//                .setEmptyClickViewID(R.id.tv_empty)
//                .setErrorClickViewID(R.id.tv_error)

        // 设置重试事件监听器
        builder.setOnStatusRetryClickListener(listener);
    }

    public void build() {
        statusLayoutManager = builder.build();
        Log.d("status layout", "status layout builder.build statusLayoutManager " + statusLayoutManager);
    }

    public void build(StatusLayoutManager.Builder builder) {
        if (builder == null) {
            Log.e("status layout", "status layout builder is null !!!");
            return;
        }
        statusLayoutManager = builder.build();
    }

    @Override
    public void showEmptyLayout() {
        if (statusLayoutManager == null) {
            Log.e("status layout", "statusLayoutManager is null");
            return;
        }
        statusLayoutManager.showEmptyLayout();
    }

    @Override
    public void showLoadingLayout() {
        if (statusLayoutManager == null) {
            Log.e("status layout", "statusLayoutManager is null");
            return;
        }
        try {
            statusLayoutManager.showLoadingLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoadErrorLayout() {
        if (statusLayoutManager == null) {
            Log.e("status layout", "statusLayoutManager is null");
            return;
        }
        statusLayoutManager.showLoadErrorLayout();
    }

    @Override
    public void showNetDisconnectLayout() {
        if (statusLayoutManager == null) {
            Log.e("status layout", "statusLayoutManager is null");
            return;
        }
        statusLayoutManager.showNetDisconnectLayout();
    }

    @Override
    public void hideStatusLayout() {
        if (statusLayoutManager == null) {
            Log.e("status layout", "statusLayoutManager is null");
            return;
        }
        statusLayoutManager.hideStatusLayout();
    }

    @Override
    public void showCustomLayout(@LayoutRes int customLayoutID, OnStatusCustomClickListener onStatusCustomClickListener, @IdRes int... clickViewID) {
        if (statusLayoutManager == null) {
            Log.e("status layout", "statusLayoutManager is null");
            return;
        }
        statusLayoutManager.showCustomLayout(customLayoutID, onStatusCustomClickListener, clickViewID);
    }

    public DefaultStatusLayout getEmptyLayout() {
        if (statusLayoutManager == null) {
            Log.e("status layout", "statusLayoutManager is null");
            if (builder != null) {
                statusLayoutManager = builder.build();
            }
        }
        return statusLayoutManager == null ? null : (DefaultStatusLayout) statusLayoutManager.getEmptyLayout();
    }

    public DefaultStatusLayout getLoadErrorLayout() {
        if (statusLayoutManager == null) {
            Log.e("status layout", "statusLayoutManager is null");
            if (builder != null) {
                statusLayoutManager = builder.build();
            }
        }
        return statusLayoutManager == null ? null : (DefaultStatusLayout) statusLayoutManager.getLoadErrorLayout();
    }

}
