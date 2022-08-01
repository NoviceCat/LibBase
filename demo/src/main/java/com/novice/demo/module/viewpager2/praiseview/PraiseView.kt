package com.novice.demo.module.viewpager2.praiseview

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.blankj.utilcode.util.ClickUtils
import com.novice.demo.R

/**
 * @author novice
 */
class PraiseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    private var limitTime = 300L
    private var curCount = 0
    var clickCallback: PraiseCallback? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_praise, this, true)

        val imgRose = findViewById<ImageView>(R.id.img_rose)
        val pbCircle = findViewById<CircleProgressBar>(R.id.cicle_progress_bar)
        setOnClickListener(object : ClickUtils.OnMultiClickListener(100, limitTime) {
            override fun onTriggerClick(v: View) {
            }

            override fun onBeforeTriggerClick(v: View, count: Int) {
                clickCallback?.clickAction()
                curCount = count
                imgRose.visibility = View.GONE
                pbCircle.visibility = View.VISIBLE
                pbCircle.updateCount(curCount)
                postDelayed({
                    if (curCount == count) {
                        if (curCount == 1) {
                            pbCircle.visibility = View.GONE
                            imgRose.visibility = View.VISIBLE
                            clickCallback?.clickComplete(1)
                        } else {
                            isEnabled = false
                            isClickable = false
                            pbCircle.setProgress(360, 1500, object : Animator.AnimatorListener {
                                override fun onAnimationRepeat(animation: Animator?) {
                                }

                                override fun onAnimationEnd(animation: Animator?) {
                                    pbCircle.visibility = View.GONE
                                    imgRose.visibility = View.VISIBLE
                                    clickCallback?.clickComplete(curCount)
                                    isEnabled = true
                                    isClickable = true
                                }

                                override fun onAnimationCancel(animation: Animator?) {
                                }

                                override fun onAnimationStart(animation: Animator?) {
                                }

                            })
                        }
                    }
                }, limitTime)
            }
        })
    }
}