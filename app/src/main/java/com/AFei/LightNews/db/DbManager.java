package com.AFei.LightNews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.AFei.LightNews.dao.DaoMaster;
import com.AFei.LightNews.dao.DaoSession;

import org.greenrobot.greendao.database.Database;



public class DbManager
{
    private static DbManager mManager;
    private DaoSession mSession;

    private DbManager()
    {

    }

    public static DbManager getManager()
    {
        if (mManager == null)
        {
            synchronized (DbManager.class)
            {
                if (mManager == null)
                {
                    mManager = new DbManager();
                }
            }
        }
        return mManager;
    }


    public DaoSession initialization(Context context)
    {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, context.getPackageName() + ".db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        mSession = new DaoMaster(db).newSession();
        mSession.clear();
        return mSession;
    }

}
