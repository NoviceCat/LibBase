package com.novice.base.net.init

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoroutinesLoadUtils {

    fun <T> create(baseUrl: String, clazz: Class<T>): T {
        if (okHttpClient == null) {
            init()
        }
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return retrofit.create(clazz)
    }

    private var okHttpClient: OkHttpClient? = null

    fun init() {
        okHttpClient = OkHttpFactory.createBuilder().build()
    }
}