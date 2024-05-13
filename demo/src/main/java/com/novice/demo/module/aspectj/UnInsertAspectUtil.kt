package com.novice.demo.module.aspectj

import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut

@Aspect
class UnInsertAspectUtil {

    companion object {
        const val TAG = "NoviceLog"

        const val POINT_CUT_MAIN_ACTIVITY = "call(* com.novice.demo.module.aspectj.AspectActivity.**(..))"

        const val POINT_CUT_APPLICATION_MANAGER = "call(* android.app.ApplicationPackageManager.**(..))" //

        const val POINT_CUT_PACKAGE_MANAGER = "call(* android.content.pm.PackageManager.**(..))" // TEST PASS

    }

    @Pointcut(POINT_CUT_PACKAGE_MANAGER)
    fun onCreate() {
    }

    @Before("onCreate()")
    fun beforeCreate(joinPoint: JoinPoint) {
        try {
            val methodName = joinPoint.signature.name
            Log.d(TAG, "beforeCreate --> $methodName")

        } catch (e: Exception) {
            Log.d(TAG, "beforeCreate: failed on error: " + e.message!!)
        }
    }

    @After("onCreate()")
    fun afterCreate(joinPoint: JoinPoint) {
        try {
            val methodName = joinPoint.signature.name
            Log.d(TAG, "afterCreate --> $methodName")
        } catch (e: Exception) {
            Log.d(TAG, "afterCreate: failed on error: " + e.message)
        }
    }
}