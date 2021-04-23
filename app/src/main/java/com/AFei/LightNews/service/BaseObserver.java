package com.AFei.LightNews.service;

import com.google.gson.Gson;
import com.Blinger.base.net.ApiException;
import com.Blinger.base.utils.LogUtils;
import com.AFei.LightNews.config.Constant;
import com.AFei.LightNews.model.BaseBean;
import com.AFei.LightNews.model.DataBean;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;



public abstract class BaseObserver<T> implements Observer<BaseBean<DataBean<T>>>
{
    @Override
    public void onSubscribe(Disposable d)
    {
        onSubcribes(d);
    }

    @Override
    public void onNext(BaseBean<DataBean<T>> model)
    {
        if (model.getCode() == 0)
        {
            //LogUtils.d(Constant.debugName,String.valueOf(model.getT()));
            //LogUtils.d(Constant.debugName,"into onNext");

            onSuccess(model.getT().getT());
//            if (model.getT().getCode() != 0){
//
//            }else {
//                LogUtils.d(Constant.debugName,"into 休息一下");
//                onFailed("~休息一下~");
//            }

            return;
        }
        ApiException e = new ApiException(model.getCode(), model.getMsg());
        onError(e);
    }

    @Override
    public void onError(Throwable e)
    {
        String errorMsg = null;
        LogUtils.e(Constant.debugName,"into error");
        //没有网络，请求失败
        if (e instanceof IOException)
        {
            errorMsg = "Please Check Your Net!!";
            // 网络异常，http 请求失败，即 http 状态码不在 [200, 300) 之间, such as: "server internal error".
        } else if (e instanceof HttpException)
        {
            HttpException exception = (HttpException) e;
            try
            {
                String json = exception.response().errorBody().string();
                Gson gson = new Gson();
                BaseBean data = gson.fromJson(json, BaseBean.class);
                errorMsg = data.getMsg();
            } catch (IOException e1)
            {
                e1.printStackTrace();
                errorMsg = e1.getMessage();
            }
            //网络正常，http 请求成功，服务器返回逻辑错误
        } else if (e instanceof ApiException)
        {
            errorMsg = ((ApiException) e).getMsg();
            //其他未知错误
        } else
        {
            errorMsg = "Unknown Error";
        }
        LogUtils.e(Constant.debugName,e.getMessage()+"     "+e.toString());
        onFailed(errorMsg);
    }

    @Override
    public void onComplete()
    {
    }


    /**
     * Request a successful callback
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * Request failed callback
     * @param msg
     */
    public abstract void onFailed(String msg);

    /**
     * Add Disposable for easy administration
     * @param d
     */
    public abstract void onSubcribes(Disposable d);
}