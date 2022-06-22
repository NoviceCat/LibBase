package com.novice.base.uicore.utils

import com.novice.base.BuildConfig
import com.novice.base.uicore.inner.UICoreThrowableListener

/**
 * @author novice
 */
object UICoreConfig {

    /**
     * 异常监听
     */
    private var throwableListener: UICoreThrowableListener? = null
    var mode = BuildConfig.DEBUG//环境
    var defaultThemeColor = 0//主题色
    var defaultEmptyIcon = 0//空布局图标
    var loadErrorIcon = 0//错误布局图标
    var netDisconnectIcon = 0//网络断开图标
    var loadingLottie = ""//加载中布局动图
    var progressLottie = ""//加载中弹窗动图

    fun setCatchThrowableListener(throwableListener: UICoreThrowableListener?) {
        this.throwableListener = throwableListener
    }

    fun throwable(throwable: Throwable) {
        if (throwableListener != null) {
            throwableListener!!.catchThrowable(throwable)
        }
    }

}