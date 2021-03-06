package com.wsg.mysign.mydatepicker;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * 测量工具类
 *
 * Util of measure.
 *
 * @author AigeStudio 2015-03-26
 */
public final class MeasureUtil {
    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    public static float BLANK_HEIGHT_RATIO = 0.5f;
    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + BLANK_HEIGHT_RATIO);
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + BLANK_HEIGHT_RATIO);
    }

    public static int px2sp(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().scaledDensity) + BLANK_HEIGHT_RATIO);
    }

    public static int sp2px(Context context, float spValue) {
        return (int) ((spValue * context.getResources().getDisplayMetrics().scaledDensity) + BLANK_HEIGHT_RATIO);
    }

    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return new Point(dm.widthPixels, dm.heightPixels);
    }

    public static float getScreenRate(Context context) {
        Point P = getScreenMetrics(context);
        return ((float) P.y) / ((float) P.x);
    }
    public static int getScreenWidth(Context context) {
        DisplayMetrics metric =context.getResources().getDisplayMetrics();
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        return width;
    }
    public static int getScreenHeight(Context context) {
        DisplayMetrics metric =context.getResources().getDisplayMetrics();
        int height = metric.heightPixels;     // 屏幕高度（像素）
        return height;
    }
}
