package com.novice.libbasedemo.network.basic

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class RequestBodyBuilder {


    private val paramsMap = hashMapOf<String, Any>()

    fun addParams(key: String, value: Any?): RequestBodyBuilder {
        if (value != null) {
            paramsMap[key] = value
        }
        return this
    }

    fun addParams(params: HashMap<String, Any>): RequestBodyBuilder {
        for (item in params) addParams(item.key, item.value)
        return this
    }

    fun build(): RequestBody {
        return Gson().toJson(paramsMap)
            .toRequestBody("application/json;charset=utf-8".toMediaType())

    }

}