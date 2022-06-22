package com.novice.libbasedemo.network.demo

import com.novice.libbasedemo.network.basic.HttpManager
import com.novice.libbasedemo.network.basic.Utils.buildRequestBody
import com.novice.libbasedemo.network.demo.bean.UserBean
import com.novice.base.net.CommonResponse
import okhttp3.ResponseBody
import retrofit2.Response

object DemoHttp {

    suspend fun login(
        username: String,
        password: String,
        rememberMe: Boolean
    ): Response<ResponseBody> {
        return HttpManager.instance!!.demoApi.login(username, password, rememberMe)
    }

    suspend fun getUserByUserId(userId: String): CommonResponse<UserBean.UserDetailBean> {
        return HttpManager.instance!!.demoApi.getUser(userId)
    }

    suspend fun saveUser(
        userId: String,
        password: String,
        username: String,
        mobile: String
    ): CommonResponse<Any> {
        return HttpManager.instance!!.demoApi.saveUser(userId, username, password, mobile)
    }

    suspend fun getUserList(): CommonResponse<MutableList<UserBean.UserDetailBean>> {
        return HttpManager.instance!!.demoApi.getUserList()
    }

    suspend fun updateUser(
        userId: String,
        password: String,
        username: String,
        mobile: String
    ): CommonResponse<Any> {
        val params = HashMap<String, Any>()
        params["userId"] = userId
        params["password"] = password
        params["username"] = username
        params["mobile"] = mobile
        return HttpManager.instance!!.demoApi.updateUser(params.buildRequestBody())
    }

    suspend fun delUser(userId: String): CommonResponse<Any> {
        return HttpManager.instance!!.demoApi.delUser(userId)
    }


}