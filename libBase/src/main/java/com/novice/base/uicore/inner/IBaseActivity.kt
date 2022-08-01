package com.novice.base.uicore.inner

import android.view.View
import androidx.annotation.*
import com.novice.base.uicore.statuslayout.OnStatusCustomClickListener
import com.novice.base.uicore.statuslayout.StatusLayoutManager
import com.novice.base.uicore.statuslayout.StatusLayoutType
import com.novice.base.uicore.view.ToolBarView

/**
 * 用户页面, 操作页面，对应Activity,fragment统一接口层
 * @author novice
 */
interface IBaseActivity : ICoreView {

    fun enabledDefaultBack(): Boolean

    fun enabledVisibleToolBar(): Boolean

    fun setToolBarTitle(title: String): ToolBarView?

    fun setToolBarTitle(@StringRes resId: Int): ToolBarView?

    fun setToolBarTitleColor(@ColorRes resId: Int): ToolBarView?

    fun setToolBarRightImage(@DrawableRes drawable: Int): ToolBarView?

    fun setToolBarRightText(text: String): ToolBarView?

    fun setToolBarRightTextColor(@ColorRes resId: Int): ToolBarView?

    fun setToolBarViewVisible(isVisible: Boolean, vararg events: ToolBarView.ViewType): ToolBarView?

    fun setToolBarBottomLineVisible(isVisible: Boolean): ToolBarView?

    fun initView()

    fun initData()

    fun initLazyData()

    fun onBackPressed()

    fun buildCustomStatusLayoutView(builder: StatusLayoutManager.Builder): StatusLayoutManager.Builder

    fun showCustomLayout(
        @LayoutRes customLayoutID: Int,
        onStatusCustomClickListener: OnStatusCustomClickListener,
        @IdRes vararg clickViewID: Int
    )

    fun statusLayoutRetry(view: View)

    fun statusLayoutRetry(view: View, status: StatusLayoutType)

}