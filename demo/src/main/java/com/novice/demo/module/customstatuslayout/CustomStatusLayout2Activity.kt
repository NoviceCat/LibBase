package com.novice.demo.module.customstatuslayout

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.novice.base.uicore.statuslayout.OnStatusCustomClickListener
import com.novice.base.uicore.statuslayout.StatusLayoutManager
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.R
import com.novice.demo.databinding.ActivityCustomStatusLayout2Binding

class CustomStatusLayout2Activity :
    BaseActivity<ActivityCustomStatusLayout2Binding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CustomStatusLayout2Activity::class.java))
        }
    }

    override fun buildCustomStatusLayoutView(builder: StatusLayoutManager.Builder): StatusLayoutManager.Builder {
        val emptyView =
            LayoutInflater.from(this).inflate(R.layout.view_custom_empty_status_layout, null)
        val errorView =
            LayoutInflater.from(this).inflate(R.layout.view_custom_error_status_layout, null)
        val netDisconnectView =
            LayoutInflater.from(this).inflate(R.layout.view_custom_netdisconnet_status_layout, null)
        val imgContent = emptyView.findViewById<AppCompatImageView>(R.id.img_content)
        val btnErrorRefresh = errorView.findViewById<MaterialButton>(R.id.btn_refresh)
        val btnNetDisconnectRefresh =
            netDisconnectView.findViewById<MaterialButton>(R.id.btn_refresh)
        builder.setEmptyLayout(emptyView)
        builder.setLoadErrorLayout(errorView)
        builder.setNetDisconnectLayout(netDisconnectView)
        imgContent.setOnClickListener {
            hideStatusLayout()
            showToast("隐藏了空布局")
        }
        btnErrorRefresh.setOnClickListener {
            hideStatusLayout()
            showToast("隐藏了错误布局")
        }
        btnNetDisconnectRefresh.setOnClickListener {
            hideStatusLayout()
            showToast("隐藏了网络断开布局")
        }
        return builder
    }

    override fun initView() {
        setToolBarTitle("完全自定义StatusLayout")
        mBinding.btnEmpty.setOnClickListener {
            showEmptyLayout()
        }
        mBinding.btnError.setOnClickListener {
            showLoadErrorLayout()
        }
        mBinding.btnNetDisconnect.setOnClickListener {
            showNetDisconnectLayout()
        }
        mBinding.btnCustom.setOnClickListener {
            showCustomLayout(R.layout.view_custom_status_layout, object :
                OnStatusCustomClickListener {
                override fun onCustomClick(view: View) {
                    when (view.id) {
                        R.id.btn1 -> {
                            hideStatusLayout()
                            showToast("隐藏了自定义布局")
                        }
                        R.id.btn2 -> {
                            showToast("我是点击事件")
                        }
                        else -> {}
                    }
                }
            }, R.id.btn1, R.id.btn2)
        }
    }

}
