package com.AFei.LightNews.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.AFei.LightNews.App;
import com.AFei.LightNews.R;
import com.AFei.LightNews.dao.HistoryBeanDao;
import com.AFei.LightNews.dao.NewBeanDao;
import com.AFei.LightNews.model.BaseBean;
import com.AFei.LightNews.model.HistoryBean;
import com.AFei.LightNews.model.NewBean;
import com.Blinger.base.base.BaseActivity;
import com.Blinger.base.base.BaseAdapter;
import com.Blinger.base.base.BasePresenter;
import com.Blinger.base.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class HistoryActivity extends BaseActivity {
    @Bind(R.id.tv_title_history)
    TextView mTvTitle;
    @Bind(R.id.recyclerView_history)
    RecyclerView mRecyclerView;

    private BaseAdapter mAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_history;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        super.initView(savedInstanceState);
        mTvTitle.setText(getString(R.string.tv_history_title));
        EventBus.getDefault().register(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseAdapter<NewBean>(this, R.layout.item_new) {
            @Override
            public void convert(BaseViewHolder holder, int position, final NewBean data) {
                holder.setText(data.getTitle(), R.id.item_title)
                        .setText(data.getAuthorName(), R.id.item_author)
                        .setText(data.getDate(), R.id.item_time)
                        .setImageResource(data.getThumbnailPicS(), R.id.item_img)
                        .setVisible(R.id.none_interest_iv, false)
                        .setVisible(R.id.item_rl_bottom, false)
                        .setVisible(R.id.item_rl_tail_toast, false)
                        .itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent it = new Intent(HistoryActivity.this, WebViewActivity.class);
                        it.putExtra(WebViewActivity.class.getSimpleName(), data);
                        startActivity(it);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        updateHistory();
    }

    private void updateHistory() {
        HistoryBeanDao dao = App.mSession.getHistoryBeanDao();
        List<HistoryBean> list = dao.loadAll();
        List<NewBean> newBeans = new ArrayList<>();

        for (int i = list.size() - 1; i >= 0; i--) {
            NewBean bean = new NewBean();
            bean.setAuthorName(list.get(i).getAuthorName());
            bean.setCategory(list.get(i).getCategory());
            bean.setDate(list.get(i).getDate());
            bean.setTitle(list.get(i).getTitle());
            bean.setUniquekey(list.get(i).getUniquekey());
            bean.setUrl(list.get(i).getUrl());
            bean.setThumbnailPicS(list.get(i).getThumbnailPicS());

            newBeans.add(bean);
        }

        mAdapter.setData(newBeans);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void obEventMain(BaseBean data) {
        if (200 == data.getCode()) {
            updateHistory();
        }
    }

    @OnClick(R.id.img_finish_history)
    public void onViewClicked() {
        doFinish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
