package com.novice.base.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.Target
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


/**
 * author novice
 *
 */
object GlideUtils {

    private var placeholderDrawable: Int = 0
    private var errorDrawable: Int = 0

    fun initConfig(@DrawableRes placeholderDrawable: Int, @DrawableRes errorDrawable: Int) {
        this.placeholderDrawable = placeholderDrawable
        this.errorDrawable = errorDrawable
    }

    fun onLowMemory(context: Context) {
        Glide.with(context).onLowMemory()
    }

    fun onTrimMemory(context: Context, level: Int) {
        Glide.with(context).onTrimMemory(level)
    }

    fun load(context: Context?, url: String?, imageView: ImageView) {
        context?.let {
            val default = RequestOptions
                .placeholderOf(placeholderDrawable)
                .error(errorDrawable)
                .centerCrop()
            Glide.with(it).load(url ?: "").apply(default).into(imageView)
        }
    }

    fun loadNoPlace(context: Context?, url: String?, imageView: ImageView) {
        context?.let {
            Glide.with(it).load(url ?: "").centerCrop().into(imageView)
        }
    }

    fun load(context: Context?, @DrawableRes resourceId: Int, imageView: ImageView) {
        context?.let {
            val default = RequestOptions
                .placeholderOf(placeholderDrawable)
                .error(errorDrawable)
                .centerCrop()
            Glide.with(it).load(resourceId).centerCrop().apply(default).into(imageView)
        }
    }

    fun loadNoPlace(context: Context?, @DrawableRes resourceId: Int, imageView: ImageView) {
        context?.let {
            Glide.with(it).load(resourceId).centerCrop().into(imageView)
        }
    }

    fun load(context: Context?, url: String?, imageView: ImageView, options: RequestOptions) {
        context?.let {
            Glide.with(it).load(url ?: "").centerCrop().apply(options).into(imageView)
        }
    }

    fun load(context: Context?, url: String?, imageView: ImageView, @DrawableRes placeholderId: Int) {
        context?.let {
            val default = RequestOptions
                .placeholderOf(placeholderId)
                .error(placeholderId)
                .centerCrop()
            Glide.with(it).load(url ?: "").centerCrop().apply(default).into(imageView)
        }
    }

    fun load(context: Context?, @DrawableRes resourceId: Int, imageView: ImageView, @DrawableRes placeholderId: Int) {
        context?.let {
            val default = RequestOptions
                .placeholderOf(placeholderId)
                .error(placeholderId)
                .centerCrop()
            Glide.with(it).load(resourceId).centerCrop().apply(default).into(imageView)
        }
    }

