package com.novice.demo.module.widget

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.novice.base.glide.GlideUtils
import com.novice.base.manager.LinearLayoutManagerWrap
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.R
import com.novice.demo.databinding.ActivityWidgetBinding
import com.novice.demo.module.loadingview.LoadingViewActivity
import com.novice.demo.module.roundimageview.ShapealeImageViewActivity
import com.novice.demo.module.swipe.SwipeMenuActivity
import com.novice.demo.module.tablayout.TabLayoutActivity

class WidgetActivity : BaseActivity<ActivityWidgetBinding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, WidgetActivity::class.java))
        }
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView() {
        GlideUtils.load(this, R.drawable.banner, mBinding.imgBanner)
        val adapter = WidgetAdapter()
        val list = mutableListOf(
            "EasySwipeMenuLayout",
            "TabLayout+ViewPager",
            "LoadingView",
            "ShapeableImageView"
        )
        mBinding.recyclerView.adapter = adapter
        mBinding.recyclerView.layoutManager = LinearLayoutManagerWrap(this)
        adapter.setNewInstance(list)
        adapter.setOnItemClickListener { _, _, position ->
            when (adapter.getItem(position)) {
                "DotView" -> {
                }
                "WheelView" -> {
                }
                "EasySwipeMenuLayout" -> {
                    SwipeMenuActivity.start(this)
                }
                "TabLayout+ViewPager" -> {
                    TabLayoutActivity.start(this)
                }
                "LoadingView" -> {
                    LoadingViewActivity.start(this)
                }
                "MsgView" -> {
                }
                "ToggleButton" -> {
                }
                "ShapeableImageView" -> {
                    ShapealeImageViewActivity.start(this)
                }
            }
        }
    }

}
