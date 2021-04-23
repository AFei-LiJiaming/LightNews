package com.AFei.LightNews.utils;



import android.content.Context;



public class ScreenUtils {

    private ScreenUtils() {
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

}