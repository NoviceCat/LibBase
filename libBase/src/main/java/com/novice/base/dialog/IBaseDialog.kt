package com.novice.base.dialog

interface IBaseDialog {
    fun getWidth(): Int
    fun getHeight(): Int
    fun canOutsideTouchToCancel(): Boolean
    fun canCancel(): Boolean
    fun getGravity(): Int
    fun getAnimationStyle(): Int
    fun initView()

}