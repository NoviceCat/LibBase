package com.novice.demo.module.viewpager2

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.novice.base.uicore.ui.BaseFragment
import com.novice.base.utils.Extra
import com.novice.demo.databinding.FragmentLeftBinding

/**
 * @author novice
 */
class VLeftFragment : BaseFragment<FragmentLeftBinding, ViewPagerViewModel>() {

    companion object {
        fun newInstance(liveID: Int): VLeftFragment {
            return VLeftFragment().apply {
                val bundle = Bundle()
                bundle.putInt(Extra.arg1, liveID)
                arguments = bundle
            }
        }
    }

    override fun initView() {
        val liveID = arguments?.getInt(Extra.arg1) ?: 0
        mBinding.tvTitle.text = liveID.toString()
    }

    override fun registerUIChangeLiveDataCallBack() {
        viewModel.updateItemEvent.observe(viewLifecycleOwner, Observer {
            mBinding.tvTitle.text = it.toString()
        })
    }

    override fun initData() {
        viewModel.updateItem()
    }

}