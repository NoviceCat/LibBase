package com.novice.demo.module.viewpager2.praiseview

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import com.blankj.utilcode.util.SizeUtils
import com.novice.demo.R


/**
 * @author novice
 */
class CircleProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val paintOuterProgressBackColor: Paint
    private val paintOuterProgressForeBackColor: Paint
    private val paintInnerProgressColor: Paint
    private val paintText: Paint
    private val cicleOuterWidth: Float
    private var cicleFrontRect: RectF? = null
    private var curProgress = 0
    private var count: Int? = null
    private val textRect = Rect()

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.CicleProgressBar)
        cicleOuterWidth = SizeUtils.dp2px(2F).toFloat()
        val outerProgressBackColor = attr.getColor(R.styleable.CicleProgressBar_outer_progress_back_color, Color.parseColor("#1AFFFFFF"))
        val outerProgressForeColor = attr.getColor(R.styleable.CicleProgressBar_outer_progress_fore_color, Color.parseColor("#F9DC53"))
        val innerProgressColor = attr.getColor(R.styleable.CicleProgressBar_inner_progress_color, Color.parseColor("#801F0D33"))
        val progressTextColor = attr.getColor(R.styleable.CicleProgressBar_progress_text_color, Color.parseColor("#F9DC53"))
        val progressTextSize = attr.getDimension(R.styleable.CicleProgressBar_progress_text_size, SizeUtils.dp2px(7F).toFloat())
        attr.recycle()

        paintOuterProgressBackColor = Paint().apply {
            color = outerProgressBackColor
            style = Paint.Style.STROKE
            strokeWidth = cicleOuterWidth
            isAntiAlias = true//启用抗锯齿
        }
        paintOuterProgressForeBackColor = Paint().apply {
            color = outerProgressForeColor
            style = Paint.Style.STROKE
            strokeWidth = cicleOuterWidth
            strokeCap = Paint.Cap.ROUND//设置画笔为椭圆
            isAntiAlias = true//启用抗锯齿
        }
        paintInnerProgressColor = Paint().apply {
            color = innerProgressColor
            style = Paint.Style.FILL
            isAntiAlias = true//启用抗锯齿
        }
        paintText = Paint().apply {
            color = progressTextColor
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            isAntiAlias = true//启用抗锯齿
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC)
            textSize = progressTextSize
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.translate(width / 2f, height / 2f)
        canvas.drawCircle(0f, 0f, width / 2f, paintInnerProgressColor)
        canvas.drawCircle(0f, 0f, width / 2f - cicleOuterWidth / 2f, paintOuterProgressBackColor)
        if (curProgress > 0) {
            if (cicleFrontRect == null) {
                cicleFrontRect = RectF(
                    -width / 2f + cicleOuterWidth / 2f, -width / 2f + cicleOuterWidth / 2f,
                    width / 2f - cicleOuterWidth / 2f, width / 2f - cicleOuterWidth / 2f
                )
            }
            canvas.drawArc(cicleFrontRect!!, -90f, curProgress.toFloat(), false, paintOuterProgressForeBackColor)
        }
        count?.let {
            textRect.set((-width / 2f).toInt(), (-height / 2f).toInt(), (width / 2f).toInt(), (height / 2f).toInt())
            val lFontMetrics = paintText.fontMetrics
            val lBasTop = lFontMetrics.top
            val lBaseBottom = lFontMetrics.bottom
            val lBaseLineY = textRect.centerY() - lBasTop / 2 - lBaseBottom / 2
            canvas.drawText("×${count}", textRect.centerX().toFloat(), lBaseLineY, paintText)
        }
    }

    fun updateCount(count: Int) {
        curProgress = 0
        this.count = count
        post {
            invalidate()
        }
    }

    /**
     * 设置当前进度，并展示进度动画。如果动画时间小于等于0，则不展示动画
     *
     * @param progress 当前进度（0-100）
     * @param animTime 动画时间（毫秒）
     */
    fun setProgress(progress: Int, animTime: Long, listener: Animator.AnimatorListener) {
        curProgress = 0
        val animator = ValueAnimator.ofInt(curProgress, progress)
        animator.addUpdateListener { animation ->
            curProgress = animation.animatedValue as Int
            invalidate()
        }
        animator.addListener(listener)
        animator.interpolator = OvershootInterpolator()
        animator.duration = animTime
        animator.start()
    }

}