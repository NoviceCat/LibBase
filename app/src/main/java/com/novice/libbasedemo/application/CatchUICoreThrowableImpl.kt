package com.novice.libbasedemo.application

import com.tencent.bugly.crashreport.CrashReport
import com.novice.base.BuildConfig
import com.novice.base.uicore.inner.UICoreThrowableListener
import com.novice.base.uicore.utils.UICatchException

/**
 * uicore异常捕获
 * debug环境 :throw exception 并且上报到bugly
 * release环境：不抛异常，只上报到bugly
 * @author novice
 */
class CatchUICoreThrowableImpl :UICoreThrowableListener{

    override fun catchThrowable(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            throw UICatchException(throwable)
        }
        CrashReport.postCatchedException(throwable)
    }
}