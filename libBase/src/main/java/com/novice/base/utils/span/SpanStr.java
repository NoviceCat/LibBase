package com.novice.base.utils.span;

import android.text.style.ClickableSpan;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 分段颜色
 * @author : novice
 */
public class SpanStr {

    @NonNull
    private String text;
    @ColorRes
    private int color;

    /**
     * pixels
     */
    private int size;
    /**
     * 支持点击事件.
     * 注意要调用才生效textView.setMovementMethod(LinkMovementMethod.getInstance());
     */
    @Nullable
    private ClickableSpan clickableSpan;

    public SpanStr(@NonNull String text, @ColorRes int color) {
        this(text, 0, color, null);
    }

    public SpanStr(@NonNull String text, int size, @ColorRes int color) {
        this(text, size, color, null);
    }

    public SpanStr(@NonNull String text, @ColorRes int color, @Nullable ClickableSpan clickableSpan) {
        this(text, 0, color, clickableSpan);
    }

    public SpanStr(@NonNull String text, int size, @ColorRes int color, @Nullable ClickableSpan clickableSpan) {
        this.text = text;
        this.color = color;
        this.size = size;
        this.clickableSpan = clickableSpan;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(@ColorRes int color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ClickableSpan getClickableSpan() {
        return clickableSpan;
    }

    public void setClickableSpan(ClickableSpan clickableSpan) {
        this.clickableSpan = clickableSpan;
    }

}
