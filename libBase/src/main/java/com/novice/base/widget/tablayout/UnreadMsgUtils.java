package com.novice.base.widget.tablayout;


import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.novice.base.widget.MsgView;


/**
 * 未读消息提示View,显示小红点或者带有数字的红点:
 * 数字一位,圆
 * 数字两位,圆角矩形,圆角是高度的一半
 * 数字超过两位,显示99+
 */

/**
 *  未读消息提示View,显示小红点或者带有数字的红点:
 *  数字一位,圆
 *  数字两位,圆角矩形,圆角是高度的一半
 *  数字超过两位,显示99+
 * @author novice
 */
public class UnreadMsgUtils {

    public static void show(MsgView msgView, int num) {
        show(msgView, num,true);
    }

    public static void show(MsgView msgView,int num,boolean isStroke){
        if (msgView == null) {
            return;
        }
        msgView.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) msgView.getLayoutParams();
        DisplayMetrics dm = msgView.getResources().getDisplayMetrics();
        msgView.setVisibility(View.VISIBLE);
        msgView.setSingleLine();
        if (num <= 0) {//圆点,设置默认宽高
            msgView.setText("");
            lp.width = (int) ( (isStroke ? 10 : 8) * dm.density);
            lp.height = (int) ( (isStroke ? 10 : 8) * dm.density);
            msgView.setLayoutParams(lp);
        } else {
            lp.height = (int) (17 * dm.density);
            if (num > 0 && num < 10) {//圆
                lp.width = (int) (17 * dm.density);
                msgView.setText(num + "");
            } else if (num > 9 && num < 100) {//圆角矩形,圆角是高度的一半,设置默认padding
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                msgView.setPadding((int) (3 * dm.density), 0, (int) (3 * dm.density), 0);
                msgView.setText(num + "");
            } else {//数字超过两位,显示
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                msgView.setPadding((int) (3 * dm.density),0, (int) (3 * dm.density), 0);
                msgView.setText("99+");
            }
            msgView.setLayoutParams(lp);
        }
    }

    public static void show(MsgView msgView, String text) {
        if (msgView == null) {
            return;
        }
        msgView.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) msgView.getLayoutParams();
        DisplayMetrics dm = msgView.getResources().getDisplayMetrics();
        msgView.setVisibility(View.VISIBLE);
        msgView.setSingleLine();
        lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        lp.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        msgView.setPadding((int) (6 * dm.density),0, (int) (6 * dm.density), 0);
        msgView.setText(text);
        msgView.setLayoutParams(lp);
    }




    public static void setSize(MsgView msgView, int size) {
        if (msgView == null) {
            return;
        }
        ViewGroup.LayoutParams lp = msgView.getLayoutParams();
        lp.width = size;
        lp.height = size;
        msgView.setLayoutParams(lp);
    }

}
