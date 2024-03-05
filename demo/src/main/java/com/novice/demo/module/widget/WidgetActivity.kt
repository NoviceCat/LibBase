package com.novice.demo.module.widget

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.CloseUtils.closeIO
import com.blankj.utilcode.util.LogUtils
import com.novice.base.glide.GlideUtils
import com.novice.base.manager.LinearLayoutManagerWrap
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.R
import com.novice.demo.databinding.ActivityWidgetBinding
import com.novice.demo.module.loadingview.LoadingViewActivity
import com.novice.demo.module.roundimageview.ShapealeImageViewActivity
import com.novice.demo.module.swipe.SwipeMenuActivity
import com.novice.demo.module.tablayout.TabLayoutActivity
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class WidgetActivity : BaseActivity<ActivityWidgetBinding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, WidgetActivity::class.java))
        }
    }

    val imageFileName = "DBSQRImage.png"

    override fun initView() {
        GlideUtils.load(this, R.drawable.banner, mBinding.imgBanner)
        val adapter = WidgetAdapter()
        val list = mutableListOf(
            "文件分享",
            "EasySwipeMenuLayout",
            "TabLayout+ViewPager",
            "LoadingView",
            "ShapeableImageView"
        )
        mBinding.recyclerView.adapter = adapter
        mBinding.recyclerView.layoutManager = LinearLayoutManagerWrap(this)
        adapter.setNewInstance(list)
        adapter.setOnItemClickListener { _, _, position ->
            when (adapter.getItem(position)) {
                "文件分享" -> {
//                    //将mipmap中图片转换成Uri
//                    val imageUri = Uri.parse(
//                        MediaStore.Images.Media.insertImage(
//                            contentResolver, ((resources.getDrawable(R.drawable.bg_big)) as BitmapDrawable).bitmap, "", ""
//                        )
//                    )
//                    val bitmap =
//                        ((resources.getDrawable(R.drawable.bg_big)) as BitmapDrawable).bitmap
//                    val file =
//                        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileName) // 这里的 image.jpg 是自定义的文件名
//                    try {
//                        val fos = FileOutputStream(file)
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos) // JPEG 格式，质量为 90%
//                        fos.flush()
//                        fos.close()
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                    val values = ContentValues()
//                    values.put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
//                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/*")
//                    val imageUri = baseContext.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//

                    try {
//                        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.newlive_praise7)
//
//                        // 创建 ContentValues 对象
//                        val values = ContentValues()
//                        values.put(MediaStore.Images.Media.DISPLAY_NAME, "DBSQRImage") // 设置显示名称
//                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg") // 设置 MIME 类型
//                        // 将 Bitmap 转换为字节数组
//                        val stream = ByteArrayOutputStream()
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
//                        val byteArray = stream.toByteArray()
//                        stream.close()
//                        // 将字节数组添加到 ContentValues 中
//                        values.put(MediaStore.Images.Media.DATA, byteArray)
//
//                        // 执行插入操作
//                        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

//                        val file = File(filesDir, imageFileName) // 这里的 image.jpg 是自定义的文件名
//                        try {
//                            val fos = FileOutputStream(file)
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos) // JPEG 格式，质量为 90%
//                            fos.flush()
//                            fos.close()
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//
//                        val imageUri = FileUtils.getContentUri(baseContext, file)
//
                        // 图片存储到相册中，gallery，会同步到相册  公共部分 sdcard/DCIM/packagename  or sdcard/Pictures/packagename
//                        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.bg_big)
//                        val imageUri = FileUtils.SaveBitmapToDevice(baseContext,bitmap)

                        // 图片保存到内置存储空间中 SDCard/Android/data/packagename/files
                        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.bg_big)
                        val imageUri = FileUtils.saveBitmapToInternalStorage(baseContext,bitmap)

                        var shareIntent = Intent()
                        shareIntent.setAction(Intent.ACTION_SEND)
                        shareIntent.setType("image/*")
                        //其中imgUri为图片的标识符
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "ShareDetail\nLater")
                        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
                        shareIntent = Intent.createChooser(shareIntent, "ShareTitle")
                        startActivity(shareIntent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                "DotView" -> {
                }

                "WheelView" -> {
                }

                "EasySwipeMenuLayout" -> {
                    SwipeMenuActivity.start(this)
                }

                "TabLayout+ViewPager" -> {
                    TabLayoutActivity.start(this)
                }

                "LoadingView" -> {
                    LoadingViewActivity.start(this)
                }

                "MsgView" -> {
                }

                "ToggleButton" -> {
                }

                "ShapeableImageView" -> {
                    ShapealeImageViewActivity.start(this)
                }
            }
        }
    }

}
