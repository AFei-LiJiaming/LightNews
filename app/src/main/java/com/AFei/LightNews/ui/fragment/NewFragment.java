package com.AFei.LightNews.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.AFei.LightNews.ui.activity.MainActivity;
import com.Blinger.base.BaseApplication;
import com.github.florent37.viewanimator.ViewAnimator;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.Blinger.base.base.BaseAdapter;
import com.Blinger.base.base.BaseFragment;
import com.Blinger.base.base.BaseView;
import com.Blinger.base.base.BaseViewHolder;
import com.Blinger.base.utils.CircleImageView;
import com.Blinger.base.utils.LogUtils;
import com.AFei.LightNews.App;
import com.AFei.LightNews.R;
import com.AFei.LightNews.config.Config;
import com.AFei.LightNews.config.Constant;
import com.AFei.LightNews.dao.RecentNewsBeanDao;
import com.AFei.LightNews.model.NewBean;
import com.AFei.LightNews.model.NewTypeBean;
import com.AFei.LightNews.model.RecentNewsBean;
import com.AFei.LightNews.model.UserInfoBean;
import com.AFei.LightNews.presenter.NewPresenter;
import com.AFei.LightNews.ui.activity.WebViewActivity;
import com.AFei.LightNews.utils.FitPopupUtil;
import com.AFei.LightNews.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;


public class NewFragment extends BaseFragment<NewPresenter> implements BaseView {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @Bind(R.id.up_arrow_iv)
    CircleImageView upArrowIv;
    @Bind(R.id.tv_toast)
    TextView tvToast;
    @Bind(R.id.rl_top_toast)
    RelativeLayout rlTopToast;


    private BaseAdapter mAdapter;
    private String uuid;

    private NewTypeBean mData = null;
    private boolean isShow = true;
    private int disy = 0;
    private boolean isFirstRequest = false;
    private String category;
    private int newSignPosition;
    private int isRequest = 0;
    private int isVisible = 10000000;
    private boolean isClick = false;
    private boolean firstClick = false;

    private List<Integer> myPosition = new ArrayList<>();

    public static NewFragment getFragment(NewTypeBean data, String str) {
        NewFragment fragment = new NewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NewFragment.class.getSimpleName(), data);
        fragment.setArguments(bundle);
        //category = str;
        return fragment;
    }

    @Override
    protected NewPresenter createPresenter() {
        return new NewPresenter(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_new;
    }


    @Override
    public void initView(View rootView) {
        super.initView(rootView);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("init", MODE_PRIVATE);
        uuid = sharedPreferences.getString("uuid", "");

        isFirstRequest = true;
        newSignPosition = 10000000;
        myPosition.add(-1);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new BaseAdapter<NewBean>(mActivity, R.layout.item_new) {
            @Override
            public void convert(final BaseViewHolder holder, int position, final NewBean data) {
                //LogUtils.d(Constant.debugName+"NewFragment   ","position is " + position+"   "+ data.getTitle());
                holder.setText(data.getTitle(), R.id.item_title).setTextColor(R.id.item_title,(myPosition.contains(position)) ? getResources().getColor(R.color.colorTime) : getResources().getColor(R.color.colorTitle))
                        .setText(data.getAuthorName(), R.id.item_author)
                        .setText(data.getDate(), R.id.item_time)
                        .setImageResource(data.getThumbnailPicS(), R.id.item_img)
                        .setOnClickListener(R.id.none_interest_iv, view -> {
                            FitPopupUtil fitPopupUtil = new FitPopupUtil(getActivity());
                            fitPopupUtil.setOnClickListener(new FitPopupUtil.OnCommitClickListener() {
                                @Override
                                public void onClick(String reason) {
                                    mPresenter.postEnjoy(data.getUniquekey(), uuid, reason);
                                    mAdapter.removeData(position);
                                }
                            });
                            fitPopupUtil.showPopup(view);
                        })
                        .setOnClickListener(R.id.tv_tail_toast,view -> {
                            mRecyclerView.smoothScrollToPosition(0);
                            mRefresh.autoRefresh();
                        })
                        .setVisible(R.id.item_rl_bottom,(position == -1))
                        .setVisible(R.id.item_rl_tail_toast,(position == isVisible))
                        .itemView.setOnClickListener(view -> {
                                if (newSignPosition > position){
                                    newSignPosition = position;
                                }

                                myPosition.add(position);
                                notifyItemChanged(position);

                                Intent it = new Intent(mActivity, WebViewActivity.class);
                                it.putExtra(WebViewActivity.class.getSimpleName(), data);
                                startActivity(it);
                        });
            }
        };
        //LogUtils.d(Constant.debugName + "NewsFragment   ", "category is " + category);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                if (firstVisibleItem == 0) {
                    if (!isShow) {
                        isShow = true;
                        upArrowIv.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (disy > 25 && isShow) {
                        isShow = false;
                        upArrowIv.setVisibility(View.GONE);
                        disy = 0;
                    }
                    if (disy < -25 && !isShow) {
                        isShow = true;
                        upArrowIv.setVisibility(View.VISIBLE);
                        disy = 0;
                    }
                }

                if ((isShow && dy > 0) || (!isShow && dy < 0)) {
                    disy += dy;
                }
            }
        });

        setRefreshListener();
        mData = getArguments().getParcelable(NewFragment.class.getSimpleName());
