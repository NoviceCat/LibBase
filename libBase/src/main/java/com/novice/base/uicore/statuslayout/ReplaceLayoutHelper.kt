package com.novice.base.uicore.statuslayout

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull

/**
 * 替换布局帮助类
 * @author novice
 *
 */
class ReplaceLayoutHelper(@NonNull contentLayout: View) {

    /**
     * 需要替换的 View
     */
    private val contentLayout: View

    /**
     * contentLayout 的布局参数
     */
    private var params: ViewGroup.LayoutParams? = null

    /**
     * contentLayout 的父 ViewGroup
     */
    private var parentLayout: ViewGroup? = null

    /**
     * contentLayout 在 parentLayout 中的位置
     */
    private var viewIndex = 0

    /**
     * 当前显示的 View
     */
    private var currentLayout: View? = null

    init {
        this.contentLayout = contentLayout
        getContentLayoutParams()
    }

    /**
     * 获取 contentLayout 的参数信息 LayoutParams、Parent
     */
    private fun getContentLayoutParams() {
        this.params = contentLayout.layoutParams
        val parent = contentLayout.parent
        if (parent != null) {
            // 有直接的父控件
            this.parentLayout = parent as ViewGroup
        } else {
            // 认为 contentLayout 是 activity 的跟布局
            // 所以它的父控件就是 android.R.id.content
            this.parentLayout = contentLayout.rootView.findViewById(android.R.id.content)
        }
        if (parentLayout == null) {
            // 以上两种方法还没有获取到父控件
            // contentLayout 非 activity 的跟布局
            // 父控件就是自己
            if (contentLayout is ViewGroup) {
                parentLayout = contentLayout
                this.viewIndex = 0
            } else {
                // 否则，contentLayout 是一个非 ViewGroup 的跟布局
                // 该情况，没有办法替换布局，因此不支持
                throw IllegalStateException("参数错误：StatusLayoutManager#Build#with() 方法，不能传如一个非 ViewGroup 的跟布局")
            }
        } else {
            val count: Int = parentLayout!!.childCount
            for (index in 0 until count) {
                if (contentLayout == parentLayout!!.getChildAt(index)) {
                    // 获取 contentLayout 在 parentLayout 中的位置
                    this.viewIndex = index
                    break
                }
            }
        }
        this.currentLayout = contentLayout
        Log.d("status layout", "status layout getContentLayoutParams currentLayout:$currentLayout,parentLayout:$parentLayout;viewIndex:$viewIndex")
    }

    fun showStatusLayout(view: View?) {
        if (view == null) {
            Log.e("status layout", "showStatusLayout  view is null")
            return
        }
        if (currentLayout != view) {
            currentLayout = view
            val parent = view.parent
            // 去除 view 的 父 view，才能添加到别的 ViewGroup 中
            if (parent != null){
                val viewGroup = parent as ViewGroup
                viewGroup.removeView(view)
            }
            // 替换 = 移除 + 添加
            parentLayout!!.removeViewAt(viewIndex)
            parentLayout!!.addView(view, viewIndex, params)
        } else {
            Log.e("status layout", "showStatusLayout  currentLayout == view $view")
        }
        Log.d("status layout", "status layout show currentLayout:$currentLayout,view:$view;viewIndex:$viewIndex")
    }

    fun restoreLayout() {
        showStatusLayout(contentLayout)
    }

}