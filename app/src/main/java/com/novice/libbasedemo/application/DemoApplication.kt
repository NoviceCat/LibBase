package com.novice.libbasedemo.application

import com.blankj.utilcode.util.LogUtils
import com.novice.base.BuildConfig
import com.tencent.bugly.Bugly
import com.novice.base.application.Core
import com.novice.base.glide.GlideUtils
import com.novice.base.net.init.CoroutinesLoadUtils
import com.novice.base.uicore.utils.UICoreConfig
import com.novice.libbasedemo.R

class DemoApplication : Core() {

    override fun onCreate() {
        super.onCreate()
        initUICoreConfig()
        CoroutinesLoadUtils.init()
    }

    private fun initUICoreConfig() {
        UICoreConfig.let {
            it.mode = BuildConfig.DEBUG
            it.setCatchThrowableListener(CatchUICoreThrowableImpl())
            it.defaultThemeColor = R.color.common_theme_color
            it.defaultEmptyIcon = R.drawable.ic_status_layout_load_empty
            it.loadErrorIcon = R.drawable.ic_status_layout_load_failure
            it.netDisconnectIcon = R.drawable.ic_status_layout_net_disconnect
            it.loadingLottie = "commonLoadingLottie.zip"
            it.progressLottie = "commonProgressLottie.json"
        }
        Bugly.init(this, Constant.BUGLY.APP_ID, true)
        GlideUtils.initConfig(R.drawable.ic_placeholer, R.drawable.ic_placeholer)
        LogUtils.getConfig().isLogSwitch = BuildConfig.DEBUG
    }

}