package com.novice.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * 是否可以滚动+临界值回调
 * @author novice
 */
class CustomScrollViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs) {

    private var isCanScroll = true
    private var startX = 0          //开始点击的位置
    private var criticalValue = 200 //临界值
    private var onSideListener: OnSideListener? = null

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (isCanScroll) {
            //允许滑动则应该调用父类的方法
            super.onTouchEvent(ev)
        } else {
            //禁止滑动则不做任何操作，直接返回true即可
            true
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> startX = ev.x.toInt()
            MotionEvent.ACTION_MOVE -> {
                if (startX - ev.x > criticalValue && currentItem == adapter!!.count - 1) {
                    if (isCanScroll) {
                        onSideListener?.onRightSide()
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (isCanScroll) super.onInterceptTouchEvent(arg0) else false
    }

    //设置是否允许滑动，true是可以滑动，false是禁止滑动
    fun setIsCanScroll(isCanScroll: Boolean) {
        this.isCanScroll = isCanScroll
    }

    fun setOnSideListener(listener: OnSideListener?) {
        onSideListener = listener
    }

    interface OnSideListener {
        fun onRightSide()       //右边界回调
    }

}