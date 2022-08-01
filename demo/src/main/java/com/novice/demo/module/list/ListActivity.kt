package com.novice.demo.module.list

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.novice.base.net.CommonResponse
import com.novice.base.uicore.ui.SimpleListActivity
import com.novice.base.uicore.viewmodel.SimpleListViewModel
import com.novice.demo.R

class ListActivity :
    SimpleListActivity<String, ListActivity.ListAdapter, ListActivity.ListViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ListActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        setToolBarTitle("简易列表")
    }

    override fun initAdapter(): ListAdapter {
        return ListAdapter()
    }

    override fun initViewModel(): ListViewModel {
        return ViewModelProvider(this).get(ListViewModel::class.java)
    }

    class ListAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_list),
        LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.tv_text, item)
        }
    }

    class ListViewModel : SimpleListViewModel<String>() {
        override suspend fun requestData(
            offset: Int,
            length: Int
        ): CommonResponse<MutableList<String>> {
            return TestRepository.getTestList(offset, length)
        }
    }

}
