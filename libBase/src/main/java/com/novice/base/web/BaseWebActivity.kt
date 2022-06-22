package com.novice.base.web

import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebConfig
import com.just.agentweb.MiddlewareWebChromeBase
import com.just.agentweb.MiddlewareWebClientBase
import com.novice.base.databinding.ActivityCommonWeb2Binding
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.view.ToolBarView
import com.novice.base.uicore.viewmodel.BaseViewModel
import com.novice.base.utils.Extra
import com.novice.base.web.client.MiddlewareChromeClient
import com.novice.base.web.client.MiddlewareWebViewClient

/**
 * web基类
 * @author novice
 */
abstract class BaseWebActivity<VM : BaseViewModel> : BaseActivity<ActivityCommonWeb2Binding,VM>() {

    val TAG = this::class.java.simpleName
    var agentWeb: AgentWeb? = null
    var webTitle = ""
    abstract fun indicatorColor(): Int


    override fun initView() {
        var toolBarBg: ToolBarView.ToolBarBg
        try {
            toolBarBg = intent.getSerializableExtra(Extra.COLOR) as ToolBarView.ToolBarBg
            webTitle = intent.getStringExtra(Extra.TITLE) ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            toolBarBg = ToolBarView.ToolBarBg.WHITE
        }
        if (toolBarBg == ToolBarView.ToolBarBg.WHITE) {
            setToolBarBottomLineVisible(true)
        }
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBinding.linearWeb2, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator(indicatorColor(), 3)
            .useMiddlewareWebChrome(getMiddlewareWebChrome()) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
            .useMiddlewareWebClient(getMiddlewareWebClient()) //设置WebViewClient中间件，支持多个WebViewClient，AgentWeb 3.0.0 加入。
            .createAgentWeb()
            .ready()
            .go(null)

        agentWeb!!.webCreator.webView.overScrollMode = WebView.OVER_SCROLL_NEVER
    }

    protected fun addJavaObject(key: String, androidInterface: Any) {
        agentWeb!!.jsInterfaceHolder.addJavaObject(key, androidInterface)
    }

    protected fun addJavaObjects(maps: Map<String, Any>) {
        agentWeb!!.jsInterfaceHolder.addJavaObjects(maps)
    }


    /**
     * MiddlewareWebClientBase 是 AgentWeb 3.0.0 提供一个强大的功能，
     * 如果用户需要使用 AgentWeb 提供的功能， 不想重写 WebClientView方
     * 法覆盖AgentWeb提供的功能，那么 MiddlewareWebClientBase 是一个
     * 不错的选择 。
     *
     * @return
     */
    protected open fun getMiddlewareWebClient(): MiddlewareWebClientBase {
        return object : MiddlewareWebViewClient() {
            /**
             *
             * @param view
             * @param url
             * @return
             */
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                Log.e(TAG, "MiddlewareWebClientBase#shouldOverrideUrlLoading url:$url")
                if (super.shouldOverrideUrlLoading(view, url)) { // 执行 DefaultWebClient#shouldOverrideUrlLoading
                    return true
                }
                // do you work
                return false
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
                Log.e(TAG, "MiddlewareWebClientBase#shouldOverrideUrlLoading request url:" + request.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

    protected open fun getMiddlewareWebChrome(): MiddlewareWebChromeBase {
        return object : MiddlewareChromeClient() {

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                if (TextUtils.isEmpty(webTitle)) {
                    setToolBarTitle(title ?: "")
                } else {
                    setToolBarTitle(webTitle)
                }
            }

        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (agentWeb != null && agentWeb!!.handleKeyEvent(keyCode, event)) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        agentWeb?.webLifeCycle?.onPause()
    }

    override fun onResume() {
        super.onResume()
        agentWeb?.webLifeCycle?.onResume()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        AgentWebConfig.clearDiskCache(this)
        super.onDestroy()
    }

}