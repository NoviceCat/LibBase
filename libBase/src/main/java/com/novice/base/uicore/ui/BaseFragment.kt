package com.novice.base.uicore.ui

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.novice.base.uicore.inner.IBaseFragment
import com.novice.base.uicore.statuslayout.*
import com.novice.base.uicore.utils.BindingReflex
import com.novice.base.uicore.utils.UICoreConfig
import com.novice.base.uicore.view.CommonViewDelegate
import com.novice.base.uicore.view.ToolBarView
import com.novice.base.uicore.viewmodel.BaseViewModel
import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * fragment基类
 * @author novice
 *
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment(), IBaseFragment {

    lateinit var viewModel: VM

    private var _binding: VB? = null

    protected val mBinding get() = _binding!!

    private var mViewRef: Reference<Activity>? = null

    /**
     * 顶部 bar，若使用通用toolbar则为ToolBarView。否则为自定义的top bar view
     */
    private var mTopBarView: View? = null

    /**
     * 封装统一的progressDialog,toast，statusLayout实现
     */
    private var mComponentView: CommonViewDelegate? = null

    /**
     * 通用的自定义toolbar，填充内容
     */
    protected var mChildContainerLayout: FrameLayout? = null

    /**
     * 通用的自定义toolbar
     */
    private var mCommonToolbarView: ToolBarView? = null

    private var isViewDestroy = false

    /**
     * user visible
     */
    private var isFVisible = false

    /**
     * initLazyData调用方法
     */
    private var isInitLazyData = false

    private var isAttachViewModelOk = false

    /**
     * onAttach(Context) is not called on pre API 23 versions of Android and onAttach(Activity) is deprecated
     * Use onAttachToContext instead
     */
    @TargetApi(23)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            onAttachToContext(context)
        }
    }

    /**
     * Deprecated on API 23
     * Use onAttachToContext instead
     */
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity)
        }
    }

    private fun onAttachToContext(context: Context?) {
        try {
            mViewRef = WeakReference(context as Activity)
            attachViewModelAndLifecycle()
        } catch (e: Throwable) {
            catchThrowable(e)
        }
    }

    private fun attachViewModelAndLifecycle() {
        viewModel = initViewModel()
        lifecycle.addObserver(viewModel)
        isAttachViewModelOk = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            initBaseCommonComponentView()
            _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
        } catch (e: Throwable) {
            catchThrowable(e)
        }
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            if (isActivityDestroyed()) {
                log("onViewCreated fragment刚创建，Activity就destroy了！")
                return
            }
            initView()
            initUIChangeLiveDataCallBack()
            initData()
        } catch (e: Throwable) {
            catchThrowable(e)
        }
    }


    abstract fun initViewModel(): VM

    /**
     * 运行在initView之后
     * 此时已经setContentView
     * 可以做一些初始化操作
     */
    override fun initView() {}

    /**
     * 注册ViewModel与View的契约UI回调事件
     */
    private fun initUIChangeLiveDataCallBack() {
        //ProgressDialog
        viewModel.showProgressDialogEvent.observe(viewLifecycleOwner) {
            showProgressDialog()
        }
        viewModel.hideProgressDialogEvent.observe(viewLifecycleOwner) {
            hideProgressDialog()
        }
        //StatusLayout
        viewModel.showEmptyLayoutEvent.observe(viewLifecycleOwner) {
            showEmptyLayout()
        }
        viewModel.showLoadingLayoutEvent.observe(viewLifecycleOwner) {
            showLoadingLayout()
        }
        viewModel.showLoadErrorLayoutEvent.observe(viewLifecycleOwner) {
            showLoadErrorLayout()
        }
        viewModel.showNetDisconnectLayoutEvent.observe(viewLifecycleOwner) {
            showNetDisconnectLayout()
        }
        viewModel.hideStatusLayoutEvent.observe(viewLifecycleOwner) {
            hideStatusLayout()
        }
        //Toast
        viewModel.showToastStrEvent.observe(viewLifecycleOwner) { t ->
            showToast(t)
        }
        viewModel.finishAcEvent.observe(viewLifecycleOwner) {
            finishAc()
        }
        registerUIChangeLiveDataCallBack()
    }

    open fun registerUIChangeLiveDataCallBack() {

    }

    override fun initData() {
    }

    override fun finishAc() {
        activity?.finish()
    }

    /**
     * 判断Activity是否被销毁
     *
     * @return
     */
    private fun isActivityDestroyed(): Boolean {
        val activity = activity
        return activity?.isDestroyed ?: false
    }

    override fun onPause() {
        super.onPause()
        try {
            log("fragment visible onPause:" + super.isResumed() + ";" + super.isVisible() + ";" + super.isHidden() + ";" + super.getUserVisibleHint() + ";" + super.isAdded())
        } catch (e: Throwable) {
            catchThrowable(e)
        }
    }

    private fun doInitLazyData() {
        if (!isInitLazyData) {
            isInitLazyData = true
            postInitLazyData()
        }
    }

    private fun postInitLazyData() {
        val runnable = Runnable {
            val activityNOFinishing = activity != null && !requireActivity().isFinishing
            if (!isViewDestroyed() && activityNOFinishing) {
                initLazyData()
            }
        }
        mBinding?.root?.post(runnable)
    }

    override fun initLazyData() {
    }


    open fun isViewDestroyed(): Boolean {
        return isViewDestroy
    }


    override fun onDestroyView() {
        super.onDestroyView()
        isFVisible = false
        _binding = null
    }

    override fun onDestroy() {
        this.isViewDestroy = true
        super.onDestroy()
    }

    private fun initBaseCommonComponentView() {
        mComponentView = CommonViewDelegate(context)
    }

    private fun log(args: String) {
        LogUtils.i("BaseFragment", this.javaClass.simpleName + " " + args)
    }

    private fun getViewDelegate(): CommonViewDelegate {
        if (mComponentView == null) {
            mComponentView = CommonViewDelegate(context)
        }
        return mComponentView!!
    }

    open fun getBaseActivity(): Activity? {
        if (mViewRef != null) {
            return mViewRef!!.get()
        } else {
            return null
        }
    }

    override fun showToast(msg: String?) {
        msg?.let {
            ToastUtils.setGravity(-1, -1, -1)
            ToastUtils.showShort(it)
        }
    }

    private fun catchThrowable(e: Throwable) {
        UICoreConfig.throwable(e)
    }

    //--------------------- ProgressDialog start --------------------------

    /**
     * ProgressDialog 取消
     *
     * @param onTouchOutside 点击空白区域
     * @param backCancel     点击返回取消
     */
    open fun setProgressDialogCancel(onTouchOutside: Boolean, backCancel: Boolean) {
        if (!isViewDestroyed()) {
            getViewDelegate().setProgressDialogCanceled(onTouchOutside, backCancel)
        }
    }

    override fun showProgressDialog() {
        if (!isViewDestroyed()) {
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

    /**
     * 代替showSuccessLayout
     */
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

    open fun getDefaultEmptyLayout(): DefaultStatusLayout? {
        return getViewDelegate().emptyLayout
    }

    open fun getDefaultLoadErrorLayout(): DefaultStatusLayout? {
        return getViewDelegate().loadErrorLayout
    }

    //--------------------- status layout end --------------------------

}