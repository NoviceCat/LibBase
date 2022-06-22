package com.novice.base.uicore.viewmodel

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.NetworkUtils
import com.novice.base.net.CommonResponse
import com.novice.base.uicore.inner.SimpleListContract

/**
 * @author novice
 *
 */
abstract class SimpleListViewModel<T> : BaseViewModel(), SimpleListContract.ISimpleListViewModel {

    var loadCompleteEvent = MutableLiveData<String>()
    var showErrorEvent = MutableLiveData<String>()
    var showEmptyEvent = MutableLiveData<String>()
    var showNoMoreEvent = MutableLiveData<String>()
    var pushDataEvent = MutableLiveData<MutableList<T>>()
    private val defaultLength = 10
    private val defaultOffset = 0
    private var currentOffset = defaultOffset

    abstract suspend fun requestData(offset: Int, length: Int): CommonResponse<MutableList<T>>

    override fun getData(isRefresh: Boolean) {
        if (isRefresh) {
            if (!NetworkUtils.isConnected()){
                showNetDisconnectLayout()
                return
            }
            currentOffset = defaultOffset
        }
        netLaunch(
            {
                requestData(currentOffset, defaultLength)
            },
            { message, data ->
                requestSuccess(data)
            },
            { status, message, data ->
                requestFailed(message)
            }
        )
    }

    private fun requestSuccess(data: MutableList<T>?) {
        loadComplete()
        hideStatusLayout()
        if (!CollectionUtils.isEmpty(data)) {
            pushData(data!!)
            currentOffset += data.size
            if (data.size < defaultLength) {
                showNoMore()
            }
        } else {
            showEmpty()
        }
    }

    private fun requestFailed(msg: String?) {
        loadComplete()
        hideStatusLayout()
        msg?.let {
            showToast(it)
        }
        showError()
    }

    private fun loadComplete() {
        loadCompleteEvent.value = ""
    }

    private fun showError() {
        showErrorEvent.value = ""
    }

    private fun showEmpty() {
        showEmptyEvent.value = ""
    }

    private fun showNoMore(){
        showNoMoreEvent.value = ""
    }

    private fun pushData(list: MutableList<T>) {
        pushDataEvent.value = list
    }

}