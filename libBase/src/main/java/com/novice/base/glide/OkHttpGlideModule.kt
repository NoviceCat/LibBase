package com.novice.base.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.novice.base.net.HttpsUtils
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * Glide信任所有证书，避免Glide无法加载https图片
 * @author novice
 */
@GlideModule
class OkHttpGlideModule :AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val sslParams = HttpsUtils.getSslSocketFactory()
        val builder = OkHttpClient().newBuilder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager)
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(builder.build()))
    }
}