package com.novice.base.uicore.inner

import android.view.View
import com.novice.base.uicore.view.ToolBarView

/**
 * @author novice
 */
interface OnToolBarClickListener {

    fun onClickToolBarView(view: View, event: ToolBarView.ViewType)
}