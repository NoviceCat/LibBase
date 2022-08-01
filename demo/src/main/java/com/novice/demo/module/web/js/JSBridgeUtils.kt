package com.novice.demo.module.web.js

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.novice.base.utils.ThreadPoolManager

/**
 * @author novice
 *
 */
object JSBridgeUtils {

    /**
     * 根据url 获取bitmap
     * @param imgUrl
     * @param listener
     */
    fun asyncGetBitmap(context: Context, imgUrl: String?, callback: (Bitmap?) -> Unit) {
        ThreadPoolManager.getInstance().runThread {
            try {
                val bitmap = Glide.with(context).asBitmap().load(imgUrl).submit().get()
                callback.invoke(bitmap)
            } catch (e: Throwable) {
                callback.invoke(null)
            }
        }
    }

}