package com.novice.base.web

import com.just.agentweb.MiddlewareWebClientBase
import com.novice.base.uicore.viewmodel.BaseViewModel
import com.novice.base.web.sonic.SonicImpl

/**
 * @author novice
 */
abstract class SonicWebActivity<VM : BaseViewModel> : BaseWebActivity<VM>() {

    private var sonicImpl: SonicImpl? = null
    abstract fun hostUrl(): String

    override fun initView() {
        // 1. 首先创建SonicImpl
        sonicImpl = SonicImpl(hostUrl())
        // 2. 调用 onCreateSession
        sonicImpl!!.onCreateSession()
        //3. 创建AgentWeb ，注意创建AgentWeb的时候应该使用加入SonicWebViewClient中间件
        // 创建 AgentWeb 注意的 go("") 传入的 mUrl 应该null 或者"",所以BaseWebActivity直接go(null)
        super.initView()
        //4. 注入 JavaScriptInterface
//        agentWeb!!.jsInterfaceHolder?.addJavaObject("sonic", SonicJavaScriptInterface(sonicImpl!!.sonicSessionClient))
        //5. 最后绑定AgentWeb
        sonicImpl!!.bindAgentWeb(agentWeb)
    }

    //在步骤3的时候应该传入给AgentWeb
    override fun getMiddlewareWebClient(): MiddlewareWebClientBase {
        return sonicImpl!!.createSonicClientMiddleWare()
    }

    override fun onDestroy() {
        sonicImpl?.destrory()
        super.onDestroy()
    }

}