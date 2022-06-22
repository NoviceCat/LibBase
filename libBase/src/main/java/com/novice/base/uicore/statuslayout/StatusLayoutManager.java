package com.novice.base.uicore.statuslayout;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.airbnb.lottie.LottieAnimationView;
import com.novice.base.R;

/**
 * 状态布局管理器
 * author novice
 *
 */
public class StatusLayoutManager {

    private ReplaceLayoutHelper replaceLayoutHelper;

    private LayoutInflater inflater;

    private OnStatusRetryClickListener onStatusRetryClickListener;


    private View contentLayout;

    private int defaultBackgroundColor;
    private int defaultBackgroundResource;

    /**
     * loading
     */
    @LayoutRes
    private int loadingLayoutID;
    private View loadingLayout;
    private String loadingLottiePath;

    /**
     * load empty
     */
    @LayoutRes
    private int loadEmptyLayoutID;
    private View loadEmptyLayout;
    private String loadEmptyText;
    @DrawableRes
    private int loadEmptyImgID;

    /**
     * load error
     */
    @LayoutRes
    private int loadErrorLayoutID;
    private View loadErrorLayout;
    private String loadErrorText;
    @DrawableRes
    private int loadErrorImgID;

    /**
     * net disconnect
     */
    @LayoutRes
    private int netDisconnectLayoutID;
    private View netDisconnectLayout;
    private String netDisconnectText;
    @DrawableRes
    private int netDisconnectImgID;

    /**
     * default theme
     */
    @ColorRes
    private int defaultThemeColor;

    @ColorRes
    private int defaultStatusTextColor;

    private StatusLayoutManager(Builder builder) {
        this.contentLayout = builder.contentLayout;

        //loading
        this.loadingLayoutID = builder.loadingLayoutID;
        this.loadingLayout = builder.loadingLayout;
        this.loadingLottiePath = builder.loadingLottiePath;

        //load empty
        this.loadEmptyLayoutID = builder.loadEmptyLayoutID;
        this.loadEmptyLayout = builder.loadEmptyLayout;
        this.loadEmptyText = builder.loadEmptyText;
        this.loadEmptyImgID = builder.loadEmptyImgID;

        //load error
        this.loadErrorLayoutID = builder.loadErrorLayoutID;
        this.loadErrorLayout = builder.loadErrorLayout;
        this.loadErrorText = builder.loadErrorText;
        this.loadErrorImgID = builder.loadErrorImgID;

        //net disconnect
        this.netDisconnectLayoutID = builder.netDisconnectLayoutID;
        this.netDisconnectLayout = builder.netDisconnectLayout;
        this.netDisconnectText = builder.netDisconnectText;
        this.netDisconnectImgID = builder.netDisconnectImgID;

        //default themeColor
        this.defaultThemeColor = builder.defaultThemeColor;
        this.defaultStatusTextColor = builder.defaultStatusTextColor;

        this.defaultBackgroundColor = builder.defaultBackgroundColor;
        this.defaultBackgroundResource = builder.defaultBackgroundResource;

        this.onStatusRetryClickListener = builder.onStatusRetryClickListener;

        this.replaceLayoutHelper = new ReplaceLayoutHelper(contentLayout);

    }

    private View inflate(@LayoutRes int resource) {
        if (inflater == null) {
            inflater = LayoutInflater.from(contentLayout.getContext());
        }
        return inflater.inflate(resource, null);
    }

    ///////////////////////////////////////////
    /////////////////原有布局////////////////////
    ///////////////////////////////////////////

    /**
     * 显示原有布局
     */
    public void hideStatusLayout() {
        replaceLayoutHelper.restoreLayout();
    }

    ///////////////////////////////////////////
    ////////////////加载中布局///////////////////
    ///////////////////////////////////////////

    /**
     * 创建加载中布局
     */
    private void createLoadingLayout() {
        if (loadingLayout == null) {
            loadingLayout = inflate(loadingLayoutID);
            LottieAnimationView lottieLoading = loadingLayout.findViewById(R.id.lottie_loading);
            lottieLoading.setAnimation(loadingLottiePath);
        }
        if (defaultBackgroundColor > 0) {
            loadingLayout.setBackgroundColor(defaultBackgroundColor);
        } else if (defaultBackgroundResource > 0) {
            loadingLayout.setBackgroundResource(defaultBackgroundResource);
        }
    }

