package com.novice.demo.module.viewpager2

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.databinding.ActivityViewPager2Binding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ViewPager2Activity : BaseActivity<ActivityViewPager2Binding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ViewPager2Activity::class.java))
        }
    }

    override fun enabledVisibleToolBar() = false

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventMessage(message: EventMessage) {
        if (message.code == 1) {
            mBinding.viewpager2.isUserInputEnabled = false
        } else if (message.code == 2) {
            mBinding.viewpager2.isUserInputEnabled = true
        }
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        mBinding.viewpager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        mBinding.viewpager2.offscreenPageLimit = 1
        val list = mutableListOf<Int>()
        list.add(100)
        list.add(101)
        list.add(102)
        list.add(103)
        list.add(104)
        list.add(105)
        list.add(106)
        list.add(107)
        list.add(108)
        list.add(109)
        list.add(110)
        mBinding.viewpager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return list.size
            }

            override fun createFragment(position: Int): Fragment {
                val item = list[position]
                return ViewPager2Fragment.newInstance(item, position)
            }

            override fun getItemId(position: Int): Long {
                return list[position].toLong()
            }

            override fun containsItem(itemId: Long): Boolean {
                return list.any {
                    it.toLong() == itemId
                }
            }

        }
    }

    fun isFakeDragging(): Boolean {
        return mBinding.viewpager2.isFakeDragging
    }

    fun getCurPosition(): Int {
        return mBinding.viewpager2.currentItem
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

}
