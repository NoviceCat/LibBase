package com.novice.libbasedemo.network.demo

import com.novice.libbasedemo.network.demo.bean.UserBean
import com.novice.base.net.CommonResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface DemoApi {


    @POST("login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String, @Query("rememberMe") rememberMe: Boolean
    ): Response<ResponseBody>

    @GET("test/user/{userId}")
    suspend fun getUser(@Path("userId") userId: String): CommonResponse<UserBean.UserDetailBean>

    @POST("test/user/save")
    suspend fun saveUser(
        @Query("userId") userId: String, @Query("username") username: String,
        @Query("password") password: String, @Query("mobile") mobile: String
    ): CommonResponse<Any>

    @GET("test/user/list")
    suspend fun getUserList(): CommonResponse<MutableList<UserBean.UserDetailBean>>

    @PUT("test/user/update")
    suspend fun updateUser(@Body body: RequestBody): CommonResponse<Any>

    @DELETE("test/user/{userId}")
    suspend fun delUser(@Path("userId") userId: String): CommonResponse<Any>

}