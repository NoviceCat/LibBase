package com.novice.base.uicore.inner

import android.view.View
import androidx.annotation.*
import com.novice.base.uicore.statuslayout.OnStatusCustomClickListener
import com.novice.base.uicore.statuslayout.StatusLayoutManager
import com.novice.base.uicore.statuslayout.StatusLayoutType

interface IBaseFragment : ICoreView {

    fun initView()

    fun initData()

    fun initLazyData()

    fun buildCustomStatusLayoutView(builder: StatusLayoutManager.Builder): StatusLayoutManager.Builder

    fun showCustomLayout(
        @LayoutRes customLayoutID: Int,
        onStatusCustomClickListener: OnStatusCustomClickListener,
        @IdRes vararg clickViewID: Int
    )

    fun statusLayoutRetry(view: View)

    fun statusLayoutRetry(view: View, status: StatusLayoutType)

}