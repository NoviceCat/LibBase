package com.novice.base.uicore.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.WindowManager

/**
 * 处理小米和魅族5.0-6.0系统沉浸式状态栏为白色，字体和图标也为白色问题
 * @author novice
 */
object OSUtils {

    fun fixWhiteStatusbarBug(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (isBrandMi(activity)) { //小米MIUI系统
                setMIUIStatusBarTextMode(activity, true)
            } else if (isBrandMeizu()) {//魅族flyme系统
                setFlymeStatusBarTextMode(activity, true)
            }
        }
    }

    private fun isBrandMi(context: Context): Boolean {
        try {
            if (!TextUtils.equals(Build.BRAND.toLowerCase(), "Xiaomi".toLowerCase())) {
                return false
            } else {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo("com.xiaomi.xmsf", 0)
                return packageInfo != null && packageInfo.versionCode >= 105
            }
        } catch (throwable: Throwable) {
            return false
        }
    }

    private fun isBrandMeizu(): Boolean {
        try {
            val manufacturer = Build.MANUFACTURER
            //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
            if ("meizu".equals(manufacturer, true)) {
                return true
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    /**
     * 设置MIUI系统状态栏的文字图标颜色（MIUIV6以上）
     *
     * @param activity
     * @param isDark   状态栏文字及图标是否为深色
     * @return
     */
    private fun setMIUIStatusBarTextMode(activity: Activity, isDark: Boolean): Boolean {
        var result = false
        val window = activity.window
        if (window != null) {
            val clazz: Class<*> = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField =
                    clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (isDark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag) //状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag) //清除黑色字体
                }
                result = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (isDark) {
                        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    }
                }
            } catch (e: Exception) {
            }
        }
        return result
    }

    /**
     * 设置Flyme系统状态栏的文字图标颜色
     *
     * @param activity
     * @param isDark   状态栏文字及图标是否为深色
     * @return
     */
    private fun setFlymeStatusBarTextMode(activity: Activity, isDark: Boolean): Boolean {
        val window = activity.window
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                value = if (isDark) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {
            }
        }
        return result
    }

}