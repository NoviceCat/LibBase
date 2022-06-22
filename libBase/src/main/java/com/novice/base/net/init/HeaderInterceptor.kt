package com.novice.base.net.init

import com.blankj.utilcode.util.SPUtils
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    companion object {
        const val SP_KEY_SESSION_ID = "SESSION_ID"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequestBuilder = chain.request().newBuilder()
        newRequestBuilder.header("Content-Type", "application/json;charset=utf-8")
        newRequestBuilder.header("Cookie", SPUtils.getInstance().getString(SP_KEY_SESSION_ID))
        return chain.proceed(newRequestBuilder.build())
    }
}