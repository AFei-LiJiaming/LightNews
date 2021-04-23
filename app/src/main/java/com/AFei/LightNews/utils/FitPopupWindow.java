package com.AFei.LightNews.utils;



import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.AFei.LightNews.R;



public class FitPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    private View anchorView;
    private Activity context;

    private int mWindowWidth;

    private static final int PADDING = 0;
    //x轴坐标
    private int mXCoordinate;

    private int mHorizontal;
    private int mVertical;
    private int[] windowPos;
    private FitPopupWindowLayout mFitPopupWindowLayout;

    public FitPopupWindow(Activity context) {
        init(context, ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public FitPopupWindow(Activity context, int width, int height) {
        mWindowWidth = width;
        init(context, width, height);
    }


    private void init(Activity context, int width, int height) {
        this.context = context;
        //popupwindow会默认忽略最外层的大小,所以应该再嵌套一层
        setWidth(width);
        setHeight(height);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setOutsideTouchable(true);
        setFocusable(true);
        setOnDismissListener(this);

        setAnimationStyle(R.style.popp_anim);
    }

    public void setView(View contentView, View anchorView) {
        this.anchorView = anchorView;
        windowPos = calculatePopWindowPos(anchorView, contentView);

        mFitPopupWindowLayout = new FitPopupWindowLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getHeight() - FitPopupWindowLayout.SHARP_HEIGHT);
        layoutParams.bottomMargin = FitPopupWindowLayout.SHARP_HEIGHT;

        contentView.setLayoutParams(layoutParams);
        mFitPopupWindowLayout.setOrientation(getHorizontal(), getVertical()
                , getXCoordinate());
        mFitPopupWindowLayout.addView(contentView);
        setContentView(mFitPopupWindowLayout);
    }


    public void show() {
        showAtLocation(anchorView, Gravity.TOP | Gravity.END
                , windowPos[0], windowPos[1]);
        update();
        Window window = context.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.7f;
        window.setAttributes(lp);

    }


    /**
     * @param anchorView  弹出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    protected int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        final int anchorWidth = anchorView.getWidth();
        mXCoordinate = anchorLoc[0];
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        int windowHeight = contentView.getMeasuredHeight();
        mWindowWidth = mWindowWidth > 0 ? mWindowWidth : contentView.getMeasuredWidth();

        // 判断需要向上弹出还是向下弹出,如果要改变弹出策略,改变此处即可
        // 目前是根据屏幕的一半进行判断
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < screenHeight / 2);

        // 判断需要向左弹出还是向右弹出
        final boolean isNeedShowLeft = (anchorLoc[0] < mWindowWidth / 2);

        setHorizontal(isNeedShowLeft ? FitPopupWindowLayout.LEFT : FitPopupWindowLayout.RIGHT);
        setVertical(isNeedShowUp ? FitPopupWindowLayout.UP : FitPopupWindowLayout.DOWN);

//        windowPos[0] = isNeedShowLeft ?
//                anchorLoc[0] - windowWidth : anchorLoc[0] + anchorWidth;

        windowPos[0] = (screenWidth - mWindowWidth) / 2;

        windowPos[1] = isNeedShowUp ?
                anchorLoc[1] - windowHeight - PADDING - FitPopupWindowLayout.SHARP_HEIGHT
                : anchorLoc[1] + anchorHeight + PADDING;

        return windowPos;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onDismiss() {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 1f;
        context.getWindow().setAttributes(lp);

    }

    public int getXCoordinate() {
        if (mXCoordinate > mWindowWidth / 2) {
            mXCoordinate = mWindowWidth - mXCoordinate - anchorView.getWidth() + 50;
        }
        return mXCoordinate;
    }

    private int getHorizontal() {
        return mHorizontal;
    }

    /**
     * @param mHorizontal 设置水平方向
     */
    private void setHorizontal(int mHorizontal) {
        this.mHorizontal = mHorizontal;
    }

    private int getVertical() {
        return mVertical;
    }

    /**
     * @param mVertical 设置竖直方向
     */
    private void setVertical(int mVertical) {
        this.mVertical = mVertical;
    }


    private void startAnimation(boolean isStart) {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation sa;
        sa = new ScaleAnimation(0, 1f, 0, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(150);
        sa.setInterpolator(new FastOutSlowInInterpolator());
        sa.setFillAfter(true);

        AlphaAnimation aa = new AlphaAnimation(0f, 1f);
        aa.setDuration(150);
        aa.setFillAfter(true);

        animationSet.addAnimation(sa);
        animationSet.addAnimation(aa);

        mFitPopupWindowLayout.startAnimation(animationSet);

    }
}
