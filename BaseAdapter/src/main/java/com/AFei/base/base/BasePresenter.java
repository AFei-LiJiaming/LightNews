package com.AFei.base.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



public class BasePresenter<V extends BaseView> implements BaseSubscriptionHelper
{

    protected V mView;
    private Reference<V> mReference;
    private CompositeDisposable mDisposables;


    public BasePresenter(V view)
    {
        attchView(view);
        mView = mReference.get();
    }


    public void attchView(V view)
    {
        mReference = new WeakReference<V>(view);
    }


    public void subscribe(Observable o1, Observer o2)
    {
        o1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o2);
    }


    @Override
    public void add(Disposable disposable)
    {
        if (mDisposables == null)
        {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    @Override
    public void cancle(Disposable disposable)
    {
        if (mDisposables != null)
        {
            mDisposables.delete(disposable);
        }
    }

    @Override
    public void cancleAll()
    {
        if (mDisposables != null)
        {
            mDisposables.clear();
        }
    }
}
