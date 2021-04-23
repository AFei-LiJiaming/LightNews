package com.AFei.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;


import com.AFei.base.utils.NetChangeReceiver;
import com.AFei.base.utils.NetUtils;


public class  BaseApplication extends Application
{
    /**手机网络状态**/
    private static boolean isNetConnect = true;
    private static Context context;

    public static boolean isIsNetConnect()
    {
        return isNetConnect;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        //Log打印日志
        context = getApplicationContext();
        //Logger.addLogAdapter(new AndroidLogAdapter());
        //数据库调试
        //Stetho.initializeWithDefaults(getApplicationContext());
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks()
        {
            private NetChangeReceiver receiver;

            @Override
            public void onActivityCreated(final Activity activity, Bundle bundle)
            {
            }

            @Override
            public void onActivityStarted(final Activity activity)
            {
                receiver = new NetChangeReceiver();
                //对网络状态进行监听
                receiver.setListener(new NetChangeReceiver.OnNetchangeListener()
                {
                    @Override
                    public void onNetchangeListener(int state)
                    {
                        if (state == NetUtils.STATE_NONE)
                        {
                            Toast.makeText(activity, getString(R.string.alert_no_net), Toast.LENGTH_SHORT).show();
                            isNetConnect = false;
                        } else
                        {
                            isNetConnect = true;
                        }
                    }
                });
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                registerReceiver(receiver, filter);
            }

            @Override
            public void onActivityResumed(Activity activity)
            {
            }

            @Override
            public void onActivityPaused(Activity activity)
            {
            }

            @Override
            public void onActivityStopped(Activity activity)
            {
                if (receiver != null)
                {
                    unregisterReceiver(receiver);
                    receiver = null;
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle)
            {
            }

            @Override
            public void onActivityDestroyed(Activity activity)
            {
            }
        });
    }

    public static Context getContext(){
        return context;
    }
}
