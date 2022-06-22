package com.novice.base.net.init

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.novice.base.application.Core
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpFactory {

    @JvmStatic
    fun createBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(ChuckerInterceptor(Core.getContext()))
            .addInterceptor(HeaderInterceptor())

        return builder.writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
    }
}