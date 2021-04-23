package com.AFei.LightNews.presenter;

import com.Blinger.base.base.BasePresenter;
import com.Blinger.base.base.BaseView;
import com.AFei.LightNews.model.NewBean;
import com.AFei.LightNews.model.UserInfoBean;
import com.AFei.LightNews.service.ApiService;
import com.AFei.LightNews.service.BaseObserver;
import com.AFei.LightNews.service.RetrofitManager;

import java.util.List;

import io.reactivex.disposables.Disposable;


public class NewPresenter extends BasePresenter<BaseView>
{
    public NewPresenter(BaseView view)
    {
        super(view);
    }


    public void getNewsList(String type, String userId, int index)
    {
        //LogUtils.d(Constant.debugName+"NewPresenter   ",type);
        if ("top".equals(type)){
            subscribe(
                    RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                            .getMyNewsList(userId, index),
                    new BaseObserver<List<NewBean>>()
                    {
                        @Override
                        public void onSuccess(List<NewBean> list)
                        {
                            mView.showData(list);
                        }

                        @Override
                        public void onFailed(String msg)
                        {
                            mView.showError(msg);
                        }

                        @Override
                        public void onSubcribes(Disposable d)
                        {
                            add(d);
                        }
                    }
            );
        }else {
            subscribe
                    (
                            RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                                    .getNewsList("toutiao".equals(type) ? "top" : type, userId, index),
                            new BaseObserver<List<NewBean>>()
                            {
                                @Override
                                public void onSuccess(List<NewBean> list)
                                {
                                    mView.showData(list);
                                }

                                @Override
                                public void onFailed(String msg)
                                {
                                    mView.showError(msg);
                                }

                                @Override
                                public void onSubcribes(Disposable d)
                                {
                                    add(d);
                                }
                            }
                    );
        }

    }




    //String newUid,@Query("userUniqueKey") String userId,@Query("feedbackContent") String feedbackContent
    public void postEnjoy(String newUid,String userId,String feedbackContent){
        subscribe
                (
                        RetrofitManager.getManager().getRetrofit().create(ApiService.class)
                                .postEnjoy(newUid, userId, feedbackContent),
                        new BaseObserver<List<UserInfoBean>>() {
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
