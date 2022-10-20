package com.novice.demo.module.viewpager2

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.novice.base.adapter.PagerFragmentAdapter
import com.novice.base.uicore.ui.BaseFragment
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.base.utils.Extra
import com.novice.base.view.CustomScrollViewPager
import com.novice.demo.databinding.FragmentViewpager2Binding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author novice
 */
class ViewPager2Fragment : BaseFragment<FragmentViewpager2Binding,DefaultViewModel>() {

    companion object {
        fun newInstance(liveID: Int, position: Int): ViewPager2Fragment {
            return ViewPager2Fragment().apply {
                val bundle = Bundle()
                bundle.putInt(Extra.arg1, liveID)
                bundle.putInt(Extra.POSITION, position)
                arguments = bundle
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventMessage(message: EventMessage) {
        if (message.code == 1) {
            mBinding.customViewpager.setIsCanScroll(false)
        } else if (message.code == 2) {
            mBinding.customViewpager.setIsCanScroll(true)
        }
    }

    override fun initView() {
        val liveID = arguments?.getInt(Extra.arg1) ?: 0
        val position = arguments?.getInt(Extra.POSITION) ?: 0
        mBinding.tvContent.text = liveID.toString()
        EventBus.getDefault().register(this)
        initViewPager(position)
    }

    private fun initViewPager(position: Int) {
        val fragments = arrayListOf<Fragment>()
        val fragment1 = VLeftFragment.newInstance(1)
        val fragment2 = VRightFragment.newInstance(2)
        fragments.add(fragment1)
        fragments.add(fragment2)
        val adapter = PagerFragmentAdapter<Fragment>(childFragmentManager, ArrayList())
        adapter.setFragments(fragments)
        mBinding.customViewpager.adapter = adapter
        mBinding.customViewpager.currentItem = 1
        mBinding.customViewpager.setOnSideListener(object : CustomScrollViewPager.OnSideListener {
            override fun onRightSide() {
                mBinding.drawerLayout.openDrawer(GravityCompat.END)
            }

        })
        mBinding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
                EventBus.getDefault().post(EventMessage(2))
            }

            override fun onDrawerOpened(drawerView: View) {
                val activity = activity as ViewPager2Activity
                if (position == activity.getCurPosition() && !activity.isFakeDragging()) {
                    EventBus.getDefault().post(EventMessage(1))
                }
            }

        })
    }

    override fun initLazyData() {
        val last = (1..100).shuffled().last()
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

}