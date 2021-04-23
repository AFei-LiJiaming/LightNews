package com.AFei.LightNews.ui.MyView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.AFei.LightNews.config.Constant;
import com.AFei.LightNews.model.NewTypeBean;
import com.AFei.LightNews.ui.fragment.NewFragment;
import com.Blinger.base.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class FragmentAdapter extends FragmentPagerAdapter {
    private String[] titles = {"头条", "社会", "国内", "健康", "国际", "军事", "体育", "财经", "汽车", "科技"};
    private HashMap<String, NewFragment> fragments = new HashMap<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        String title = titles[position];
        LogUtils.d(Constant.debugName + "FragmentAdapter    ", title);
        NewFragment newFragment = fragments.get(title);
        if (newFragment == null) {
            NewTypeBean data = NewTypeBean.getNewTypeBean(title);
            newFragment = NewFragment.getFragment(data, title);
            fragments.put(title, newFragment);
        }
        return newFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
