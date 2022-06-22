package com.novice.base.uicore.inner

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.novice.base.uicore.statuslayout.OnStatusCustomClickListener

/**
 * baseActivity and baseFragment 使用，共用的view component
 * @author novice
 */
interface ICommonViewDelegate {


    //---------- progress dialog--------------
    /**
     * 显示数据加载进度条
     *
     */
    fun showProgressDialog()

    /**
     * 隐藏数据加载进度条
     */
    fun hideProgressDialog()


    //---------- status layout--------------
    /**
     * 显示空的layout
     */
    fun showEmptyLayout()

    /**
     * 显示加载中的layout
     */
    fun showLoadingLayout()

    /**
     * 显示加载错误的layout
     */
    fun showLoadErrorLayout()

    /**
     * 显示网络断开的layout
     */
    fun showNetDisconnectLayout()

    /**
     * 隐藏statuslayout
     */
    fun hideStatusLayout()

    fun showCustomLayout(@LayoutRes customLayoutID: Int, onStatusCustomClickListener: OnStatusCustomClickListener?, @IdRes vararg clickViewID: Int)

}