package com.novice.base.pdf.subscaleview.decoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;


public interface ImageDecoder {


    @NonNull
    Bitmap decode(Context context, @NonNull Uri uri) throws Exception;

}