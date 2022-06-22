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
abstract class SimpleListActivity<T, A : BaseQuickAdapter<T, *>, VM : SimpleListViewModel<T>> :
    BaseListActivity<T, A, VM>(),
    SimpleListContract.SimpleListView<T> {

    override fun statusLayoutRetry(view: View, status: StatusLayoutType) {
        hideStatusLayout()
        autoRefresh()
    }

    override fun registerUIChangeLiveDataCallBack() {
        viewModel.loadCompleteEvent.observe(this) {
            loadComplete()
        }
        viewModel.showErrorEvent.observe(this) {
            showError()
        }
        viewModel.showEmptyEvent.observe(this) {
            showEmpty()
        }
        viewModel.showNoMoreEvent.observe(this) {
            showNoMore()
        }
        viewModel.pushDataEvent.observe(this) { t ->
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