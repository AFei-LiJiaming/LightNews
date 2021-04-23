package com.AFei.LightNews.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

//
//import com.github.moduth.blockcanary.BlockCanary;



import com.tencent.smtt.sdk.QbSdk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class InitializeManager extends IntentService
{
    private static final String TAG = InitializeManager.class.getSimpleName();
    private static final String BUGLY_ID = "f37b38735f";


    public InitializeManager()
    {
        super(TAG);
    }

    public static void init(Context context)
    {
        Intent it = new Intent(context, InitializeManager.class);
        it.setAction(TAG);
        context.startService(it);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if (intent != null)
        {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && action.equals(TAG))
            {
                //Logger.addLogAdapter(new AndroidLogAdapter());
                //Stetho.initializeWithDefaults(getApplicationContext());
                /*初始化Bugly*/

                /*初始化内存泄漏检测工具*/
                //LeakCanary.install(getApplication());
                /*BlockCanar UI卡顿检测工具*/
                //BlockCanary.install(getApplicationContext(), new AppBlockCanaryContext()).start();
                /*初始化X5内核*/
                QbSdk.initX5Environment(getApplicationContext(), null);
            }
        }
    }



    private String getProcessName(int pid)
    {
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName))
            {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable)
        {
            throwable.printStackTrace();
        } finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            } catch (IOException exception)
            {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
