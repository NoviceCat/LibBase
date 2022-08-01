package com.novice.demo.module.web.js

/**
 * @author novice
 *
 */
interface JSBridgeLifeCycleListener {

    fun setTitle(title: String?)

    fun setBarRightText(text: String?)

    fun setBarRightIcon(icon: String?)

    /**
     * 显示进度dialog
     */
    fun showProgressDialog()

    /**
     * 隐藏进度dialog
     */
    fun hideProgressDialog()

    /**
     * Toast
     */
    fun showToast(msg: String?)

    /**
     * 关闭页面
     */
    fun finish()


}