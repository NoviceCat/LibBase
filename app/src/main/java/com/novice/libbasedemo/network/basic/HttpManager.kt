package com.novice.libbasedemo.network.basic

import com.novice.libbasedemo.network.demo.DemoApi
import com.novice.base.net.init.CoroutinesLoadUtils

class HttpManager {


    var demoApi: DemoApi =
        CoroutinesLoadUtils.create(URLConfig.BASE_URL, DemoApi::class.java)

    companion object {
        private var mInstance: HttpManager? = null
        val instance: HttpManager?
            get() {
                if (mInstance == null) {
                    synchronized(HttpManager::class.java) {
                        if (mInstance == null) mInstance = HttpManager()
                    }
                }
                return mInstance
            }
    }

}