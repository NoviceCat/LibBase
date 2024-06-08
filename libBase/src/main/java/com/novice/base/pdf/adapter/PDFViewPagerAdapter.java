package com.novice.base.pdf.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.novice.base.R;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class PDFViewPagerAdapter extends PagerAdapter {

    public static class Builder {
        Context context;
        String pdfPath = "";
        int offScreenSize = DEFAULT_OFFSCREENSIZE;
        float renderQuality = DEFAULT_QUALITY;

        public Builder(Context context) {
            this.context = context;
        }

        public PDFViewPagerAdapter.Builder setOffScreenSize(int offScreenSize) {
            this.offScreenSize = offScreenSize;
            return this;
        }

        public PDFViewPagerAdapter.Builder setPdfPath(String path) {
            this.pdfPath = path;
            return this;
        }

        public PDFViewPagerAdapter create() {
            PDFViewPagerAdapter adapter = new PDFViewPagerAdapter(context, pdfPath);
            adapter.offScreenSize = offScreenSize;
            adapter.renderQuality = renderQuality;
            return adapter;
        }
    }

    protected static final int FIRST_PAGE = 0;
    protected static final float DEFAULT_QUALITY = 2.0f;
    protected static final int DEFAULT_OFFSCREENSIZE = 1;

    protected String pdfPath;
    protected Context context;
    protected PdfRenderer renderer;
    protected SimpleBitmapPool bitmapContainer;
    protected LayoutInflater inflater;

    protected float renderQuality;
    protected int offScreenSize;


    public PDFViewPagerAdapter(Context context, String pdfPath) {
        this.pdfPath = pdfPath;
        this.context = context;
        this.renderQuality = DEFAULT_QUALITY;
        this.offScreenSize = DEFAULT_OFFSCREENSIZE;
        init();
    }

    @SuppressWarnings("NewApi")
    protected void init() {
        try {
            renderer = new PdfRenderer(getSeekableFileDescriptor(pdfPath));
            inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            PdfRendererParams params = extractPdfParamsFromFirstPage(renderer, renderQuality);
            bitmapContainer = new SimpleBitmapPool(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("NewApi")
    protected PdfRendererParams extractPdfParamsFromFirstPage(PdfRenderer renderer, float renderQuality) {
        PdfRenderer.Page samplePage = getPDFPage(renderer, FIRST_PAGE);
        PdfRendererParams params = new PdfRendererParams();

        params.setRenderQuality(renderQuality);
        params.setOffScreenSize(offScreenSize);
        params.setWidth((int) (samplePage.getWidth() * renderQuality));
        params.setHeight((int) (samplePage.getHeight() * renderQuality));

        samplePage.close();

        return params;
    }

    protected ParcelFileDescriptor getSeekableFileDescriptor(String path) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor;
        File pdfCopy = new File(path);
        if (pdfCopy.exists()) {
            parcelFileDescriptor = ParcelFileDescriptor.open(pdfCopy, ParcelFileDescriptor.MODE_READ_ONLY);
            return parcelFileDescriptor;
        }
        URI uri = URI.create(String.format("file://%s", path));
        parcelFileDescriptor = context.getContentResolver().openFileDescriptor(Uri.parse(uri.toString()), "rw");
        return parcelFileDescriptor;
    }


    @Override
    @SuppressWarnings("NewApi")
    public Object instantiateItem(ViewGroup container, int position) {
        View v = inflater.inflate(R.layout.view_pdf_page, container, false);
        ImageView ssiv = v.findViewById(R.id.imageView);
        if (renderer == null || getCount() < position) {
            return v;
        }
        PdfRenderer.Page page = getPDFPage(renderer, position);
        Bitmap bitmap = bitmapContainer.get(position);
        ssiv.setImageBitmap(bitmap);
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        page.close();
        container.addView(v, 0);
        return v;
    }

    @SuppressWarnings("NewApi")
    protected PdfRenderer.Page getPDFPage(PdfRenderer renderer, int position) {
        return renderer.openPage(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // bitmap.recycle() causes crashes if called here.
        // All bitmaps are recycled in close().
    }

    @SuppressWarnings("NewApi")
    public void close() {
        releaseAllBitmaps();
        if (renderer != null) {
            renderer.close();
        }
    }

    protected void releaseAllBitmaps() {
        if (bitmapContainer != null) {
            bitmapContainer.clear();
        }
    }

    @Override
    @SuppressWarnings("NewApi")
    public int getCount() {
        return renderer != null ? renderer.getPageCount() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }

}
