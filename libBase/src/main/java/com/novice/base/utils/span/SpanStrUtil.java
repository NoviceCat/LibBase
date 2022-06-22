package com.novice.base.utils.span;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;

import androidx.core.content.ContextCompat;

/**
 * 分段文字处理
 * @author : novice
 */
public class SpanStrUtil {

    public static SpannableString getSpannableString(Context context, String text, SpanStr... pair) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(text);
        if (pair == null || pair.length == 0) {
            return spannableString;
        }
        int start;
        int end = 0;
        for (int i = 0; i < pair.length; i++) {
            final String txt = pair[i].getText();
            final int color = pair[i].getColor();
            final int size = pair[i].getSize();
            final ClickableSpan clickableSpan = pair[i].getClickableSpan();
            start = end;
            end = start + txt.length();
            if (size > 0) {
                spannableString.setSpan(new AbsoluteSizeSpan(size), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (clickableSpan != null) {
                spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (color > 0) {
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }

}
