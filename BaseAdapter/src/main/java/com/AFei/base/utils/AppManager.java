package com.AFei.base.utils;

import android.app.Activity;

import java.util.Stack;


//Activity管理
public class AppManager
{

    private static AppManager instance;
    private static Stack<Activity> stack_activity;

    private AppManager()
    {
    }

    /**
     * 单个实例
     */
    public static AppManager getActivityManager()
    {
        if (instance == null)
        {
            instance = new AppManager();
        }
        return instance;
    }

    public int getLength()
    {
        return stack_activity.size();
    }


    /**
     * add Activity to stack_activity
     */
    public void addActivity(Activity activity)
    {
        if (stack_activity == null)
        {
            stack_activity = new Stack<>();
        }
        stack_activity.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity()
    {
        Activity mActivity = stack_activity.lastElement();
        return mActivity;
    }


    /**
     * finish当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity()
    {
        Activity activity = stack_activity.lastElement();
        finishActivity(activity);
    }

    /**
     * finish 指定的Activity
     */
    public void finishActivity(Activity activity)
    {
        if (activity != null)
        {
            stack_activity.remove(activity);
            activity.finish();
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    /**
     * finish 指定类名的Activity
     */
    public void finishActivity(Class<?> cls)
    {
        for (Activity activity : stack_activity)
        {
            if (activity.getClass().equals(cls))
            {
                finishActivity(activity);
            }
        }
    }

    /**
     * finish all Activity
     */
    public void finishAllActivity()
    {
        for (Activity activity : stack_activity)
        {
            activity.finish();
        }
        stack_activity.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit()
    {
        try
        {
            finishAllActivity();
            System.exit(0);

        } catch (Exception e)
        {
        }
    }

    public boolean isExist(String cls)
    {
        for (Activity activity : stack_activity)
        {
            if (activity.getClass().getSimpleName().toString().equals(cls))
            {
                return true;
            }
        }
        return false;
    }

    public void showActivity()
    {
        for (Activity activity : stack_activity)
        {
            System.out.println("=====" + activity.toString());
        }
    }
}