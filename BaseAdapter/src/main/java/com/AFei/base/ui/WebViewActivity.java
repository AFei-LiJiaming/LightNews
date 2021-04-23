package com.AFei.base.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.AFei.base.R;
import com.AFei.base.base.BaseActivity;
import com.AFei.base.base.BasePresenter;


public class WebViewActivity extends BaseActivity
{
    RelativeLayout mLayout;
    TextView mTvTitle;
    ImageView mImgFinish;
    private WebView mWebView;

    private String mUrl;

    @Override
    protected BasePresenter createPresenter()
    {
        return null;
    }

    @Override
    protected int getResourceId()
    {
        return R.layout.activity_web_view;
    }


    @Override
    public void initView(Bundle savedInstanceState)
    {
        super.initView(savedInstanceState);
        mLayout = findViewById(R.id.layout);
        mTvTitle = findViewById(R.id.tv_title);
        mImgFinish = findViewById(R.id.img_finish);

        mTvTitle.setText(getIntent().getStringExtra("title"));
        mImgFinish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doFinish();
            }
        });
        mUrl = getIntent().getStringExtra(this.getClass().getSimpleName());

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(this.getApplicationContext());
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setLayoutParams(mParams);
        mLayout.addView(mWebView);

        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urls)
            {
                view.loadUrl(urls);
                return true;
            }
        });
        mWebView.loadUrl(mUrl);

        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(false);
        //设置自适应屏幕，两者合用（下面这两个方法合用）
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //缩放操作
        //支持缩放，默认为true。是下面那个的前提。
        webSettings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);
        //其他细节操作
        //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setDomStorageEnabled(true);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mWebView != null)
        {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }

}
