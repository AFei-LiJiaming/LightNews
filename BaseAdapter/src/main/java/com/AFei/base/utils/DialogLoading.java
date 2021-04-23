package com.AFei.base.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.AFei.base.R;



public class DialogLoading extends DialogFragment
{

    private AnimationDrawable mLoading;
    private static DialogLoading mDialog;


    public static void showDialog()
    {
        if (mDialog == null)
        {
            mDialog = new DialogLoading();
        }
        AppCompatActivity activity = (AppCompatActivity) AppManager.getActivityManager().currentActivity();
        mDialog.show(activity.getSupportFragmentManager(), DialogLoading.class.getSimpleName());
    }

    public static void dissDialog()
    {
        if (mDialog != null)
        {
            mDialog.dismiss();
        }
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        View mView = inflater.inflate(R.layout.dialog_loading, container, false);
//        ImageView loading = mView.findViewById(R.id.loading);
//        mLoading = (AnimationDrawable) loading.getDrawable();
//        mLoading.start();
//        return mView;
//    }
//
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState)
//    {
//        super.onActivityCreated(savedInstanceState);
//        setCancelable(false);
//        Window window = getDialog().getWindow();
//        window.setGravity(Gravity.CENTER);
////        window.setWindowAnimations(R.style.animate_dialog);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
        View layout = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.dialog_loading, null);
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        ImageView loading = layout.findViewById(R.id.loading);
        mLoading = (AnimationDrawable) loading.getDrawable();
        dialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                if (mLoading != null && mLoading.isRunning())
                {
                    return;
                }
                mLoading.start();
                Log.e("info", "已开启");
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                mLoading.stop();
                Log.e("info", "已关闭");
            }
        });
        return dialog;
    }
}
