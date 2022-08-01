package com.novice.demo.module.tablayout

import androidx.lifecycle.ViewModelProvider
import com.novice.base.net.CommonResponse
import com.novice.base.uicore.ui.SimpleListFragment
import com.novice.base.uicore.viewmodel.SimpleListViewModel
import com.novice.demo.module.list.TestRepository
import com.novice.demo.module.tablayout.adapter.IncomeListAdapter

/**
 * @author novice
 */
class IncomeListFragment : SimpleListFragment<String, IncomeListAdapter, IncomeListFragment.IncomeListViewModel>() {

    override fun initAdapter(): IncomeListAdapter {
        return IncomeListAdapter()
    }

    override fun initViewModel(): IncomeListViewModel {
        return ViewModelProvider(this).get(IncomeListViewModel::class.java)
    }

    class IncomeListViewModel : SimpleListViewModel<String>() {

        override suspend fun requestData(offset: Int, length: Int): CommonResponse<MutableList<String>> {
            return TestRepository.getTestList(offset, length)
        }
    }

}