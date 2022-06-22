package com.novice.libbasedemo.network.basic

import okhttp3.RequestBody

object Utils {

    fun HashMap<String, Any>.buildRequestBody(): RequestBody {
        return RequestBodyBuilder().addParams(this).build()
    }

}