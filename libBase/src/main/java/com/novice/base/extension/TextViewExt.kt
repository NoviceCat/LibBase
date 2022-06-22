package com.novice.base.extension

import android.graphics.Paint
import android.widget.TextView

/**
 * @author novic
 * @date 2021/4/28
 * @desc TextView的 拓展函数
 */

/**文字添加删除线*/
fun TextView.addDelLine(): TextView {
    this.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
    return this
}

/**文字加粗*/
fun TextView.setBold(): TextView {
    this.paint.flags = Paint.FAKE_BOLD_TEXT_FLAG
    return this
}

/**文字添加下划线*/
fun TextView.addUnderLine(): TextView {
    this.paint.flags = Paint.UNDERLINE_TEXT_FLAG
    return this
}

