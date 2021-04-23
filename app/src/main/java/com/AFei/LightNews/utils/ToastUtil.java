package com.AFei.LightNews.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.AFei.LightNews.R;


public class ToastUtil {
    private Toast mToast;
    private TextView mTextView;
    private TimeCount timeCount;
    private String message;
    private Handler handler = new Handler();
    private boolean canceled = true;
    private static ToastUtil Instance = null;

    public static ToastUtil getInstance() {
        if (Instance == null) {
            Instance = new ToastUtil();
        }

        return Instance;
    }
    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }


    /**
     * 自定义居中显示toast
     */

    public void showSuccess(Context context, String msg) {

        this.message = msg;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.toast_success, null);
        mTextView = (TextView) view.findViewById(R.id.toast_msg);
        mTextView.setText(msg);

        if (mToast == null) {
            mToast = new Toast(context);
        }

        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(view);
        //showUntilCancel();
        mToast.show();
        Log.i("ToastUtil", "Toast show... is success information");
    }

    public void showFailue(Context context, String msg) {
        this.message = msg;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.toast_failue, null);
        mTextView = (TextView) view.findViewById(R.id.toast_msg_failue);
        mTextView.setText(msg);

        if (mToast == null) {
            mToast = new Toast(context);
        }

        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(view);
        mToast.show();
        Log.i("ToastUtil", "Toast show...is failue information");
    }

    /**
     * 自定义时长、居中显示toast
     *
     * @param duration
     */
    public void show(int duration) {
        timeCount = new TimeCount(duration, 1000);
        Log.i("ToastUtil", "Toast show...");
        if (canceled) {
            timeCount.start();
            canceled = false;
            showUntilCancel();
        }
    }

    /**
     * 隐藏toast
     */
    private void hide() {
        if (mToast != null) {
            mToast.cancel();
        }
        canceled = true;
        Log.i("ToastUtil", "Toast that customed duration hide...");
    }

    private void showUntilCancel() {
        if (canceled) { //如果已经取消显示，就直接return
            return;
        }
        mToast.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("ToastUtil", "Toast showUntilCancel...");
                showUntilCancel();
            }
        }, Toast.LENGTH_SHORT);
    }

    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); //millisInFuture总计时长，countDownInterval时间间隔(一般为1000ms)
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setText(message + ": " + millisUntilFinished / 1000 + "s后消失");
        }

        @Override
        public void onFinish() {
            hide();
        }
    }
}
