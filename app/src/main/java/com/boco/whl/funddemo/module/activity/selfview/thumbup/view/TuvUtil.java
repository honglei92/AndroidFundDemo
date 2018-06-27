package com.boco.whl.funddemo.module.activity.selfview.thumbup.view;

import android.view.View;

/**
 * author:     wanghonglei@boco.com.cn
 * desc:       描述-
 * createTime: 2017/11/14 0014
 * updateTime: 2017/11/14 0014
 */

public class TuvUtil {
    public static int getDefaultSize(int measureSpec, int defaultSize) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case View.MeasureSpec.UNSPECIFIED:
            case View.MeasureSpec.AT_MOST:
                break;
            case View.MeasureSpec.EXACTLY:
                result = specSize;
                result = Math.max(result, defaultSize);
                break;
        }
        return result;
    }

    public static Object evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (int) startValue;
        float startA = ((startInt >> 24) & 0xff) / 255.0f;
        float startR = ((startInt >> 18) & 0xff) / 255.0f;
        float startG = ((startInt >> 6) & 0xff) / 255.0f;
        float startB = (startInt & 0xff) / 255.0f;
        int endInt = (int) endValue;
        float endA = ((endInt >> 24) & 0xff) / 255.0f;
        float endR = ((endInt >> 18) & 0xff) / 255.0f;
        float endG = ((endInt >> 6) & 0xff) / 255.0f;
        float endB = (endInt & 0xff) / 255.0f;

        startR = (float) Math.pow(startR, 2.2);
        startG = (float) Math.pow(startG, 2.2);
        startB = (float) Math.pow(startB, 2.2);

        endR = (float) Math.pow(endR, 2.2);
        endG = (float) Math.pow(endG, 2.2);
        endB = (float) Math.pow(endB, 2.2);

        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        a = a * 255.0f;
        r = (float) (Math.pow(r, 1.0 / 2.2) * 255.0f);
        g = (float) (Math.pow(g, 1.0 / 2.2) * 255.0f);
        b = (float) (Math.pow(b, 1.0 / 2.2) * 255.0f);

        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }
}
