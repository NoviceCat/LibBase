package com.novice.demo.module.widget

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.novice.demo.R

class WidgetAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_widget_list) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_name, item)
    }
}