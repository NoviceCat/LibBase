package com.novice.base.uicore.ui

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.novice.base.databinding.IncludeDefaultRecyclerviewListBinding
import com.novice.base.manager.LinearLayoutManagerWrap
import com.novice.base.uicore.viewmodel.BaseViewModel

abstract class BaseListFragment<T, A : BaseQuickAdapter<T, *>, VM : BaseViewModel> :
    BaseFragment<IncludeDefaultRecyclerviewListBinding, VM>() {

    lateinit var adapter: A
    private var isFirstRequest = true
    private var page: Int = 1
    private var isLoadMoreFail: Boolean = false

    override fun initView() {
        super.initView()
        initRecyclerview()
        initRefreshLayout()
    }

    private fun initRecyclerview() {
        adapter = initAdapter()
        mBinding.recyclerView.layoutManager = getLayoutManager()
        getItemDecoration()?.let {
            mBinding.recyclerView.addItemDecoration(it)
        }
        mBinding.recyclerView.adapter = adapter
    }

    private fun initRefreshLayout() {
        this.page = startPage()
        mBinding.refreshLayout.setEnableScrollContentWhenLoaded(true)
        mBinding.refreshLayout.setEnableLoadMoreWhenContentNotFull(enableLoadMoreWhenContentNotFull())
        setEnableRefresh(true)
        setEnableLoadMore(true)
        setRefreshListener()
        setLoadLoadMoreListener()
    }

    protected abstract fun initAdapter(): A

    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManagerWrap(context)
    }

    open fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    open fun getRecyclerView(): RecyclerView {
        return mBinding.recyclerView
    }

    open fun getSmartRefreshLayout(): SmartRefreshLayout {
        return mBinding.refreshLayout
    }

    open fun setBackgroundColor(@ColorRes color: Int) {
        setBackgroundColorInt(ContextCompat.getColor(requireContext(), color))
    }

    open fun setBackgroundColorInt(@ColorInt color: Int) {
        mBinding.refreshLayout.setBackgroundColor(color)
    }

    /**
     * 会自动调用刷新接口
     */
    open fun autoRefresh() {
        mBinding.refreshLayout.autoRefresh()
    }

    /**
     * 设置在内容不满一页的时候，是否可以上拉加载更多
     */
    open fun enableLoadMoreWhenContentNotFull(): Boolean {
        return false
    }

    /**
     * 是否使用下拉刷新
     */
    open fun enabledUsedRefresh(): Boolean {
        return true
    }

    /**
     * 是否使用上拉加载
     */
    open fun enabledUsedLoadMore(): Boolean {
        return true
    }

    /**
     * 是否使用Adapter的上拉加载
     */
    open fun enabledUsedAdapterLoadMore(): Boolean {
        return true
    }

    /**
     * 开始页，是从0，还是1开始.默认从1开始
     */
    open fun startPage(): Int {
        return 1
    }

    open fun setCurPage(page: Int) {
        this.page = page
    }

    /**
     * 发起Http请求
     * @param page 当前页面
     */
    open fun doHttpRequest(page: Int) {

    }

    /**
     * @param isRefresh 通过Page==1或>1判断是下拉刷新或加载更多
     */
    open fun doHttpRequest(isRefresh: Boolean) {

    }

    /**
     * 当Page为1时，表示下拉刷新
     *
     * @return
     */
    open fun isRefresh(): Boolean {
        return isFirstPage()
    }

    open fun isFirstPage(): Boolean {
        return getPage() == startPage()
    }

    open fun getPage(): Int {
        return page
    }

    open fun setAdapterData(@Nullable list: MutableList<T>) {
        if (isFirstPage()) {
            setListData(list)
            doDisableLoadMoreIfNotFullPage()
        } else {
            addData(list)
        }
    }

    open fun setNewData(@Nullable list: MutableList<T>?) {
        adapter.setNewInstance(list)
    }

    open fun setListData(@Nullable list: MutableList<T>?) {
        adapter.setList(list)
    }

    open fun addData(@NonNull list: Collection<T>) {
        adapter.addData(list)
    }

    open fun loadMoreFail() {
        isLoadMoreFail = true
        if (enabledUsedAdapterLoadMore()) {
            adapter.loadMoreModule.loadMoreFail()
        } else {
            mBinding.refreshLayout.finishLoadMore(false)
        }
    }

    open fun loadMoreEnd() {
        loadMoreEnd(false)
    }

    open fun loadMoreEnd(isGone: Boolean) {
        isLoadMoreFail = false
        if (enabledUsedAdapterLoadMore()) {
            adapter.loadMoreModule.loadMoreEnd(isGone)
        } else {
            mBinding.refreshLayout.finishLoadMoreWithNoMoreData()
        }
    }

    open fun loadComplete() {
        isLoadMoreFail = false
        if (isRefresh()) {
            refreshComplete()
        } else {
            loadMoreComplete()
        }
        enabledRefreshOrLoadMore(true)
    }

    private fun enabledRefreshOrLoadMore(openRefreshOrLoadMore: Boolean) {
        setEnableLoadMore(openRefreshOrLoadMore)
        setEnableRefresh(openRefreshOrLoadMore)
    }

    open fun loadComplete(hasMoreData: Boolean) {
        isLoadMoreFail = false
        if (isRefresh()) {
            refreshComplete()
        } else {
            loadMoreComplete()
        }
        if (hasMoreData) {
            enabledRefreshOrLoadMore(true)
        } else {
            setEnableRefresh(true)
            loadMoreEnd()
        }
    }

    open fun refreshComplete() {
        mBinding.refreshLayout.finishRefresh(true)
    }

    private fun loadMoreComplete() {
        if (enabledUsedAdapterLoadMore()) {
            adapter.loadMoreModule.loadMoreComplete()
        } else {
            mBinding.refreshLayout.finishLoadMore()
        }
    }

    private fun doDisableLoadMoreIfNotFullPage() {
        if (enabledUsedAdapterLoadMore()) {
            if (enableLoadMoreWhenContentNotFull()) {
                adapter.loadMoreModule.isEnableLoadMoreIfNotFullPage =
                    enableLoadMoreWhenContentNotFull()
            }
        }
    }

    private fun setEnableRefresh(enabled: Boolean) {
        mBinding.refreshLayout.setEnableRefresh(enabledUsedRefresh() && enabled)
    }

    private fun setEnableLoadMore(enabled: Boolean) {
        if (enabledUsedAdapterLoadMore()) {
            mBinding.refreshLayout.setEnableLoadMore(false)
            adapter.loadMoreModule.isEnableLoadMore = enabledUsedLoadMore() && enabled
        } else {
            mBinding.refreshLayout.setEnableLoadMore(enabledUsedLoadMore() && enabled)
            adapter.loadMoreModule.isEnableLoadMore = false
        }
    }

    private fun setRefreshListener() {
        mBinding.refreshLayout.setOnRefreshListener {
            if (isViewDestroyed()) return@setOnRefreshListener
            doRefresh()
        }
    }

    private fun setLoadLoadMoreListener() {
        if (enabledUsedAdapterLoadMore()) {
            if (enabledUsedLoadMore()) {
                adapter.loadMoreModule.setOnLoadMoreListener {
                    if (isViewDestroyed()) return@setOnLoadMoreListener
                    doLoadMoreData()
                }
            }
        } else {
            mBinding.refreshLayout.setOnLoadMoreListener {
                if (isViewDestroyed()) return@setOnLoadMoreListener
                doLoadMoreData()
            }
        }
    }

    private fun doRefresh() {
        this.page = startPage()
        getHttpRequest(page)
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        setEnableLoadMore(false)
    }

    private fun doLoadMoreData() {
        getHttpRequest(if (isLoadMoreFail || isFirstRequest) page else ++page)
        setEnableRefresh(false)
    }

    private fun getHttpRequest(page: Int) {
        isFirstRequest = false
        doHttpRequest(page)
        doHttpRequest(isRefresh())
    }

}