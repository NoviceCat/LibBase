package com.novice.base.dialog

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SizeUtils
import com.novice.base.R
import com.novice.base.uicore.utils.UICoreConfig
import com.novice.base.utils.DoubleClickUtils
import razerdp.basepopup.BasePopupWindow

/**
 * author novice
 *
 */
class ConfirmDialog(context: Context) : BasePopupWindow(context), View.OnClickListener {

    private var listener: DialogClickListener? = null
    private var tvTitle: AppCompatTextView
    private var tvContent: AppCompatTextView
    private var tvLeft: AppCompatTextView
    private var tvRight: AppCompatTextView

    init {
        tvTitle = findViewById(R.id.tv_title)
        tvContent = findViewById(R.id.tv_content)
        tvLeft = findViewById(R.id.tv_left)
        tvRight = findViewById(R.id.tv_right)
        tvRight.setTextColor(ContextCompat.getColor(context, UICoreConfig.defaultThemeColor))
        tvLeft.setOnClickListener(this)
        tvRight.setOnClickListener(this)
    }

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popup_common_confirm)
    }

    fun setListener(listener: DialogClickListener?) {
        this.listener = listener
    }

    fun setTitleVisibility(visibility: Int?) {
        visibility?.let {
            tvTitle.visibility = it
        }
    }

    fun setTitle(titleResId: Int?) {
        titleResId?.let {
            tvTitle.setText(it)
        }
    }

    fun setTitle(title: CharSequence?) {
        title?.let {
            tvTitle.text = title
        }
    }

    fun setContentColor(color: Int?) {
        color?.let {
            tvContent.setTextColor(color)
        }
    }

    fun setContentStyle(tf: Typeface?) {
        tf?.let {
            tvContent.typeface = it
        }
    }

    fun setContentMarginTop(dpTop: Int?) {
        dpTop?.let {
            val params = tvContent.layoutParams as LinearLayout.LayoutParams
            params.setMargins(
                params.leftMargin,
                SizeUtils.dp2px(it.toFloat()),
                params.rightMargin,
                params.bottomMargin
            )
            tvContent.layoutParams = params
        }
    }

    fun setContent(content: CharSequence?) {
        content?.let {
            tvContent.text = it
        }
    }

    fun setContent(content: Int?) {
        content?.let {
            tvContent.setText(it)
        }
    }

    fun setLeftBtn(left: CharSequence?) {
        tvLeft.text = left
    }

    fun setLeftBtn(left: Int?) {
        left?.let {
            tvLeft.setText(it)
        }
    }

    fun setLeftBtnColor(@ColorRes color: Int?) {
        color?.let {
            setLeftBtnColorInt(ContextCompat.getColor(context, it))
        }
    }

    fun setLeftBtnColorInt(@ColorInt color: Int?) {
        color?.let {
            tvLeft.setTextColor(it)
        }
    }

    fun setRightBtn(right: CharSequence?) {
        tvRight.text = right
    }

    fun setRightBtn(right: Int?) {
        right?.let {
            tvRight.setText(it)
        }
    }

    fun setRightBtnColor(@ColorRes color: Int?) {
        color?.let {
            setRightBtnColorInt(ContextCompat.getColor(context, it))
        }
    }

    fun setRightBtnColorInt(@ColorInt color: Int?) {
        color?.let {
            tvRight.setTextColor(it)
        }
    }

    override fun onClick(v: View) {
        if (DoubleClickUtils.instance.isInvalidClick()) return
        when (v.id) {
            R.id.tv_left -> {
                dismiss()
                listener?.onLeftBtnClick(v)
            }
            R.id.tv_right -> {
                dismiss()
                listener?.onRightBtnClick(v)
            }
            else -> {}
        }
    }


}