package com.novice.base.uicore.statuslayout

import android.view.View

/**
 * 状态布局中 点击事件
 * @author novice
 *
 */
interface OnStatusRetryClickListener {

    /**
     * 布局子 View 被点击
     *
     * @param view
     * @param status 点击布局
     */
    fun onClickRetry(view: View, status: StatusLayoutType)

}