//        LogUtils.d(Constant.debugName+"NewFragment","进入initView"+mData.getTitle());
        category = mData.getTitle();
        isUIVisible = true;

    }


    @Override
    protected void netWork() {
        super.netWork();
        if (mData == null) {
            return;
        }

        mPresenter.getNewsList(mData.getDescript(), uuid, MainActivity.index);
    }

    /**
     * 刷新监听
     */
    private void setRefreshListener() {
        mRefresh.setOnRefreshListener(refresh -> {
            //isRequest = true;
            if (BaseApplication.isIsNetConnect()) {
                netWork();
            } else {
                alert(getString(com.Blinger.base.R.string.alert_no_net_repeat));
            }
            mRefresh.finishRefresh(Config.SMARTREFRESH_MAX_REFRESH_TIME);
        });
        mRefresh.setEnableLoadmore(false);
    }

    private void showToast(int num) {
        tvToast.setText("已为你成功推荐"+num+"条新闻");
        //LogUtils.d(Constant.debugName + "showToast   ", "已为你成功推荐"+num+"条新闻");
        rlTopToast.setVisibility(View.VISIBLE);
        ViewAnimator.animate(rlTopToast)
                .newsPaper()
                .duration(500)
                .start()
                .onStop(() -> ViewAnimator.animate(rlTopToast)
                        .bounceOut()
                        .duration(1000)
                        .start());
    }


    private List<NewBean> getRecentNewsList() {
        RecentNewsBeanDao dao = App.mSession.getRecentNewsBeanDao();
        List<RecentNewsBean> recentNewsBeans = dao.queryBuilder().where(RecentNewsBeanDao.Properties.Category.eq(category)).list();
        if (recentNewsBeans == null) {
            recentNewsBeans = new ArrayList<>();
        }
//        LogUtils.d(Constant.debugName+"NewFragment","into getRecentNewsList"+recentNewsBeans.size()+mData.getTitle() );
        //LogUtils.d(Constant.debugName + "getRecentNewsList   ", recentNewsBeans.size() + "");
        List<NewBean> newBeans = new ArrayList<>();

        for (int i = 0; i < recentNewsBeans.size(); i++) {
            NewBean newBean = new NewBean();

            newBean.setUniquekey(recentNewsBeans.get(i).getUniquekey());
            newBean.setTitle(recentNewsBeans.get(i).getTitle());
            newBean.setDate(recentNewsBeans.get(i).getDate());
            newBean.setCategory(recentNewsBeans.get(i).getCategory());
            newBean.setAuthorName(recentNewsBeans.get(i).getAuthorName());
            newBean.setUrl(recentNewsBeans.get(i).getUrl());
            newBean.setThumbnailPicS(recentNewsBeans.get(i).getThumbnailPicS());

            newBeans.add(newBean);
        }
        isFirstRequest = false;
        return newBeans;
    }

    private void setRecentNewsList(List<NewBean> list) {
        RecentNewsBeanDao dao = App.mSession.getRecentNewsBeanDao();
        List<RecentNewsBean> tempList = dao.queryBuilder().where(RecentNewsBeanDao.Properties.Category.eq(category)).list();
//
//        for (int i=0; i<tempList.size();i++){
//            LogUtils.d(Constant.debugName+"newFragment","tempList" + tempList.get(i).getTitle()+"   type   "+mData.getTitle()+i);
//        }

        if (tempList != null) {
            dao.deleteInTx(tempList);
        }

        //LogUtils.d(Constant.debugName + "setRecentNewsList   ", (list.size()) + "条");
        for (int i = 0; i < list.size(); i++) {
            RecentNewsBean recentNewsBean = new RecentNewsBean();

            recentNewsBean.setUniquekey(list.get(i).getUniquekey());
            recentNewsBean.setTitle(list.get(i).getTitle());
            recentNewsBean.setDate(list.get(i).getDate());
            recentNewsBean.setCategory(list.get(i).getCategory());
            recentNewsBean.setAuthorName(list.get(i).getAuthorName());
            recentNewsBean.setUrl(list.get(i).getUrl());
            recentNewsBean.setThumbnailPicS(list.get(i).getThumbnailPicS());
            recentNewsBean.setThumbnailPicS02("123");
            recentNewsBean.setThumbnailPicS03("456");

            //LogUtils.d(Constant.debugName+"setRecentNewsList   ",list.get(i).getCategory());
            dao.insert(recentNewsBean);
        }

//        LogUtils.d(Constant.debugName + "NewFragment   ", "保存成功"+list.size()+mData.getTitle());
    }

    @Override
    public void showData(Object obj) {
        if (obj instanceof List) {
            if (((List) obj).size() == 0) {
                if (isFirstRequest) {
                    //isFirstRequest = false;
//                    LogUtils.d(Constant.debugName + "NewFragment   ", "into isFirstRequest");
                    mAdapter.setData(getRecentNewsList());
                } else {
                    if (mRefresh.isRefreshing()) {
                        mRefresh.finishRefresh();
                    }
                    alert("~休息一下吧~");
                }
            } else {
                if (((List) obj).get(0) instanceof NewBean) {
                    List<NewBean> list = (List<NewBean>) obj;
//                    LogUtils.d(Constant.debugName + "NewFragment   ", list.size() + "");
                    mAdapter.setData(list);
                    if (mRefresh.isRefreshing()) {
                        mRefresh.finishRefresh();
                        //ToastUtil.getInstance().showSuccess(App.getContext(), "数据刷新成功");
                        //isRequest = false;

//                        LogUtils.d(Constant.debugName + "NewFragment   ", mData.getTitle()+"  "+isFirstRequest);
                        isRequest = 1;
                        newSignPosition += list.size();
                        isVisible = newSignPosition;
                        firstClick = true;
                        addSize(list.size());
                    }
                    isFirstRequest = false;
                    showToast(list.size());
                } else if (((List) obj).get(0) instanceof UserInfoBean) {
                    List<UserInfoBean> list = (List<UserInfoBean>) obj;
//                    LogUtils.d(Constant.debugName + "postEnjoy", list.get(0).getInfo());
                    ToastUtil.getInstance().showSuccess(App.getContext(), "反馈成功");
                }
            }
        }
    }

    private void addSize(int size){
        for (int i = 1; i < myPosition.size(); i++){
            myPosition.set(i,myPosition.get(i) + size);
        }
    }
    @Override
    public void showError(String msg) {
        if (mRefresh.isRefreshing()) {
            mRefresh.finishRefresh();
        }
        alert(msg);
    }

    @Override
    public void onDestroyView() {
        setRecentNewsList(mAdapter.getRecentNews());
//        LogUtils.d(Constant.debugName+"NewFragment","into destroyView"+mData.getTitle());
        //mAdapter.getRecentNews();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.up_arrow_iv)
    public void onViewClicked() {
        mRecyclerView.smoothScrollToPosition(0);
        mRefresh.autoRefresh();
    }

}
