package com.novice.base.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import com.novice.base.R

/**
 * 消息红点未读数
 * author novice
 *
 */
class MsgView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    private val gdBackground = GradientDrawable()
    private var bgColor: Int = 0
        set(value) {
            field = value
            setBgSelector()
        }
    private var cornerRadius: Int = 0
        set(value) {
            field = value
            setBgSelector()
        }
    private var strokeWidth: Int = 0
        set(value) {
            field = value
            setBgSelector()
        }
    private var strokeColor: Int = 0
        set(value) {
            field = value
            setBgSelector()
        }
    private var isRadiusHalfHeight: Boolean = false
        set(value) {
            field = value
            setBgSelector()
        }
    private var isWidthHeightEqual: Boolean = false
        set(value) {
            field = value
            setBgSelector()
        }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MsgView)
        bgColor = ta.getColor(R.styleable.MsgView_mv_backgroundColor, Color.TRANSPARENT)
        cornerRadius = ta.getDimensionPixelSize(R.styleable.MsgView_mv_cornerRadius, 0)
        strokeWidth = ta.getDimensionPixelSize(R.styleable.MsgView_mv_strokeWidth, 0)
        strokeColor = ta.getColor(R.styleable.MsgView_mv_strokeColor, Color.TRANSPARENT)
        isRadiusHalfHeight = ta.getBoolean(R.styleable.MsgView_mv_isRadiusHalfHeight, false)
        isWidthHeightEqual = ta.getBoolean(R.styleable.MsgView_mv_isWidthHeightEqual, false)
        ta.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isWidthHeightEqual && width > 0 && height > 0) {
            val max = Math.max(width, height)
            val measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY)
            super.onMeasure(measureSpec, measureSpec)
            return
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isRadiusHalfHeight) {
            cornerRadius = height / 2
        } else {
            setBgSelector()
        }
    }

    private fun setBgSelector() {
        val bg = StateListDrawable()
        setDrawable(gdBackground, bgColor, strokeColor)
        bg.addState(intArrayOf(-android.R.attr.state_pressed), gdBackground)
        background = bg
    }

    private fun setDrawable(gd: GradientDrawable, color: Int, strokeColor: Int) {
        gd.setColor(color)
        gd.cornerRadius = cornerRadius.toFloat()
        gd.setStroke(strokeWidth, strokeColor)
    }


    fun show(num: Int, isStroke: Boolean) {
        gravity = Gravity.CENTER
        val lp = layoutParams as ViewGroup.LayoutParams
        val dm = resources.displayMetrics
        visibility = View.VISIBLE
        setSingleLine()
        if (num <= 0) {//圆点,设置默认宽高
            text = ""
            lp.width = ((if (isStroke) 10 else 8) * dm.density).toInt()
            lp.height = ((if (isStroke) 10 else 8) * dm.density).toInt()
            layoutParams = lp
        } else {
            lp.height = (17 * dm.density).toInt()
            if (num > 0 && num < 10) {//圆
                lp.width = (17 * dm.density).toInt()
                text = num.toString() + ""
            } else if (num > 9 && num < 100) {//圆角矩形,圆角是高度的一半,设置默认padding
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
                setPadding((3 * dm.density).toInt(), 0, (3 * dm.density).toInt(), 0)
                text = num.toString() + ""
            } else {//数字超过两位,显示
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT
                setPadding((3 * dm.density).toInt(), 0, (3 * dm.density).toInt(), 0)
                text = "99+"
            }
            layoutParams = lp
        }
    }

}