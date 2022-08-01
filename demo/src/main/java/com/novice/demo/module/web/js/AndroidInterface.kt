package com.novice.demo.module.web.js

import android.content.Context
import android.text.TextUtils
import android.webkit.JavascriptInterface
import com.just.agentweb.AgentWeb
import org.json.JSONObject

/**
 * 与js的交互都在此处处理
 * @author novice
 */
class AndroidInterface {

    private var context: Context
    private var agentWeb: AgentWeb
    private var listener: JSBridgeLifeCycleListener? = null

    constructor(context: Context, agentWeb: AgentWeb) {
        this.context = context
        this.agentWeb = agentWeb
    }

    fun setJSBridgeLifeCycleListener(listener: JSBridgeLifeCycleListener?) {
        this.listener = listener
    }

    /**
     * h5调用原生方法
     */
    @JavascriptInterface
    fun router(json: String?) {
        try {
            if (TextUtils.isEmpty(json)) {
                return
            }
            json!!.trimStart('"')
            json.trimEnd('"')
            val jsonObject = JSONObject(json)
            val action = jsonObject.optString("action")
            if (TextUtils.isEmpty(action)) {
                return
            }
            var data: JSONObject? = null
            if (jsonObject.has("data")) {
                data = jsonObject.getJSONObject("data")
            }
//            JumpToUtils.onJumpTo(context, action, data, listener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @JavascriptInterface
    fun setShareTimeline(data: String?) {
    }

    @JavascriptInterface
    fun setShareMessage(data: String?) {
    }

    @JavascriptInterface
    fun setCopyLink(data: String?) {
    }

    /**
     * 原生调用h5方法
     */
    fun routerCallback(json: JSONObject?) {
        agentWeb.jsAccessEntrace.quickCallJs("routerCallback", json.toString())
    }

}