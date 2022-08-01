package com.novice.demo.module.dialog

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.novice.base.dialog.CommonAlertDialog
import com.novice.base.dialog.DialogClickListener
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.databinding.ActivityCommonAlertDialogBinding

class CommonAlertDialogActivity :
    BaseActivity<ActivityCommonAlertDialogBinding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CommonAlertDialogActivity::class.java))
        }
    }


    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView() {
        setToolBarTitle("CommonAlertDialog")
        mBinding.btn1.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.Confirm
                title = "标题"
                content = "我是内容"
                leftBtnText = "左边按钮"
                rightBtnText = "右边按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onLeftBtnClick(view: View) {
                        showToast("点击了左边按钮")
                    }

                    override fun onRightBtnClick(view: View) {
                        showToast("点击了右边按钮")
                    }
                }
            }.show()
        }
        mBinding.btn2.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.Confirm
                content = "我是内容"
                leftBtnText = "左边按钮"
                rightBtnText = "右边按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onLeftBtnClick(view: View) {
                        showToast("点击了左边按钮")
                    }

                    override fun onRightBtnClick(view: View) {
                        showToast("点击了右边按钮")
                    }
                }
            }.show()
        }
        mBinding.btn3.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.SINGLE
                title = "标题"
                content = "我是内容"
                confirmBtnText = "确定按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onConfirmBtnClick(view: View) {
                        showToast("点击了确定按钮")
                    }
                }
            }.show()
        }
        mBinding.btn4.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.SINGLE
                content = "我是内容"
                confirmBtnText = "确定按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onConfirmBtnClick(view: View) {
                        showToast("点击了确定按钮")
                    }
                }
            }.show()
        }
        mBinding.btn5.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.Edit
                title = "标题"
                hint = "编辑框提示语"
                leftBtnText = "左边按钮"
                rightBtnText = "右边按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onLeftBtnClick(view: View) {
                        showToast("点击了左边按钮")
                    }

                    override fun onRightEditBtnClick(view: View, content: String?) {
                        showToast("点击了右边按钮")
                    }

                }
            }.show()
        }
        mBinding.btn6.setOnClickListener {
            CommonAlertDialog(this).apply {
                type = CommonAlertDialog.DialogType.Edit
                hint = "编辑框提示语"
                leftBtnText = "左边按钮"
                rightBtnText = "右边按钮"
                listener = object : DialogClickListener.DefaultLisener() {
                    override fun onLeftBtnClick(view: View) {
                        showToast("点击了左边按钮")
                    }

                    override fun onRightEditBtnClick(view: View, content: String?) {
                        showToast("点击了右边按钮")
                    }
                }
            }.show()
        }
    }

}
