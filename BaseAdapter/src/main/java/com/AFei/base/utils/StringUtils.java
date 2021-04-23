package com.AFei.base.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils
{

    @SuppressWarnings("手机号码正则表达式验证")
    public static boolean isMobile(String mobiles)
    {
        String telRegex = "^[1][3,4,5,7,8][0-9]{9}$";
        if (TextUtils.isEmpty(mobiles))
        {
            return false;
        } else
        {
            Pattern p = Pattern.compile(telRegex);
            Matcher m = p.matcher(mobiles);
            return m.matches();
        }
    }


    /**
     * 读取本地assets中Json文件
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName)
    {
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getApplicationContext().getAssets();
        //使用IO流读取json文件内容
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName), "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String splitString(String str){
        String[] splitStr;
        if (str.contains("区")){
            splitStr = str.split("区");
            splitStr[0] = splitStr[0]+"区";
        }else if (str.contains("县")){
            splitStr = str.split("县");
            splitStr[0] = splitStr[0]+"县";
        }else {
            return "来自火星";
        }

        return splitStr[0];
    }


}
