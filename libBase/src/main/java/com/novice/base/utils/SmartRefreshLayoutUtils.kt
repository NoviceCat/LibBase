package com.novice.base.utils

import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout


/**
 * author novice
 *
 */
object SmartRefreshLayoutUtils {

    fun init() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(
                android.R.color.white,
                android.R.color.white
            )//全局设置主题颜色
            ClassicsHeader(context).setTimeFormat(DynamicTimeFormat("更新于 %s"))//指定为经典Header，默认是 贝塞尔雷达Header
        }
    }

}