package com.AFei.LightNews.utils;

import android.text.TextUtils;

import com.Blinger.base.utils.LogUtils;
import com.AFei.LightNews.config.Constant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class Md5 {

    public static String md5(String string, String slat) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest((string + slat).getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }

            LogUtils.d(Constant.debugName+"MD5___result", result);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
