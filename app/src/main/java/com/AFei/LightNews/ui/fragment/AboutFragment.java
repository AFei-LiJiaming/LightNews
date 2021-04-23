package com.AFei.LightNews.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.AFei.LightNews.R;


public class AboutFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
        View layout = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.dialog_about, null);
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }
        });
        return dialog;
    }
}
