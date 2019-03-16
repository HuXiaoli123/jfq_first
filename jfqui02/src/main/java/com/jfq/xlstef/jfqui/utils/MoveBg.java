package com.jfq.xlstef.jfqui.utils;

import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class MoveBg {
    /**
     * 移动方法
     *
     * @param v
     *            需要移动的View
     * @param startX
     *            起始x坐标
     * @param toX
     *            终止x坐标
     * @param startY
     *            起始y坐标
     * @param toY
     *            终止y坐标
     */
    public static void moveFrontBg(View v,final View text, int startX, int toX, int startY, int toY) {
        TranslateAnimation anim = new TranslateAnimation(startX, toX, startY, toY);
        anim.setDuration(200);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((TextView)text).setTextColor(Color.rgb(225,25,50));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);
    }
}
