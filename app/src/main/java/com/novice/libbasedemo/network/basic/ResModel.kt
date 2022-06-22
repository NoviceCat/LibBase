package com.novice.libbasedemo.network.basic

class ResModel<T> {
    var data: T? = null
        private set
    var code = 0
    var msg: String? = null
    fun setData(data: T) {
        this.data = data
    }

    companion object{

        const val SUCCESS_CODE = 0


    }
}