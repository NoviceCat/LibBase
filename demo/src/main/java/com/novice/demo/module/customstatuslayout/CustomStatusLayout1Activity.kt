package com.novice.demo.module.customstatuslayout

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.novice.base.uicore.statuslayout.StatusLayoutManager
import com.novice.base.uicore.statuslayout.StatusLayoutType
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.R
import com.novice.demo.databinding.ActivityCustomStatusLayout1Binding

class CustomStatusLayout1Activity :
    BaseActivity<ActivityCustomStatusLayout1Binding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CustomStatusLayout1Activity::class.java))
        }
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
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
        }
    }

    override fun buildCustomStatusLayoutView(builder: StatusLayoutManager.Builder): StatusLayoutManager.Builder {
        builder.setDefaultEmptyImg(R.drawable.ic_status_layout_load_empty1)
        builder.setDefaultEmptyText("我是自定义空布局内容")
        builder.setDefaultLoadErrorImg(R.drawable.ic_status_layout_load_failure1)
        builder.setDefaultLoadErrorText("我是自定义错误布局内容")
        builder.setDefaultNetDisconnectImg(R.drawable.ic_status_layout_net_disconnect1)
        builder.setDefaultNetDisconnectText("我是自定义网络断开布局内容")
        builder.setDefaultThemeColor(R.color.color_FFBD01)
        builder.setDefaultStatusTextColor(R.color.white)
        builder.setDefaultLayoutsBackgroundResource(R.drawable.custom_bg)
        return builder
    }

    override fun initView() {
        setToolBarTitle("自定义StatusLayout")
        mBinding.btnEmpty.setOnClickListener {
            showEmptyLayout()
        }
        mBinding.btnEmpty.setOnClickListener {
            showLoadErrorLayout()
        }
        mBinding.btnNetDisconnect.setOnClickListener {
            showNetDisconnectLayout()
        }
    }
}
