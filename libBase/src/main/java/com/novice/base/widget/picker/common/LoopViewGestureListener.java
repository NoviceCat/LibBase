package com.novice.base.widget.picker.common;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.novice.base.widget.picker.WheelView;


/**
 * 手势监听
 *
 * @author novice
 *
 */
public final class LoopViewGestureListener extends GestureDetector.SimpleOnGestureListener {

    private final WheelView wheelView;


    public LoopViewGestureListener(WheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        wheelView.scrollBy(velocityY);
        return true;
    }
}
