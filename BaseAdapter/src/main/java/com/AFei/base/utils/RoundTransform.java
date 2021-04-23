package com.AFei.base.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;


public class RoundTransform implements Transformation
{
    /**
     * 圆角值
     */
    private int radius;

    public RoundTransform(int radius)
    {
        this.radius = radius;
    }

    @Override
    public Bitmap transform(Bitmap source)
    {
        //获取宽,高
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap.Config config = source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
        //创建一张可以操作的正方形图片的位图
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        //创建一个画布Canvas
        Canvas canvas = new Canvas(bitmap);
        //创建画笔
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, paint);
        source.recycle();
        return bitmap;

    }

    @Override
    public String key()
    {
        return "round : radius = " + radius;
    }
}
