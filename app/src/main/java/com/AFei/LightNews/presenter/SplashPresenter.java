package com.AFei.LightNews.presenter;

import com.Blinger.base.base.BasePresenter;
import com.Blinger.base.base.BaseView;
import com.AFei.LightNews.model.UserInfoBean;
import com.AFei.LightNews.service.ApiService;
import com.AFei.LightNews.service.BaseObserver;
import com.AFei.LightNews.service.RetrofitManager;

import java.util.List;

import io.reactivex.disposables.Disposable;



public class SplashPresenter extends BasePresenter<BaseView>{
    public SplashPresenter(BaseView view) {
        super(view);
    }

    public void postNewUser(String UID,String name,int imageType,double latitude,double longitude){
        subscribe(
                RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                        .postNewUser(UID,name,imageType,latitude,longitude),
                new BaseObserver <List <UserInfoBean>>() {
                    @Override
                    public void onSuccess(List<UserInfoBean> userInfoBeans) {
                        mView.showData(userInfoBeans);
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.showError(msg);
                    }

                    @Override
                    public void onSubcribes(Disposable d) {
                        add(d);
                    }

                }
        );
    }
}
