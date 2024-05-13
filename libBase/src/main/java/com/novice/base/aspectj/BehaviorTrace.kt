package com.novice.base.aspectj

/**
 * 用于注解连接点
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class BehaviorTrace(val value: String)
