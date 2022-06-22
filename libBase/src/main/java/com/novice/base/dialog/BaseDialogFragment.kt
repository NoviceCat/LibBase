package com.novice.base.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.novice.base.R
import com.novice.base.uicore.utils.BindingReflex

open class BaseDialogFragment<VB : ViewBinding> : DialogFragment(), IBaseDialog {

    var TAG = javaClass.simpleName

    override fun getWidth() = ViewGroup.LayoutParams.MATCH_PARENT
    override fun getHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
    override fun canOutsideTouchToCancel() = false
    override fun canCancel() = true
    override fun getGravity() = Gravity.CENTER
    override fun getAnimationStyle() = R.style.BaseDialogFadeAnimation
    override fun initView() {}

    private var _binding: VB? = null

    protected val mBinding get() = _binding!!

    var isDialogDismiss = true

    override fun onCreateView(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (getAnimationStyle() != 0) {
            dialog!!.window!!.setWindowAnimations(getAnimationStyle())
        } else {
            dialog!!.window!!.setWindowAnimations(R.style.BaseDialogFadeAnimation)
        }
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(canCancel())
        dialog!!.setCanceledOnTouchOutside(canOutsideTouchToCancel())

        val params = dialog!!.window!!.attributes
        params.width = if (getWidth() == 0) ViewGroup.LayoutParams.MATCH_PARENT else getWidth()
        params.height = if (getHeight() == 0) ViewGroup.LayoutParams.WRAP_CONTENT else getHeight()
        params.gravity = if (getGravity() == 0) Gravity.CENTER else getGravity()
        dialog!!.window!!.attributes = params
        initView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BaseDialogFragment)
    }

    fun show(manager: FragmentManager) {
        show(manager, TAG)
        isDialogDismiss = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isDialogDismiss = true
    }

    override fun dismiss() {
        super.dismiss()
        isDialogDismiss = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isDialogDismiss = true
    }


}