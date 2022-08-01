package com.novice.demo.module.tablayout

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.novice.base.adapter.PagerFragmentAdapter
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.databinding.ActivityTablayoutBinding

class TabLayoutActivity : BaseActivity<ActivityTablayoutBinding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TabLayoutActivity::class.java))
        }
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView() {
        setToolBarTitle("TabLayout+ViewPager")
        setToolBarBottomLineVisible(true)
        val size = 6
        val titles = arrayListOf<String>()
        val fragments = arrayListOf<Fragment>()
        for (index in 1..size) {
            titles.add("标题${index}")
            if (index % 2 == 0) {
                fragments.add(IncomeListFragment())
            } else {
                fragments.add(ExpendListFragment())
            }
        }
        val adapter = PagerFragmentAdapter<Fragment>(supportFragmentManager, titles)
        adapter.setFragments(fragments)
        mBinding.viewPager.adapter = adapter
        mBinding.viewPager.offscreenPageLimit = size//这边必须设置limit，否则切换Fragment再回到对应Fragment数据会丢失
        mBinding.tabLayout.setViewPager(mBinding.viewPager)
    }

}
