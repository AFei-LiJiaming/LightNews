package com.AFei.base.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


//文件操作工具类
public class FileUtils
{
    /**
     * 切好的头像保存到本地
     * @param data
     * @param context
     * @return
     */
    public static File saveImageFile(Intent data, Context context)
    {
        Bundle extras = data.getExtras();
        File file = null;
        if (extras != null)
        {
            Bitmap bm = extras.getParcelable("data");
            //将要保存图片的路径
            file = new File(context.getApplicationContext().getFilesDir() + String.valueOf(System.currentTimeMillis())+".png");
            try
            {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bm.compress(Bitmap.CompressFormat.JPEG, 20, bos);
                bos.flush();
                bos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
                LogUtils.e(e.getMessage());
            }
        }
        return file;
    }
}
