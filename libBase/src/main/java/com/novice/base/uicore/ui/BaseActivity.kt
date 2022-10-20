package com.novice.base.uicore.ui

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.novice.base.R
import com.novice.base.databinding.ActivityBaseBinding
import com.novice.base.uicore.inner.IBaseActivity
import com.novice.base.uicore.inner.OnToolBarClickListener
import com.novice.base.uicore.statuslayout.OnStatusCustomClickListener
import com.novice.base.uicore.statuslayout.OnStatusRetryClickListener
import com.novice.base.uicore.statuslayout.StatusLayoutManager
import com.novice.base.uicore.statuslayout.StatusLayoutType
import com.novice.base.uicore.utils.BindingReflex
import com.novice.base.uicore.utils.OSUtils
import com.novice.base.uicore.utils.UICoreConfig
import com.novice.base.uicore.view.CommonViewDelegate
import com.novice.base.uicore.view.ToolBarView
import com.novice.base.uicore.viewmodel.BaseViewModel

/**
 * activity基类
 * @author novice
 *
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity(),
    IBaseActivity, OnToolBarClickListener {

    protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }
    protected val viewModel: VM by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewModel(javaClass, this)
    }

    lateinit var baseBinding: ActivityBaseBinding

    private val TAG = this.javaClass.simpleName

    private var componentView: CommonViewDelegate? = null

    /**
     * activity is destroyed
     */
    private var isViewDestroy = false

    /**
     * 通用toolBar
     */
    private var toolbarView: ToolBarView? = null

    /**
     * 顶部 bar，若使用通用toolbar则为ToolBarView。否则为自定义的top bar view
     */
    private var topBarView: View? = null

    /**
     * 默认显示，当不显示时，设置为false
     * 用户控件dialog的显示，注意，不能onResume判断，因为有些页面在初始化时，就要弹出dialog加载进度条。
     */
    private var isActivityVisible = true

    /**
     * 重写getResources()方法，让APP的字体不受系统设置字体大小影响
     */
    override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            attachViewModelAndLifecycle()
            initContentView()
            initImmersionBar()
            initCommonToolBar()
            initView()
            initUIChangeLiveDataCallBack()
            initData()
            postInitLazyData()
        } catch (e: Throwable) {
            catchThrowable(e)
        }
    }

    private fun attachViewModelAndLifecycle() {
        lifecycle.addObserver(viewModel)
    }

    private fun postInitLazyData() {
        val runnable = Runnable {
            if (!isFinishing && !isViewDestroyed()) {
                initLazyData()
            }
        }
        baseBinding.root.post(runnable)
    }

    override fun initLazyData() {
    }

    private fun getViewDelegate(): CommonViewDelegate {
        if (componentView == null) {
            componentView = CommonViewDelegate(this)
        }
        return componentView!!
    }

    /**
     * 注册ViewModel与View的契约UI回调事件
     */
    private fun initUIChangeLiveDataCallBack() {
        //ProgressDialog
        viewModel.showProgressDialogEvent.observe(this, Observer {
            showProgressDialog()
        })
        viewModel.hideProgressDialogEvent.observe(this, Observer {
            hideProgressDialog()
        })
        //StatusLayout
        viewModel.showEmptyLayoutEvent.observe(this, Observer {
            showEmptyLayout()
        })
        viewModel.showLoadingLayoutEvent.observe(this, Observer {
            showLoadingLayout()
        })
        viewModel.showNetDisconnectLayoutEvent.observe(this, Observer {
            showNetDisconnectLayout()
        })
        viewModel.showLoadErrorLayoutEvent.observe(this, Observer {
            showLoadErrorLayout()
        })
        viewModel.hideStatusLayoutEvent.observe(this, Observer {
            hideStatusLayout()
        })
        //Toast
        viewModel.showToastStrEvent.observe(this, Observer { t ->
            showToast(t)
        })
        viewModel.finishAcEvent.observe(this, Observer {
            finishAc()
        })
        registerUIChangeLiveDataCallBack()
    }

    open fun registerUIChangeLiveDataCallBack() {

    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        this.isActivityVisible = true
    }

    override fun onPause() {
        super.onPause()
        this.isActivityVisible = false
    }

    override fun onDestroy() {
        try {
            hideProgressDialog()
            this.isViewDestroy = true
            this.isActivityVisible = false
            super.onDestroy()
        } catch (e: Throwable) {
            catchThrowable(e)
        }
    }

    open fun isViewDestroyed(): Boolean {
        return isViewDestroy
    }

    private fun initContentView() {
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        if (enabledVisibleToolBar()) {
            baseBinding.toolBarView.visibility = View.VISIBLE
        } else {
            baseBinding.toolBarView.visibility = View.GONE
        }
        setContentView(baseBinding.root)
        buildStatusView(baseBinding.flContainer)
        baseBinding.flContainer.addView(mBinding.root)
    }

    override fun enabledVisibleToolBar(): Boolean {
        return true
    }

    override fun enabledDefaultBack(): Boolean {
        return true
    }

    override fun initView() {}

    private var closeToast: Toast? = null

    protected open fun processBackPressed(): Boolean {
        return false
    }

    override fun onBackPressed() {
        if (processBackPressed()) {
            return
        }
        back()
    }

    private var mCloseWarned = false

    private fun back() {
        val cnt = supportFragmentManager.backStackEntryCount
        if (cnt <= 1 && isTaskRoot) {
            val closeWarningHint = "再按一次退出程序"
            if (!mCloseWarned) {
                closeToast =
                    Toast.makeText(applicationContext, closeWarningHint, Toast.LENGTH_SHORT)
                closeToast!!.show()
                mCloseWarned = true
                Handler().postDelayed({ mCloseWarned = false }, 1500)
            } else {
                if (closeToast != null) {
                    closeToast!!.cancel()
                }
                doReturnBack()
            }
        } else {
            mCloseWarned = false
            doReturnBack()
        }
    }

    private fun doReturnBack() {
        val count = supportFragmentManager.backStackEntryCount
        if (count <= 1) {
            finish()
        } else {
            supportFragmentManager.popBackStackImmediate()
        }
    }

    protected open fun log(args: String?) {
        LogUtils.i(TAG, args)
    }

    override fun showToast(msg: String?) {
        msg?.let {
            ToastUtils.setGravity(-1, -1, -1)
            ToastUtils.showShort(it)
        }
    }

    override fun finishAc() {
        finish()
    }

    private fun catchThrowable(e: Throwable) {
        UICoreConfig.throwable(e)
    }

    //------------------toolbar start setting-----------------------------------

    protected open fun hasCommonToolBar(): Boolean {
        return toolbarView != null
    }

    private fun initCommonToolBar() { //toolbar
        toolbarView = baseBinding.toolBarView
        if (!hasCommonToolBar()) {
            return
        }
        getCommonToolBarView()?.setToolBarClickListener(this)
        //支持默认返回按钮和事件
        setToolBarViewVisible(enabledDefaultBack(), ToolBarView.ViewType.LEFT_IMAGE)
    }

    override fun setToolBarTitle(title: String): ToolBarView? {
        return if (hasCommonToolBar()) {
            getCommonToolBarView()?.setCenterText(title)
        } else {
            null
        }
    }

    override fun setToolBarTitle(@StringRes idRes: Int): ToolBarView? {
        return if (hasCommonToolBar()) {
            getCommonToolBarView()?.setCenterText(idRes)
        } else {
            null
        }
    }

    override fun setToolBarTitleColor(@ColorRes resId: Int): ToolBarView? {
        return if (hasCommonToolBar()) {
            getCommonToolBarView()?.setCenterTextColor(resId)
        } else {
            null
        }
    }

    override fun setToolBarRightImage(@DrawableRes drawable: Int): ToolBarView? {
        return if (hasCommonToolBar()) {
            getCommonToolBarView()?.setRightImage(drawable)
        } else {
            null
        }
    }


    override fun setToolBarRightText(text: String): ToolBarView? {
        return if (hasCommonToolBar()) {
            getCommonToolBarView()?.setRightText(text)
        } else {
            null
        }
    }

    override fun setToolBarRightTextColor(@ColorRes resId: Int): ToolBarView? {
        return if (hasCommonToolBar()) {
            getCommonToolBarView()?.setRightTextColor(resId)
        } else {
            null
        }
    }

    override fun setToolBarBottomLineVisible(isVisible: Boolean): ToolBarView? {
        return if (hasCommonToolBar()) {
            getCommonToolBarView()?.showBottomLine(isVisible)
        } else null
    }

    override fun setToolBarViewVisible(
        isVisible: Boolean,
        vararg events: ToolBarView.ViewType
    ): ToolBarView? {
        return if (hasCommonToolBar()) {
            getCommonToolBarView()?.setToolBarViewVisible(isVisible, *events)
        } else {
            null
        }
    }


    protected open fun getCommonToolBarView(): ToolBarView? {
        return toolbarView
    }

    override fun onClickToolBarView(view: View, event: ToolBarView.ViewType) {
        when (event) {
            //支持默认返回按钮和事件
            ToolBarView.ViewType.LEFT_IMAGE -> {
                if (enabledDefaultBack()) {
                    onBackPressed()
                }
            }
        }
    }

    //------------------ toolbar end ------------


    //------------------------- 沉浸式 ImmersionBar start --------------------

    private fun initImmersionBar() {
        if (enabledVisibleToolBar()) {
            setWhiteFakeStatus(baseBinding.llBaseRoot.id)
        } else {
            setTransparentStatus(baseBinding.llBaseRoot.id)
        }
    }

    open fun setWhiteFakeStatus(contentParentViewId: Int) {
        setFakeStatus(
            contentParentViewId,
            true,
            0,
            R.color.common_white_color,
            true
        )
        OSUtils.fixWhiteStatusbarBug(this)
    }

    open fun setTransparentStatus(contentParentViewId: Int) {
        val parentView = findViewById<View>(contentParentViewId)
        if (parentView != null) {
            BarUtils.setStatusBarColor(this, Color.argb(100, 0, 0, 0), false).background = null
            BarUtils.subtractMarginTopEqualStatusBarHeight(parentView)
            BarUtils.setStatusBarLightMode(this, true)
        }
        fixImmersionAndEditBug(true)
    }

    private fun setFakeStatus(
        contentParentViewId: Int,
        isLightMode: Boolean,
        alpha: Int,
        statusBgResource: Int,
        enableFixImmersionAndEditBug: Boolean
    ) {
        val parentView = findViewById<View>(contentParentViewId)
        if (parentView != null) {
            BarUtils.setStatusBarColor(this, Color.argb(alpha, 0, 0, 0))
                .setBackgroundResource(statusBgResource)
            BarUtils.addMarginTopEqualStatusBarHeight(parentView)
            BarUtils.setStatusBarLightMode(this, isLightMode)
        }
        fixImmersionAndEditBug(enableFixImmersionAndEditBug)
    }

    private fun fixImmersionAndEditBug(enableFixImmersionAndEditBug: Boolean) {
        if (enableFixImmersionAndEditBug) {
            KeyboardUtils.fixAndroidBug5497(this) //解决沉浸式状态栏与edittext冲突问题
        }
    }

    //------------------------- 沉浸式 ImmersionBar end --------------------


    //--------------------- ProgressDialog start --------------------------
    private fun isActivityVisible(): Boolean {
        return isActivityVisible
    }

    open fun setProgressDialogCancel(onTouchOutside: Boolean, backCancel: Boolean) {
        if (!isViewDestroyed()) {
            getViewDelegate().setProgressDialogCanceled(onTouchOutside, backCancel)
        }
    }

    override fun showProgressDialog() {
        if (!isViewDestroyed() && isActivityVisible()) {
            getViewDelegate().showProgressDialog()
        }
    }

    override fun hideProgressDialog() {
        if (!isViewDestroyed()) {
            getViewDelegate().hideProgressDialog()
        }
    }

    //--------------------- ProgressDialog end --------------------------


    //--------------------- status layout start --------------------------

    /**
     * 若是不显示通用toolbar，需要传入status覆盖的View.
     *
     * @param contentView
     */
    private fun buildStatusView(contentView: View) {
        log("[buildStatusView]contentView：$contentView")
        getViewDelegate().initStatusLayout(contentView)
        val statusLayoutManagerBuilder = getViewDelegate().statusLayoutManagerBuilder
        val builder = buildCustomStatusLayoutView(statusLayoutManagerBuilder)
        if (builder.onStatusRetryClickListener == null) {
            getViewDelegate().setDefaultStatusListener(object : OnStatusRetryClickListener {
                override fun onClickRetry(view: View, status: StatusLayoutType) {
                    statusLayoutRetry(view, status)
                }
            })
        }
        getViewDelegate().build(builder)
    }

    override fun buildCustomStatusLayoutView(builder: StatusLayoutManager.Builder): StatusLayoutManager.Builder {
        return builder
    }


    override fun statusLayoutRetry(view: View) {

    }

    /**
     * 点击不同状态。
     * StatusLayoutType
     *
     * @param view
     * @param status
     */
    override fun statusLayoutRetry(view: View, status: StatusLayoutType) {

    }

    override fun showEmptyLayout() {
        getViewDelegate().showEmptyLayout()
    }

    override fun showLoadingLayout() {
        getViewDelegate().showLoadingLayout()
    }

    override fun showLoadErrorLayout() {
        getViewDelegate().showLoadErrorLayout()
    }

    override fun showNetDisconnectLayout() {
        getViewDelegate().showNetDisconnectLayout()
    }

    override fun hideStatusLayout() {
        getViewDelegate().hideStatusLayout()
    }

    override fun showCustomLayout(
        @LayoutRes customLayoutID: Int,
        onStatusCustomClickListener: OnStatusCustomClickListener,
        @IdRes vararg clickViewID: Int
    ) {
        getViewDelegate().showCustomLayout(
            customLayoutID,
            onStatusCustomClickListener,
            *clickViewID
        )
    }

    //--------------------- status layout end --------------------------

}