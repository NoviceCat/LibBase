package com.novice.demo.module.viewpager2

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.customview.widget.ViewDragHelper
import org.greenrobot.eventbus.EventBus


/**
 * @author novice
 */
class DragConstraintLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private var callback: OnClickListener? = null
    private var viewDragHelper: ViewDragHelper
    private var hasDx = false
    private var hasDy = false
    private var views: Array<out View>? = null
    private var dragView: View? = null

    init {
        viewDragHelper = ViewDragHelper.create(this, 1F, ViewDragCallback())
    }

    fun addDragView(callback: OnClickListener, vararg views: View) {
        this.callback = callback
        this.views = views
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        var isCanDrag = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (containerChildUnder(event.x, event.y)) {
                    isCanDrag = true
                    hasDx = false
                    hasDy = false
                }
            }
        }
        if (isCanDrag) {
            EventBus.getDefault().post(EventMessage(1))
            viewDragHelper.processTouchEvent(event)
            return super.onInterceptTouchEvent(event)
        } else {
            return viewDragHelper.shouldInterceptTouchEvent(event)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper.processTouchEvent(event)
        return true
    }

    /**
     * 手势是否在被拖拽view的范围里面
     */
    private fun containerChildUnder(x: Float, y: Float): Boolean {
        if (views.isNullOrEmpty()) return false
        for (view in views!!) {
            if (x >= view.left && x < view.right && y >= view.top && y < view.bottom) {
                dragView = view
                return true
            }
        }
        return false
    }

    private inner class ViewDragCallback : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            //child 表示想要滑动的view
            //pointerId 表示触摸点的id, 比如多点按压的那个id
            //返回值表示,是否可以capture,也就是是否可以滑动.可以根据不同的child决定是否可以滑动
            if (views == null) return false
            for (view in views!!) {
                if (child == view) {
                    return true
                }
            }
            return false
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            hasDx = true
            //控制左边的拖曳距离，不能越界。
            //当拖曳的距离超过左边的padding值，也意味着child view越界，复位
            //默认的padding值=0
            val paddingleft = paddingLeft
            if (left < paddingleft) {
                return paddingleft
            }

            //这里是控制右边的拖曳边缘极限位置。
            //假设pos的值刚好是子view child右边边缘与父view的右边重合的情况
            //pos值即为一个极限的最右边位置，超过也即意味着拖曳越界：越出右边的界限，复位。
            //可以再加一个paddingRight值，缺省的paddingRight=0，所以即便不加也在多数情况正常可以工作
            val pos = width - child.width - paddingRight
            if (left > pos) {
                return pos
            }

            //其他情况属于在范围内的拖曳，直接返回系统计算默认的left即可
            return left
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            hasDy = true
            //控制子view拖曳不能超过最顶部
            val paddingTop = paddingTop
            if (top < paddingTop) {
                return paddingTop
            }

            //控制子view不能越出底部的边界。
            val pos = height - child.height - paddingBottom
            if (top > pos) {
                return pos
            }

            //其他情况正常，直接返回Android系统计算的top即可。
            return top
        }

        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            if (dragView != null) {
                //拖拽状态发生变化时重新设置Margin位置，不然拖拽控件在onlayout时候（受recyclerview滚动影响等情况）会回归到最初的位置
                val params = dragView!!.layoutParams as LayoutParams
                params.topToTop = LayoutParams.UNSET
                params.startToStart = LayoutParams.UNSET
                params.endToEnd = LayoutParams.PARENT_ID
                params.bottomToBottom = LayoutParams.PARENT_ID
                params.rightMargin = measuredWidth - dragView!!.right
                params.bottomMargin = measuredHeight - dragView!!.bottom
                dragView!!.layoutParams = params
                if (state == ViewDragHelper.STATE_IDLE) {
                    EventBus.getDefault().post(EventMessage(2))
                    if (!hasDx && !hasDy && dragView!!.visibility == View.VISIBLE) {
                        //停止拖拽的时候如果坐标没变则当做点击事件处理
                        callback?.onClick(dragView)
                    }
                }
            }
        }
    }


}