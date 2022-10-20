package com.novice.libbasedemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.SPUtils
import com.novice.libbasedemo.network.demo.DemoHttp
import com.novice.base.extension.safe
import com.novice.base.net.init.CoroutinesLoadUtils
import com.novice.base.net.init.HeaderInterceptor
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.BaseViewModel
import com.novice.libbasedemo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainActivity.TestViewModel>() {

    override fun initView() {
        super.initView()
        initObserver()
        mBinding.tvLogin.setOnClickListener {
            viewModel.login()
        }
        mBinding.tvGetUser.setOnClickListener {
            viewModel.getUser()
        }
        mBinding.tvSaveUser.setOnClickListener {
            viewModel.saveUser()
        }
        mBinding.tvGetUserList.setOnClickListener {
            viewModel.getUserList()
        }
        mBinding.tvUpdateUser.setOnClickListener {
            viewModel.updateUser()
        }
        mBinding.tvDelUser.setOnClickListener {
            viewModel.delUserByUserId()
        }
    }

    private fun initObserver() {
        viewModel.loginResult.observe(this) {
            showToast(it?.toString() ?: " 请求失败")
        }
        viewModel.getResult.observe(this) {
            showToast(it?.toString() ?: " 请求失败")
        }
        viewModel.saveResult.observe(this) {
            showToast(it?.toString() ?: " 请求失败")
        }
        viewModel.userList.observe(this) {
            showToast(it?.toString() ?: " 请求失败")
        }
        viewModel.updateUser.observe(this) {
            showToast(it?.toString() ?: " 请求失败")
        }
        viewModel.delUser.observe(this) {
            showToast(it?.toString() ?: " 请求失败")
        }
    }

    class TestViewModel : BaseViewModel() {

        val loginResult = MutableLiveData<Any>()
        val getResult = MutableLiveData<Any>()
        val userList = MutableLiveData<Any>()
        val updateUser = MutableLiveData<Any>()
        val delUser = MutableLiveData<Any>()
        val saveResult = MutableLiveData<Any>()

        fun login() {
            launch({
                showProgressDialog()
                val result = DemoHttp.login("admin", "admin123", true)
                if (200 == result.code()) {
                    val body = result.body()
                    val headers = result.headers()
                    var jsessionId = headers["Set-Cookie"]?.split(";")!![0].safe()
                    SPUtils.getInstance().put(HeaderInterceptor.SP_KEY_SESSION_ID, jsessionId)
                    CoroutinesLoadUtils.init()
                }
            }, { msg, data ->

            })
        }

        fun getUser() {
            netLaunch({
                DemoHttp.getUserByUserId("1")
            }, { msg, data ->
                getResult.value = msg
            }, { code, msg, data ->

            })
        }

        fun saveUser() {
            netLaunch({
                DemoHttp.saveUser("333", "333", "333", "333")
            }, { msg, data ->
                saveResult.value = msg
            }, { code, msg, data ->

            })
        }

        fun getUserList() {
            netLaunch({
                DemoHttp.getUserList()
            }, { msg, data ->
                userList.value = msg
            }, { code, msg, data ->

            })
        }

        fun delUserByUserId() {
            netLaunch({
                DemoHttp.delUser("333")
            }, { msg, data ->
                delUser.value = msg
            }, { code, msg, data ->

            })
        }

        fun updateUser() {
            netLaunch({
                DemoHttp.updateUser("333", "111", "333", "222")
            }, { msg, data ->
                updateUser.value = msg
            }, { code, msg, data ->

            })
        }
    }

}