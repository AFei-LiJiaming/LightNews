package com.AFei.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


public class SpUtils
{
    private static Context mContext;
    public static String FILE_NAME = "share_data";
    private static SpUtils mUtils;


    public static SharedPreferences sp = null;
    public static SharedPreferences.Editor editor = null;

    private SpUtils(Context context)
    {
        this.mContext = context;
        FILE_NAME = context.getPackageName();
    }


    public static SpUtils getUtils(Context context)
    {
        if (mUtils == null)
        {
            synchronized (SpUtils.class)
            {
                if (mUtils == null)
                {
                    mUtils = new SpUtils(context);
                }
            }
        }
        return mUtils;
    }

    public static void putString(String key, String value)
    {
        ensureEditor();
        editor.putString(key, value);
    }

    public static void putFloat(String key, float value)
    {
        ensureEditor();
        editor.putFloat(key, value);
    }

    public static void putInt(String key, int value)
    {
        ensureEditor();
        editor.putInt(key, value);
    }

    public static void putBoolean(String key, boolean value)
    {
        ensureEditor();
        editor.putBoolean(key, value);
    }


    public static void commit()
    {
        if (editor != null)
        {
            SharedPreferencesCompat.apply(editor);
        }
        sp = null;
        editor = null;
    }

    private static void ensureEditor()
    {
        if (sp == null)
        {
            sp = mContext.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);

            editor = sp.edit();
        }
    }

    public void put(String key, Object object)
    {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    public Object get(String key, Object defaultObject)
    {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public static void remove(String key)
    {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    public static void clear()
    {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean contains(String key)
    {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public static Map<String, ?> getAll()
    {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }


    private static class SharedPreferencesCompat
    {
        private static final Method APPLY_METHOD = findApplyMethod();

        private static Method findApplyMethod()
        {
            try
            {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e)
            {
            }

            return null;
        }

        public static void apply(SharedPreferences.Editor editor)
        {
            try
            {
                if (APPLY_METHOD != null)
                {
                    APPLY_METHOD.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e)
            {
            } catch (IllegalAccessException e)
            {
            } catch (InvocationTargetException e)
            {
            }
            editor.commit();
        }
    }
}