    /**
     * 获取加载中布局
     *
     * @return 加载中布局
     */
    public View getLoadingLayout() {
        createLoadingLayout();
        return loadingLayout;
    }

    /**
     * 显示加载中布局
     */
    public void showLoadingLayout() {
        createLoadingLayout();
        Log.d("status layout", "status layout showLoadingLayout:" + loadingLayout);
        replaceLayoutHelper.showStatusLayout(loadingLayout);
    }

    ///////////////////////////////////////////
    ////////////////空数据布局///////////////////
    ///////////////////////////////////////////

    /**
     * 创建空数据布局
     */
    private void createLoadEmptyLayout() {
        if (loadEmptyLayout == null) {
            loadEmptyLayout = inflate(loadEmptyLayoutID);
        }

        Log.d("status layout", "status layout create showLoadingLayout !isDefaultLayout :" + !isDefaultLayout(loadEmptyLayout) + ";loadEmptyText:" + loadEmptyText + ";loadEmptyImgID:" + loadEmptyImgID);
        //不是默认的布局
        if (!isDefaultLayout(loadEmptyLayout)) {
            loadEmptyLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRetryClick(loadEmptyLayout);
                }
            });
            return;
        }
        setDefaultCommonView(loadEmptyLayout, loadEmptyText, loadEmptyImgID, defaultThemeColor, defaultStatusTextColor, true);
    }


    /**
     * 获取空数据布局
     *
     * @return 空数据布局
     */
    public View getEmptyLayout() {
        createLoadEmptyLayout();
        return loadEmptyLayout;
    }

    /**
     * 显示空数据布局
     */
    public void showEmptyLayout() {
        createLoadEmptyLayout();
        Log.d("status layout", "status layout showEmptyLayout:" + loadEmptyLayout);
        replaceLayoutHelper.showStatusLayout(loadEmptyLayout);
    }

    ///////////////////////////////////////////
    /////////////////加载出错布局////////////////////
    ///////////////////////////////////////////


    /**
     * 创建出错布局
     */
    private void createLoadErrorLayout() {
        if (loadErrorLayout == null) {
            loadErrorLayout = inflate(loadErrorLayoutID);
        }

        //不是默认的布局
        if (!isDefaultLayout(loadErrorLayout)) {
            loadErrorLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRetryClick(loadErrorLayout);
                }
            });
            return;
        }
        setDefaultCommonView(loadErrorLayout, loadErrorText, loadErrorImgID, defaultThemeColor, defaultStatusTextColor, true);
    }

    /**
     * 获取出错布局
     *
     * @return 出错布局
     */
    public View getLoadErrorLayout() {
        createLoadErrorLayout();
        return loadErrorLayout;
    }

    /**
     * 显示出错布局
     */
    public void showLoadErrorLayout() {
        createLoadErrorLayout();
        Log.d("status layout", "status layout showLoadErrorLayout:" + loadErrorLayout);
        replaceLayoutHelper.showStatusLayout(loadErrorLayout);
    }


    ///////////////////////////////////////////
    /////////////////网络未连接布局////////////////////
    ///////////////////////////////////////////

    /**
     * 创建出错布局
     */
    private void createNetDisconnectLayout() {
        if (netDisconnectLayout == null) {
            netDisconnectLayout = inflate(netDisconnectLayoutID);
        }
        if (!isDefaultLayout(netDisconnectLayout)) {
            return;
        }
        setDefaultCommonView(netDisconnectLayout, netDisconnectText, netDisconnectImgID, defaultThemeColor, defaultStatusTextColor, true);
    }


    /**
     * 获取出错布局
     *
     * @return 出错布局
     */
    public View getNetDisconnectLayout() {
        createNetDisconnectLayout();
        return netDisconnectLayout;
    }

    /**
     * 显示出错布局
     */
    public void showNetDisconnectLayout() {
        createNetDisconnectLayout();
        replaceLayoutHelper.showStatusLayout(netDisconnectLayout);
    }


    private boolean isDefaultLayout(View view) {
        return view instanceof DefaultStatusLayout;
    }

    private View setDefaultCommonView(View view, String text, int imgId, int defaultThemeColor, int defaultStatusTextColor, boolean viewClick) {
        if (defaultBackgroundColor > 0) {
            ((DefaultStatusLayout) view).setDefaultBackgroundColor(defaultBackgroundColor);
        } else if (defaultBackgroundResource > 0) {
            ((DefaultStatusLayout) view).setDefaultBackgroundResource(defaultBackgroundResource);
        }
        // 设置默认空数据布局的提示文本
        if (!TextUtils.isEmpty(text)) {
            ((DefaultStatusLayout) view).setTipText(text);
            Log.d("status layout", "status layout setDefaultCommonView setTipText:" + text);
        }

        // 设置默认空数据布局的图片
        if (imgId > 0) {
            ((DefaultStatusLayout) view).setImage(imgId);
            Log.d("status layout", "status layout setDefaultCommonView setImage:" + imgId);
        }

        //设置默认的刷新按钮背景颜色
        if (defaultThemeColor > 0) {
            ((DefaultStatusLayout) view).setRefreshBackgroundColor(defaultThemeColor);
            Log.d("status layout", "status layout setDefaultCommonView setRefreshBackgroundColor:" + defaultThemeColor);
        }

        //设置默认的状态描述字体颜色
        if (defaultStatusTextColor > 0) {
            ((DefaultStatusLayout) view).setTipTextColor(defaultStatusTextColor);
            Log.d("status layout", "status layout setDefaultCommonView setDefaultStatusTextColor:" + defaultStatusTextColor);
        }

        if (view == loadErrorLayout) {
            ((DefaultStatusLayout) view).setVisibleRefresh(true);
            if (viewClick) {
                ((DefaultStatusLayout) view).setErrorClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRetryClick(view);
                    }
                });
            }
        } else if (view == loadEmptyLayout) {
            ((DefaultStatusLayout) view).setVisibleRefresh(false);
            if (viewClick) {
                ((DefaultStatusLayout) view).setEmptyClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRetryClick(view);
                    }
                });
            }
        } else if (view == netDisconnectLayout) {
            ((DefaultStatusLayout) view).setVisibleRefresh(true);
            if (viewClick) {
                ((DefaultStatusLayout) view).setErrorClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRetryClick(view);
                    }
                });
            }
        }
        return view;
    }

    private void setRetryClick(View view) {
        if (onStatusRetryClickListener != null) {
            StatusLayoutType status = StatusLayoutType.STATUS_DEFAULT;
            if (view == loadErrorLayout) {
                status = StatusLayoutType.STATUS_LOAD_ERROR;
            } else if (view == loadEmptyLayout) {
                status = StatusLayoutType.STATUS_EMPTY;
            } else if (view == netDisconnectLayout) {
                status = StatusLayoutType.STATUS_NET_DISCONNECT_ERROR;
            }
            onStatusRetryClickListener.onClickRetry(view, status);
        }
    }

    ///////////////////////////////////////////
    ////////////////自定义布局///////////////////
    ///////////////////////////////////////////

    /**
     * 显示自定义状态布局
     *
     * @param customLayout 自定义布局
     */
    public void showCustomLayout(@NonNull View customLayout) {
        replaceLayoutHelper.showStatusLayout(customLayout);
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayoutID 自定义状态布局 ID
     * @return 通过 customLayoutID 生成的 View
     */
    public View showCustomLayout(@LayoutRes int customLayoutID) {
        View customerView = inflate(customLayoutID);
        showCustomLayout(customerView);
        return customerView;
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayout 自定义布局
     * @param clickViewID  可点击 View ID
     */
    public void showCustomLayout(@NonNull View customLayout, final OnStatusCustomClickListener onStatusCustomClickListener, @IdRes int... clickViewID) {
        replaceLayoutHelper.showStatusLayout(customLayout);
        if (onStatusCustomClickListener == null) {
            return;
        }

        for (int aClickViewID : clickViewID) {
            View clickView = customLayout.findViewById(aClickViewID);
            if (clickView == null) {
                return;
            }

            // 设置点击按钮点击时事件回调
            clickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStatusCustomClickListener.onCustomClick(view);
                }
            });
        }
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayoutID 自定义布局 ID
     * @param clickViewID    点击按钮 ID
     */
    public View showCustomLayout(@LayoutRes int customLayoutID, OnStatusCustomClickListener listener, @IdRes int... clickViewID) {
        View customLayout = inflate(customLayoutID);
        showCustomLayout(customLayout, listener, clickViewID);
        return customLayout;
    }

    public static final class Builder {

        private final Resources mResources;
        private View contentLayout;
        @ColorRes
        private int defaultBackgroundColor;
        @DrawableRes
        private int defaultBackgroundResource;

        /**
         * loading
         */
        @LayoutRes
        private int loadingLayoutID;
        private View loadingLayout;
        private String loadingLottiePath;

        /**
         * load empty
         */
        @LayoutRes
        private int loadEmptyLayoutID;
        private View loadEmptyLayout;
        private String loadEmptyText;
        @DrawableRes
        private int loadEmptyImgID;

        /**
         * load error
         */
        @LayoutRes
        private int loadErrorLayoutID;
        private View loadErrorLayout;
        private String loadErrorText;
        @DrawableRes
        private int loadErrorImgID;

        /**
         * net disconnect
         */
        @LayoutRes
        private int netDisconnectLayoutID;
        private View netDisconnectLayout;
        private String netDisconnectText;
        @DrawableRes
        private int netDisconnectImgID;

        /**
         * default theme
         */
        @ColorRes
        private int defaultThemeColor;

        @ColorRes
        private int defaultStatusTextColor;

        private OnStatusRetryClickListener onStatusRetryClickListener;


        public OnStatusRetryClickListener getOnStatusRetryClickListener() {
            return onStatusRetryClickListener;
        }

        /**
         * 创建状态布局 Build 对象
         *
         * @param contentLayout 原有布局，内容布局
         */
        public Builder(@NonNull View contentLayout) {
            this.contentLayout = contentLayout;
            mResources = contentLayout.getContext().getResources();
            // 设置默认布局
            this.loadingLayoutID = R.layout.layout_status_layout_manager_loading;
            this.loadEmptyLayoutID = R.layout.status_layout_default_load_empty;
            this.loadErrorLayoutID = R.layout.status_layout_default_load_error;
            this.netDisconnectLayoutID = R.layout.status_layout_default_net_disconnect;
        }

        /**
         * 设置默认布局的背景颜色，包括加载中、空数据和出错布局
         *
         * @param defaultBackgroundColor 默认布局的背景颜色
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultLayoutsBackgroundColor(int defaultBackgroundColor) {
            this.defaultBackgroundColor = defaultBackgroundColor;
            return this;
        }

        /**
         * 设置默认布局的背景颜色，包括加载中、空数据和出错布局
         *
         * @param defaultBackgroundResource 默认布局的背景资源图片
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultLayoutsBackgroundResource(@DrawableRes int defaultBackgroundResource) {
            this.defaultBackgroundResource = defaultBackgroundResource;
            return this;
        }

        /**
         * 设置点击事件监听器
         *
         * @param listener 点击事件监听器
         * @return 状态布局 Build 对象
         */
        public Builder setOnStatusRetryClickListener(OnStatusRetryClickListener listener) {
            this.onStatusRetryClickListener = listener;
            return this;
        }

        ///////////////////////////////////////////
        ////////////////加载中布局///////////////////
        ///////////////////////////////////////////

        /**
         * 设置加载中布局
         *
         * @param loadingLayoutID 加载中布局 ID
         * @return 状态布局 Build 对象
         */
        public Builder setLoadingLayout(@LayoutRes int loadingLayoutID) {
            this.loadingLayoutID = loadingLayoutID;
            return this;
        }

        /**
         * 设置加载中布局
         *
         * @param loadingLayout 加载中布局
         * @return 状态布局 Build 对象
         */
        public Builder setLoadingLayout(@NonNull View loadingLayout) {
            this.loadingLayout = loadingLayout;
            return this;
        }

        /**
         * 设置默认加载中布局提Gif
         *
         * @param loadingLottiePath 加载中布局Lottie路径
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultLoadingLottiePath(String loadingLottiePath) {
            this.loadingLottiePath = loadingLottiePath;
            return this;
        }

        ///////////////////////////////////////////
        ////////////////空数据布局///////////////////
        ///////////////////////////////////////////

        /**
         * 设置空数据布局
         *
         * @param emptyLayoutResId 空数据布局 ID
         * @return 状态布局 Build 对象
         */
        public Builder setEmptyLayout(@LayoutRes int emptyLayoutResId) {
            this.loadEmptyLayoutID = emptyLayoutResId;
            return this;
        }

        /**
         * 设置空数据布局
         *
         * @param emptyLayout 空数据布局
         * @return 状态布局 Build 对象
         */
        public Builder setEmptyLayout(@NonNull View emptyLayout) {
            this.loadEmptyLayout = emptyLayout;
            return this;
        }


        /**
         * 设置空数据布局图片
         *
         * @param emptyImgID 空数据布局图片 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyImg(@DrawableRes int emptyImgID) {
            this.loadEmptyImgID = emptyImgID;
            return this;
        }

        /**
         * 设置空数据布局提示文本
         *
         * @param emptyText 空数据布局提示文本
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyText(String emptyText) {
            this.loadEmptyText = emptyText;
            return this;
        }

        /**
         * 设置空数据布局提示文本
         *
         * @param emptyTextStrID 空数据布局提示文本 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultEmptyText(@StringRes int emptyTextStrID) {
            this.loadEmptyText = mResources.getString(emptyTextStrID);
            return this;
        }

        ///////////////////////////////////////////
        /////////////////加载数据出错布局////////////////////
        ///////////////////////////////////////////

        /**
         * 设置加载数据出错布局
         *
         * @param errorLayoutResId 出错布局 ID
         * @return 状态布局 Build 对象
         */
        public Builder setLoadErrorLayout(@LayoutRes int errorLayoutResId) {
            this.loadErrorLayoutID = errorLayoutResId;
            return this;
        }

        /**
         * 设置加载数据出错布局
         *
         * @param errorLayout 出错布局
         * @return 状态布局 Build 对象
         */
        public Builder setLoadErrorLayout(@NonNull View errorLayout) {
            this.loadErrorLayout = errorLayout;
            return this;
        }


        /**
         * 设置出错布局提示文本
         *
         * @param errorText 出错布局提示文本
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultLoadErrorText(String errorText) {
            this.loadErrorText = errorText;
            return this;
        }

        /**
         * 设置出错布局提示文本
         *
         * @param errorTextStrID 出错布局提示文本 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultLoadErrorText(@StringRes int errorTextStrID) {
            this.loadErrorText = mResources.getString(errorTextStrID);
            return this;
        }


        /**
         * 设置出错布局图片
         *
         * @param errorImgID 出错布局图片 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultLoadErrorImg(@DrawableRes int errorImgID) {
            this.loadErrorImgID = errorImgID;
            return this;
        }

        ///////////////////////////////////////////
        /////////////////网络未连接布局////////////////////
        ///////////////////////////////////////////

        /**
         * 设置网络未连接布局
         *
         * @param disconnectLayoutResId 布局 ID
         * @return 状态布局 Build 对象
         */
        public Builder setNetDisconnectLayout(@LayoutRes int disconnectLayoutResId) {
            this.netDisconnectLayoutID = disconnectLayoutResId;
            return this;
        }

        /**
         * 设置网络未连接布局
         *
         * @param disconnectrLayout 布局
         * @return 状态布局 Build 对象
         */
        public Builder setNetDisconnectLayout(@NonNull View disconnectrLayout) {
            this.netDisconnectLayout = disconnectrLayout;
            return this;
        }


        /**
         * 设置布局提示文本
         *
         * @param disconnectText 提示文本
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultNetDisconnectText(String disconnectText) {
            this.netDisconnectText = disconnectText;
            return this;
        }

        /**
         * 设置布局提示文本
         *
         * @param disconnectTextStrID 提示文本 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultNetDisconnectText(@StringRes int disconnectTextStrID) {
            this.netDisconnectText = mResources.getString(disconnectTextStrID);
            return this;
        }


        /**
         * 设置出错布局图片
         *
         * @param imgID 出错布局图片 ID
         * @return 状态布局 Build 对象
         */
        public Builder setDefaultNetDisconnectImg(@DrawableRes int imgID) {
            this.netDisconnectImgID = imgID;
            return this;
        }

        ///////////////////////////////////////////
        /////////////////默认主题色////////////////////
        ///////////////////////////////////////////
        public Builder setDefaultThemeColor(@ColorRes int defaultThemeColor) {
            this.defaultThemeColor = defaultThemeColor;
            return this;
        }

        public Builder setDefaultStatusTextColor(@ColorRes int defaultStatusTextColor) {
            this.defaultStatusTextColor = defaultStatusTextColor;
            return this;
        }


        /**
         * 创建状态布局管理器
         *
         * @return 状态布局管理器
         */
        @NonNull
        public StatusLayoutManager build() {
            return new StatusLayoutManager(this);
        }

    }

}
