package com.novice.base.net

/**
 * @author novice
 */
object HttpNetCode {

    const val SUCCESS = 0//成功
    const val LOGIN_EXPIRED = 401//登录过期
    const val DATA_ERROR = 15001//数据解析异常
    const val NET_CONNECT_ERROR = 15002//网络连接异常
    const val NET_TIMEOUT = 15003//网络请求超时
    const val PARSE_ERROR = 15004//Parse解析异常
    const val JSON_ERROR = 15005//Json解析异常

}