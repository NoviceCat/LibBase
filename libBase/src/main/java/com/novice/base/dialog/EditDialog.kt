package com.novice.base.dialog

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SizeUtils
import com.novice.base.R
import com.novice.base.uicore.utils.UICoreConfig
import com.novice.base.utils.DoubleClickUtils
import razerdp.basepopup.BasePopupWindow

/**
 * @author novice
 */
class EditDialog(context: Context) : BasePopupWindow(context), View.OnClickListener {

    private var listener: DialogClickListener? = null
    private var tvTitle: AppCompatTextView
    private var etContent: AppCompatEditText
    private var tvLeft: AppCompatTextView
    private var tvRight: AppCompatTextView

    init {
        setAdjustInputMethod(true)
        tvTitle = findViewById(R.id.tv_title)
        etContent = findViewById(R.id.et_content)
        tvLeft = findViewById(R.id.tv_left)
        tvRight = findViewById(R.id.tv_right)
        tvRight.setTextColor(ContextCompat.getColor(context, UICoreConfig.defaultThemeColor))
        tvLeft.setOnClickListener(this)
        tvRight.setOnClickListener(this)
    }

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popup_common_edit)
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
            tvTitle.text = it
        }
    }

    fun setContentColor(color: Int?) {
        color?.let {
            etContent.setTextColor(it)
        }
    }

    fun setContentStyle(tf: Typeface?) {
        tf?.let {
            etContent.typeface = it
        }
    }

    fun setContentMarginTop(dpTop: Int?) {
        dpTop?.let {
            val params = etContent.layoutParams as LinearLayout.LayoutParams
            params.setMargins(
                params.leftMargin,
                SizeUtils.dp2px(it.toFloat()),
                params.rightMargin,
                params.bottomMargin
            )
            etContent.layoutParams = params
        }
    }

    fun setContent(content: CharSequence?) {
        content?.let {
            etContent.setText(it)
        }
    }

    fun setContent(content: Int?) {
        content?.let {
            etContent.setText(it)
        }
    }

    fun setHint(hint: CharSequence?) {
        hint?.let {
            etContent.hint = it
        }
    }

    fun setHint(hint: Int?) {
        hint?.let {
            etContent.setHint(it)
        }
    }

    fun setLeftBtn(left: CharSequence?) {
        left?.let {
            tvLeft.text = it
        }
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
        right?.let {
            tvRight.text = it
        }
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
                listener?.onRightEditBtnClick(v, etContent.text.toString().trimEnd())
            }
            else -> {}
        }
    }

}