package com.novice.demo.module.aspectj

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.View
import com.novice.base.aspectj.BehaviorTrace
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.databinding.ActivityAspectjBinding

class AspectActivity : BaseActivity<ActivityAspectjBinding, DefaultViewModel>(){

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AspectActivity::class.java))
        }
    }

    override fun initView() {
        Handler().postDelayed({
            val pagList = packageManager.getInstalledPackages(0)
            Log.d(UnInsertAspectUtil.TAG,"getResult == " + pagList.size)
        },2000)

        testMethodCalled()

    }

    fun testMethodCalled(){
        Log.d(UnInsertAspectUtil.TAG,"testMethodCalled on activity")
    }

    @BehaviorTrace("上传")
    fun upload(view: View?) {
        SystemClock.sleep(300)
    }

    @BehaviorTrace("下载")
    fun download(view: View?) {
        SystemClock.sleep(400)
    }

    @BehaviorTrace("扫码")
    fun scan(view: View?) {
        SystemClock.sleep(500)
    }
}