package com.novice.demo.module.list

import com.novice.base.net.CommonResponse
import com.novice.base.net.HttpNetCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TestRepository {

    suspend fun getTestList(offset: Int, length: Int): CommonResponse<MutableList<String>> {
        return withContext(Dispatchers.IO) {
            val list = mutableListOf<String>()
            for (index in 1..5) {
                val randoms = (0..1000).random()
                list.add("随机数：${randoms}")
            }
            CommonResponse<MutableList<String>>().apply {
                code = HttpNetCode.SUCCESS
                msg = null
                data = list
            }
        }
    }

}