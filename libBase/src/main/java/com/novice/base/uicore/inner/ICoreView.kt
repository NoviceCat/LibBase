package com.novice.base.uicore.inner

/**
 * ViewModel与View的契约接口
 * @author novice
 */
interface ICoreView {

    /**
     * 显示进度dialog
     */
    fun showProgressDialog()

    /**
     * 隐藏进度dialog
     */
    fun hideProgressDialog()


    /**
     * 调用status layout，显示空
     */
    fun showEmptyLayout()

    /**
     * 调用status layout，显示加载中...
     */
    fun showLoadingLayout()

    /**
     * 调用status layout，显示加载失败
     */
    fun showLoadErrorLayout()

    /**
     * 调用status layout，显示网络断开
     */
    fun showNetDisconnectLayout()

    /**
     * 隐藏status layout
     */
    fun hideStatusLayout()

    /**
     * 吐司
     */
    fun showToast(msg: String?)

    /**
     * 关闭Activity
     */
    fun finishAc()

}