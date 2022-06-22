package com.novice.base.uicore.ui

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.novice.base.uicore.inner.SimpleListContract
import com.novice.base.uicore.statuslayout.StatusLayoutType
import com.novice.base.uicore.viewmodel.SimpleListViewModel

/**
 * 简单列表
 * @author novice
 *
 */
abstract class SimpleListFragment<T, A : BaseQuickAdapter<T, *>, VM : SimpleListViewModel<T>> :
    BaseListFragment<T, A, VM>(),
    SimpleListContract.SimpleListView<T> {

    override fun statusLayoutRetry(view: View, status: StatusLayoutType) {
        hideStatusLayout()
        autoRefresh()
    }

    override fun registerUIChangeLiveDataCallBack() {
        viewModel.loadCompleteEvent.observe(viewLifecycleOwner) {
            loadComplete()
        }
        viewModel.showErrorEvent.observe(viewLifecycleOwner) {
            showError()
        }
        viewModel.showEmptyEvent.observe(viewLifecycleOwner) {
            showEmpty()
        }
        viewModel.showNoMoreEvent.observe(viewLifecycleOwner) {
            showNoMore()
        }
        viewModel.pushDataEvent.observe(viewLifecycleOwner) { t ->
            pushData(t)
        }
    }

    override fun initLazyData() {
        autoRefresh()
    }

    override fun doHttpRequest(isRefresh: Boolean) {
        viewModel.getData(isRefresh)
    }

    override fun pushData(list: MutableList<T>) {
        setAdapterData(list)
    }

    override fun showEmpty() {
        if (isRefresh()) {
            setNewData(null)
            showEmptyLayout()
        } else {
            showNoMore()
        }
    }

    override fun showError() {
        if (isRefresh()) {
            if (adapter.data.size == 0) {
                setNewData(null)
                showLoadErrorLayout()
            } else {
                showNoMore()
            }
        } else {
            loadMoreFail()
        }
    }

    override fun showNoMore() {
        loadComplete()
        loadMoreEnd()
    }
}