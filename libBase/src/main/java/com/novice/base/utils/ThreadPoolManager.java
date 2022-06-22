package com.novice.base.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池管理类
 *
 * @author novice
 */
public class ThreadPoolManager {

    private static ThreadPoolManager self = null;
    /**
     * 常规线程池
     */
    private ExecutorService mCommonThreadPool;

    public static ThreadPoolManager getInstance() {
        if (self == null) {
            self = new ThreadPoolManager();
        }
        return self;
    }

    public ThreadPoolManager() {
        mCommonThreadPool = Executors.newFixedThreadPool(10);
    }

    /**
     * 项目中执行线程操作
     *
     * @param runnable
     * @author wangbx
     * @version 4.3  2016/6/20
     */
    public synchronized void runThread(Runnable runnable) {
        mCommonThreadPool.execute(runnable);
    }


    public ExecutorService getCommonThreadPool() {
        return mCommonThreadPool;
    }
}
