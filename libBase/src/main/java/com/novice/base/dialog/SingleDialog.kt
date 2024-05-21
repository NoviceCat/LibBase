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
class SingleDialog(context: Context) : BasePopupWindow(context), View.OnClickListener {

    private var listener: DialogClickListener? = null
    private var tvTitle: AppCompatTextView
    private var tvContent: AppCompatTextView
    private var tvOk: AppCompatTextView

    init {
        tvTitle = findViewById(R.id.tv_title)
        tvContent = findViewById(R.id.tv_content)
        tvOk = findViewById(R.id.tv_ok)
        tvOk.setTextColor(ContextCompat.getColor(context, UICoreConfig.defaultThemeColor))
        tvOk.setOnClickListener(this)
    }

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popup_common_single)
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

    fun setConfirmBtn(right: CharSequence?) {
        tvOk.text = right
    }

    fun setConfirmBtn(right: Int?) {
        right?.let {
            tvOk.setText(it)
        }
    }

    fun setConfirmBtnColor(@ColorRes color: Int?) {
        color?.let {
            setConfirmBtnColorInt(ContextCompat.getColor(context, it))
        }
    }

    fun setConfirmBtnColorInt(@ColorInt color: Int?) {
        color?.let {
            tvOk.setTextColor(it)
        }
    }

    override fun onClick(v: View) {
        if (DoubleClickUtils.instance.isInvalidClick()) return
        when (v.id) {
            R.id.tv_ok -> {
                dismiss()
                listener?.onConfirmBtnClick(v)
            }
            else -> {}
        }
    }


}