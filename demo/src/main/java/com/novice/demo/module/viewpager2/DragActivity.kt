package com.novice.demo.module.viewpager2

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.R
import com.novice.demo.databinding.ActivityInteriorBinding

class DragActivity : BaseActivity<ActivityInteriorBinding, DefaultViewModel>() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, DragActivity::class.java))
        }
    }

    override fun enabledVisibleToolBar() = false

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, VRightFragment.newInstance(666), "").commitNow()
    }

}