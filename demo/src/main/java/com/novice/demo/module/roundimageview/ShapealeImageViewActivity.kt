package com.novice.demo.module.roundimageview

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.databinding.ActivityShapeableImageViewBinding

class ShapealeImageViewActivity :
    BaseActivity<ActivityShapeableImageViewBinding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ShapealeImageViewActivity::class.java))
        }
    }

    override fun initView() {
        setToolBarTitle("ShapeableImageView")
        //        val radius = 50.0f
//        imageView.shapeAppearanceModel = imageView.shapeAppearanceModel
//            .toBuilder()
//            .setTopRightCorner(CornerFamily.ROUNDED, radius)
//            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
//            .build()
    }

}
