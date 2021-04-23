package com.AFei.base.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

//手机常用功能
public class ScreenUtils
{
    /**
     * 获取手机屏幕的宽高
     * @param mContext
     * @return
     */
    public static int[] getScreen(Context mContext)
    {
        WindowManager mManager = (WindowManager) mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        mManager.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }
}
