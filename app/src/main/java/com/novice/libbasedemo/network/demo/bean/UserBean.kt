package com.novice.libbasedemo.network.demo.bean

import java.io.Serializable

class UserBean {

    data class UserDetailBean(
        var userId: String?, var username: String?,
        var password: String?, var mobile: String?
    ) : Serializable{

        override fun toString(): String {
            return "userId == $userId"
        }

    }


}