package com.AFei.LightNews;

import android.content.Context;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import com.Blinger.base.BaseApplication;
import com.AFei.LightNews.dao.DaoSession;
import com.AFei.LightNews.db.DbManager;
import com.AFei.LightNews.utils.InitializeManager;

import java.text.SimpleDateFormat;


public class App extends BaseApplication
{
    public static DaoSession mSession;
    private static Context mContext;



    static
    {
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = "下拉推荐";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "释放推荐";
        ClassicsHeader.REFRESH_HEADER_FINISH = "推荐完成";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在推荐";

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater()
        {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout)
            {
                //全局设置主题颜色
                layout.setPrimaryColorsId(R.color.colorBG, R.color.colorEtHint);
                //指定为经典Header，默认是 贝塞尔雷达Header
                ClassicsHeader classicsHeader = new ClassicsHeader(context);

                return classicsHeader
                        .setTimeFormat(new SimpleDateFormat());
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater()
        {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout)
            {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mContext = getApplicationContext();
        mSession = DbManager.getManager().initialization(this);
        InitializeManager.init(this);
    }



    public static Context getContent()
    {
        return mContext;
    }
}
