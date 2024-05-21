package com.novice.demo.module.statuslayout

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.novice.base.uicore.statuslayout.StatusLayoutType
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.databinding.ActivityStatuslayoutBinding

class StatusLayoutActivity : BaseActivity<ActivityStatuslayoutBinding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, StatusLayoutActivity::class.java))
        }
    }

    override fun statusLayoutRetry(view: View, status: StatusLayoutType) {
        when (status) {
            StatusLayoutType.STATUS_EMPTY -> {
                hideStatusLayout()
                showToast("隐藏了空布局")
            }
            StatusLayoutType.STATUS_LOAD_ERROR -> {
                hideStatusLayout()
                showToast("隐藏了错误布局")
            }
            StatusLayoutType.STATUS_NET_DISCONNECT_ERROR -> {
                hideStatusLayout()
                showToast("隐藏了网络断开布局")
            }
            else -> {}
        }
    }

    override fun initView() {
        setToolBarTitle("StatusLayout")
        mBinding.btnLoading.setOnClickListener {
            showLoadingLayout()
        }
        mBinding.btnEmpty.setOnClickListener {
            showEmptyLayout()
        }
        mBinding.btnError.setOnClickListener {
            showLoadErrorLayout()
        }
        mBinding.btnNetDisconnect.setOnClickListener {
            showNetDisconnectLayout()
        }
        mBinding.btnDialog.setOnClickListener {
            showProgressDialog()
        }
    }

}
