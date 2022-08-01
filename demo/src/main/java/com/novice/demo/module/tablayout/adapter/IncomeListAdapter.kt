package com.novice.demo.module.tablayout.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.novice.demo.R

/**
 * @author novice
 *
 */
class IncomeListAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_income_list),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_text, item)
    }
}