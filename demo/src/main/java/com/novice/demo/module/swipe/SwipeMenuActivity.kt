package com.novice.demo.module.swipe

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.base.widget.swipe.State
import com.novice.demo.databinding.ActivitySwipeMenuBinding

class SwipeMenuActivity : BaseActivity<ActivitySwipeMenuBinding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SwipeMenuActivity::class.java))
        }
    }

    override fun initView() {
        mBinding.btnOpen.setOnClickListener {
            mBinding.swipeMenu.handlerSwipeMenu(State.RIGHTOPEN)
        }
        mBinding.btnClose.setOnClickListener {
            mBinding.swipeMenu.handlerSwipeMenu(State.CLOSE)
        }
        mBinding.imgDelete.setOnClickListener {
            showToast("delete")
        }

    }

}
