package com.novice.demo.module.tablayout

import androidx.lifecycle.ViewModelProvider
import com.novice.base.net.CommonResponse
import com.novice.base.uicore.ui.SimpleListFragment
import com.novice.base.uicore.viewmodel.SimpleListViewModel
import com.novice.demo.module.list.TestRepository
import com.novice.demo.module.tablayout.adapter.ExpendListAdapter

/**
 * @author novice
 */
class ExpendListFragment :
    SimpleListFragment<String, ExpendListAdapter, ExpendListFragment.ExpendListViewModel>() {

    override fun initAdapter(): ExpendListAdapter {
        return ExpendListAdapter()
    }

    override fun initViewModel(): ExpendListViewModel {
        return ViewModelProvider(this).get(ExpendListViewModel::class.java)
    }

    class ExpendListViewModel : SimpleListViewModel<String>() {
        override suspend fun requestData(
            offset: Int,
            length: Int
        ): CommonResponse<MutableList<String>> {
            return TestRepository.getTestList(offset, length)
        }
    }

}