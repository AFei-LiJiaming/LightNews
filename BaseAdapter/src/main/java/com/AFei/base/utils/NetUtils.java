package com.AFei.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetUtils
{
    /**
     * 无网络
     */
    public static final int STATE_NONE = -1;
    /**
     * 移动网络
     */
    public static final int STATE_MOBILE = 0;
    /**
     * wifi
     */
    public static final int STATE_WIFI = 1;


    public static int getNetState(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
        {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI))
            {
                //wifi
                return STATE_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE))
            {
                //mobile
                return STATE_MOBILE;
            }
        } else
        {
            //网络异常
            return STATE_NONE;
        }
        return STATE_NONE;
    }
}
