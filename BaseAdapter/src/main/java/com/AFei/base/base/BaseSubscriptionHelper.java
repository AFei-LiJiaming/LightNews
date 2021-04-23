package com.AFei.base.base;

import io.reactivex.disposables.Disposable;



public interface BaseSubscriptionHelper
{
    /**
     * 将请求添加进队列
     * @param disposable
     */
    void add(Disposable disposable);

    /**
     * 取消单个请求
     * @param disposable
     */
    void cancle(Disposable disposable);

    /**
     * 取消所有的请求
     */
    void cancleAll();
}
