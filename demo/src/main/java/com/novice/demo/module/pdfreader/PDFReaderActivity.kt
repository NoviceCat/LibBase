package com.novice.demo.module.pdfreader

import android.content.Context
import android.content.Intent
import com.novice.base.pdf.PDFViewPager
import com.novice.base.pdf.adapter.PDFPagerAdapter
import com.novice.base.uicore.ui.BaseActivity
import com.novice.base.uicore.viewmodel.DefaultViewModel
import com.novice.demo.databinding.ActivityPdfReaderBinding

class PDFReaderActivity : BaseActivity<ActivityPdfReaderBinding, DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, PDFReaderActivity::class.java))
        }
    }

    val fileList = arrayListOf(
//        "/data/user/0/com.dbs.ideal.debug/files/terms-and-conditions.pdf",
//        "/data/user/0/com.dbs.ideal.debug/files/electronic-banking-terms-and-conditions.pdf",
        "/data/user/0/com.novice.libbasedemo/files/electronic-banking-terms-and-conditions.pdf",
        "/data/user/0/com.novice.libbasedemo/files/terms-and-conditions.pdf"
    )

    override fun initView() {
        mBinding.tvClick.setOnClickListener {
            showPDFReader(fileList[0])
        }
    }

    var pdfViewPager: PDFViewPager? = null

    private fun showPDFReader(url: String) {
        showProgressDialog()
        releasePDFReader()
        pdfViewPager = PDFViewPager(baseContext, url)
        mBinding.flContainer.addView(pdfViewPager)
        hideProgressDialog()
    }


    override fun onDestroy() {
        super.onDestroy()
        releasePDFReader()
    }

    private fun releasePDFReader(){
        (pdfViewPager?.adapter as PDFPagerAdapter?)?.close()
        mBinding.flContainer.removeAllViews()
        pdfViewPager = null
    }

}