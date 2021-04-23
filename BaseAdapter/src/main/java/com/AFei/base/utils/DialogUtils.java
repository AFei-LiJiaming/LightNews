package com.AFei.base.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.AFei.base.R;



public class DialogUtils extends DialogFragment
{
    private static   DialogUtils mDialog;
    private  OnItemClickListener mOnItemClickListener;


    public static DialogUtils getUtils()
    {
        if (mDialog == null)
        {
            synchronized (DialogUtils.class)
            {
                if (mDialog == null)
                {
                    mDialog = new DialogUtils();
                }
            }
        }
        return mDialog;
    }


    public  void showDialog(String msg, OnItemClickListener listener)
    {
        if (listener != null)
        {
            mOnItemClickListener = listener;
        }
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        mDialog.setArguments(bundle);
        AppCompatActivity activity = (AppCompatActivity) AppManager.getActivityManager().currentActivity();
        mDialog.show(activity.getSupportFragmentManager(), DialogUtils.class.getSimpleName());
    }


    public static void dissDialog()
    {
        if (mDialog != null && mDialog.isVisible())
        {
            mDialog.dismiss();
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
        View layout = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.dialog_msg, null);
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView msg = layout.findViewById(R.id.dialog_msg);
        String message = getArguments().getString("msg");
        msg.setText(message);

        TextView cancel = layout.findViewById(R.id.dialog_cancel);
        TextView ok = layout.findViewById(R.id.dialog_ok);
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mOnItemClickListener.clickOk();
                dismiss();
            }
        });
        return dialog;
    }


    public interface OnItemClickListener
    {
        /**
         * The ok click listening of Dialog
         */
        void clickOk();
    }

}
