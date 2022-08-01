package com.novice.demo.module.viewpager2

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.novice.base.uicore.ui.BaseFragment
import com.novice.base.utils.Extra
import com.novice.demo.R
import com.novice.demo.databinding.FragmentRightBinding
import com.novice.demo.module.list.ListActivity
import com.novice.demo.module.viewpager2.praiseview.PraiseCallback

/**
 * @author novice
 */
class VRightFragment : BaseFragment<FragmentRightBinding, ViewPagerViewModel>() {

    companion object {
        fun newInstance(liveID: Int): VRightFragment {
            return VRightFragment().apply {
                val bundle = Bundle()
                bundle.putInt(Extra.arg1, liveID)
                arguments = bundle
            }
        }
    }

    override fun initViewModel(): ViewPagerViewModel {
        return ViewModelProvider(this).get(ViewPagerViewModel::class.java)
    }


    override fun initView() {
        val liveID = arguments?.getInt(Extra.arg1) ?: 0
        mBinding.button.text = liveID.toString()
        mBinding.dragConstraintLayout.addDragView({ v ->
            when (v.id) {
                R.id.drag_view1 -> {
                    ToastUtils.showLong("我被点击了Drag1")
                }
                R.id.drag_view2 -> {
                    ToastUtils.showLong("我被点击了Drag2")
                }
            }
        }, mBinding.dragView1, mBinding.dragView2)
        mBinding.button.setOnClickListener {
//            SharePop(context!!).showPopupWindow()
            mBinding.button.visibility = View.GONE
        }
        val adapter = ListActivity.ListAdapter()
        adapter.setNewInstance(
            mutableListOf(
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20"
            )
        )
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.adapter = adapter

        mBinding.praiseView.clickCallback = object : PraiseCallback {
            override fun clickAction() {
                if (isViewDestroyed()) return
                mBinding.periscope.addHeart()
            }

            override fun clickComplete(count: Int) {
                if (isViewDestroyed()) return
            }

        }
    }

    override fun registerUIChangeLiveDataCallBack() {
        super.registerUIChangeLiveDataCallBack()
        viewModel.updateItemEvent.observe(viewLifecycleOwner, Observer {
            mBinding.button.text = it.toString()
        })
    }

    override fun initData() {
        viewModel.updateItem()
    }


}