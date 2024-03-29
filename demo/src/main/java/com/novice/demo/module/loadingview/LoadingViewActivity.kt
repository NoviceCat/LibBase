package com.novice.demo.module.loadingview

import android.content.Context
import android.content.Intent
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.databinding.ActivityLoadingViewBinding

class LoadingViewActivity : BaseActivity<ActivityLoadingViewBinding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoadingViewActivity::class.java))
        }
    }

    override fun enabledVisibleToolBar(): Boolean {
        return false
    }

    override fun initView() {
        setToolBarTitle("LoadingView")
        mBinding.loadingView.start()
    }

}