    fun loadRound(context: Context?, url: String?, imageView: ImageView, radius: Float) {
        context?.let {
            val options = bitmapTransform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCornersTransformation(SizeUtils.dp2px(radius), 0, RoundedCornersTransformation.CornerType.ALL)
                )
            )
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
            Glide.with(it).load(url ?: "")
                .apply(options)
                .into(imageView)
        }
    }

    fun loadRoundNoPlace(context: Context?, url: String?, imageView: ImageView, radius: Float) {
        context?.let {
            val options = bitmapTransform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCornersTransformation(SizeUtils.dp2px(radius), 0, RoundedCornersTransformation.CornerType.ALL)
                )
            )
            Glide.with(it).load(url ?: "")
                .apply(options)
                .into(imageView)
        }
    }

    fun loadRound(context: Context?, url: String?, imageView: ImageView, radius: Float, cornerType: RoundedCornersTransformation.CornerType) {
        context?.let {
            val options = bitmapTransform(MultiTransformation(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(radius), 0, cornerType)))
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
            Glide.with(it).load(url ?: "")
                .apply(options)
                .into(imageView)
        }
    }

    fun loadRoundNoPlace(context: Context?, url: String?, imageView: ImageView, radius: Float, cornerType: RoundedCornersTransformation.CornerType) {
        context?.let {
            val options = bitmapTransform(MultiTransformation(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(radius), 0, cornerType)))
            Glide.with(it).load(url ?: "")
                .apply(options)
                .into(imageView)
        }
    }

    fun loadCicle(context: Context?, url: String?, imageView: ImageView) {
        context?.let {
            val options = bitmapTransform(CircleCrop())
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
            Glide.with(it).load(url ?: "")
                .apply(options)
                .into(imageView)
        }
    }

    fun loadCicleNoPlace(context: Context?, url: String?, imageView: ImageView) {
        context?.let {
            val options = bitmapTransform(CircleCrop())
            Glide.with(it).load(url ?: "")
                .apply(options)
                .into(imageView)
        }
    }

    fun loadCicle(context: Context?, url: String?, imageView: ImageView, @DrawableRes placeholderId: Int) {
        context?.let {
            val options = bitmapTransform(CircleCrop())
                .placeholder(placeholderId)
                .error(placeholderId)
            Glide.with(it).load(url ?: "")
                .apply(options)
                .into(imageView)
        }
    }

    fun loadBlur(context: Context?, url: String?, imageView: ImageView) {
        context?.let {
            val options = bitmapTransform(BlurTransformation())
                .placeholder(placeholderDrawable)
                .error(errorDrawable)
            Glide.with(it).load(url ?: "")
                .apply(options)
                .into(imageView)
        }
    }

    fun loadBlurNoPlace(context: Context?, url: String?, imageView: ImageView) {
        context?.let {
            val options = bitmapTransform(BlurTransformation())
            Glide.with(it).load(url ?: "")
                .apply(options)
                .into(imageView)
        }
    }

    fun loadGif(context: Context?, @DrawableRes resourceId: Int, imageView: ImageView) {
        context?.let {
            val default = RequestOptions
                .placeholderOf(placeholderDrawable)
                .error(errorDrawable)
                .fitCenter()
            Glide.with(it).load(resourceId).fitCenter().apply(default).into(imageView)
        }
    }

    fun loadGifNoPlace(context: Context?, @DrawableRes resourceId: Int, imageView: ImageView) {
        context?.let {
            Glide.with(it).load(resourceId).fitCenter().into(imageView)
        }
    }

    fun loadGif(context: Context?, url: String?, imageView: ImageView, callback: () -> Unit) {
        try {
            context?.let {
                Glide.with(context).load(url ?: "").listener(object :
                    RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        callback.invoke()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        if (resource is GifDrawable) {
                            try {
                                val gifStateField = GifDrawable::class.java.getDeclaredField("state")
                                gifStateField.isAccessible = true
                                val gifStateClass = Class.forName("com.bumptech.glide.load.resource.gif.GifDrawable\$GifState")
                                val gifFrameLoaderField = gifStateClass.getDeclaredField("frameLoader")
                                gifFrameLoaderField.isAccessible = true
                                val gifFrameLoaderClass = Class.forName("com.bumptech.glide.load.resource.gif.GifFrameLoader")
                                val gifDecoderField = gifFrameLoaderClass.getDeclaredField("gifDecoder")
                                gifDecoderField.isAccessible = true
                                val gifDecoderClass = Class.forName("com.bumptech.glide.gifdecoder.GifDecoder")
                                val gifDecoder = gifDecoderField.get(gifFrameLoaderField[gifStateField[resource]])
                                val getDelayMethod = gifDecoderClass.getDeclaredMethod("getDelay", Int::class.javaPrimitiveType)
                                getDelayMethod.isAccessible = true
                                //设置只播放一次
                                resource.setLoopCount(1)
                                //获得总帧数
                                val count: Int = resource.frameCount
                                var delay = 0L
                                for (i in 0 until count) {
                                    //计算每一帧所需要的时间进行累加
                                    delay += getDelayMethod.invoke(gifDecoder, i) as Int
                                }
                                imageView.postDelayed({
                                    callback.invoke()
                                }, delay)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                callback.invoke()
                            }
                        } else {
                            callback.invoke()
                        }
                        return false
                    }

                }).into(imageView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback.invoke()
        }
    }

}