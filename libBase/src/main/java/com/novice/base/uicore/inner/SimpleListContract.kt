package com.novice.base.uicore.inner

/**
 * @author novice
 */
interface SimpleListContract {

    interface SimpleListView<T> {
        fun pushData(list: MutableList<T>)
        fun showEmpty()
        fun showError()
        fun showNoMore()
    }

    interface ISimpleListViewModel {
        fun getData(isRefresh: Boolean)
    }

}