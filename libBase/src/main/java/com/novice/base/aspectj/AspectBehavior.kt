package com.novice.base.aspectj

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

@Aspect
class AspectBehavior {
    //指定使用BehaviorTrace注解的所有包下所有类的所有方法，为一个切入点
    @Pointcut("execution(@com.novice.base.aspectj.BehaviorTrace * *(..))")
    fun methodAnnotatedWithBehaviorTrace() {
    }

    //争对上面切入点methodAnnotatedWithBehaviorTrace的所有连接点进行处理
    @Around("methodAnnotatedWithBehaviorTrace()")
    fun weaveJoinPoint(joinPoint: ProceedingJoinPoint): Any? {
        var ret: Any? = null
        try {
            //获取方法签名
            val methodSignature = joinPoint.signature as MethodSignature
            //获取注解
            val annotation = methodSignature.method.getAnnotation(BehaviorTrace::class.java)
            //执行之前记录下时间
            val startTime = System.currentTimeMillis()
            //方法执行
            ret = joinPoint.proceed()
            //方法执行完后的耗时
            val diffTime = System.currentTimeMillis() - startTime
            val clzName = methodSignature.declaringType.simpleName
            val methodName = methodSignature.name
            Log.d("NoviceLog", String.format("功能：%s 类：%s中方法：%s执行耗时:%d ms", annotation.value, clzName, methodName, diffTime))
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
        return ret
    }
}
