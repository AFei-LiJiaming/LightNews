package com.AFei.LightNews.ui.MyView;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.AFei.LightNews.R;

import butterknife.Bind;
import butterknife.OnClick;


public class AdjustTypeDialog extends AppCompatDialog implements View.OnClickListener {
    private TextView smallTv, normalTv, largeTv, largestTv, closeDialogTv;
    private ImageView smallIv, normalIv, largeIv, largestIv;
    private RelativeLayout smallRl, normalRl, largeRl, largestRl;

    private Context mContext;
    private int type;

    public AdjustTypeDialog(Context context, int theme, int type) {
        super(context, theme);
        this.mContext = context;
        this.getWindow().setWindowAnimations(R.style.main_menu_animstyle);
        this.type = type;
        init();
        setLayout();
    }

    private void setSmall() {
        //smallTv.setTextColor(mContext.getResources().getColor(R.color.colorMain));
        smallIv.setImageResource(R.drawable.spot_red);

        normalTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        normalIv.setImageResource(R.drawable.spot_grey);

        largeTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        largeIv.setImageResource(R.drawable.spot_grey);

        largestTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        largestIv.setImageResource(R.drawable.spot_grey);
    }

    private void setNormal() {
        smallTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        smallIv.setImageResource(R.drawable.spot_grey);

        // normalTv.setTextColor(mContext.getResources().getColor(R.color.colorMain));
        normalIv.setImageResource(R.drawable.spot_red);

        largeTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        largeIv.setImageResource(R.drawable.spot_grey);

        largestTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        largestIv.setImageResource(R.drawable.spot_grey);
    }

    private void setLarge() {
        smallTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        smallIv.setImageResource(R.drawable.spot_grey);

        normalTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        normalIv.setImageResource(R.drawable.spot_grey);

        //largeTv.setTextColor(mContext.getResources().getColor(R.color.colorMain));
        largeIv.setImageResource(R.drawable.spot_red);

        largestTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        largestIv.setImageResource(R.drawable.spot_grey);
    }

    private void setLargest() {
        smallTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        smallIv.setImageResource(R.drawable.spot_grey);

        normalTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        normalIv.setImageResource(R.drawable.spot_grey);

        largeTv.setTextColor(mContext.getResources().getColor(R.color.colorTitle));
        largeIv.setImageResource(R.drawable.spot_grey);

        //largestTv.setTextColor(mContext.getResources().getColor(R.color.colorMain));
        largestIv.setImageResource(R.drawable.spot_red);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.small_rl:
                type = 1;
                setSmall();
                onAdjustTypeListener.onAdjustType(type);
                break;
            case R.id.normal_rl:
                type = 2;
                setNormal();
                onAdjustTypeListener.onAdjustType(type);
                break;
            case R.id.largest_rl:
                type = 3;
                setLargest();
                onAdjustTypeListener.onAdjustType(type);
                break;
            case R.id.large_rl:
                type = 4;
                setLarge();
                onAdjustTypeListener.onAdjustType(type);
                break;
            case R.id.close_dialog_tv:
                dismiss();
                break;
        }
    }

    public interface OnAdjustTypeListener {
        void onAdjustType(int type);
    }

    private OnAdjustTypeListener onAdjustTypeListener;

    public void setOnAdjustTypeListener(OnAdjustTypeListener onAdjustTypeListener) {
        this.onAdjustTypeListener = onAdjustTypeListener;
    }

    private void init() {
        setContentView(R.layout.dialog_adjust_type);
        smallTv = (TextView) findViewById(R.id.small_tv);
        smallIv = (ImageView) findViewById(R.id.small_iv);
        smallRl = (RelativeLayout) findViewById(R.id.small_rl);

        normalTv = (TextView) findViewById(R.id.normal_tv);
        normalIv = (ImageView) findViewById(R.id.normal_iv);
        normalRl = (RelativeLayout) findViewById(R.id.normal_rl);

        largeTv = (TextView) findViewById(R.id.large_tv);
        largeIv = (ImageView) findViewById(R.id.large_iv);
        largeRl = (RelativeLayout) findViewById(R.id.large_rl);

        largestTv = (TextView) findViewById(R.id.largest_tv);
        largestIv = (ImageView) findViewById(R.id.largest_iv);
        largestRl = (RelativeLayout) findViewById(R.id.largest_rl);

        closeDialogTv = (TextView) findViewById(R.id.close_dialog_tv);

        smallRl.setOnClickListener(this);
        normalRl.setOnClickListener(this);
        largeRl.setOnClickListener(this);
        largestRl.setOnClickListener(this);
        closeDialogTv.setOnClickListener(this);

        switch (type) {
            case 1:
                setSmall();
                break;
            case 2:
                setNormal();
                break;
            case 3:
                setLarge();
                break;
            case 4:
                setLargest();
                break;
        }
    }

    private void setLayout() {
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(p);
        setCancelable(true);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
