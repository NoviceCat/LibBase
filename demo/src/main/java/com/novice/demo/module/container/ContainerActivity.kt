package com.novice.demo.module.container

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.novice.base.uicore.statuslayout.StatusLayoutType
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.databinding.ActivityContainerBinding

class ContainerActivity : BaseActivity<ActivityContainerBinding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ContainerActivity::class.java))
        }
    }

    override fun statusLayoutRetry(view: View, status: StatusLayoutType) {
        if (status == StatusLayoutType.STATUS_LOAD_ERROR) {
            hideStatusLayout()
            showToast("隐藏了错误布局")
        }
    }

    override fun initView() {
        setToolBarTitle("指定StatusLayout覆盖区域")
        mBinding.tvContent.setOnClickListener {
            showLoadErrorLayout()
        }
    }

}
