package com.AFei.LightNews.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.AFei.LightNews.App;
import com.AFei.LightNews.R;
import com.AFei.LightNews.model.NewTypeBean;
import com.AFei.LightNews.ui.MyView.FragmentAdapter;
import com.AFei.LightNews.ui.fragment.AboutFragment;
import com.AFei.LightNews.ui.fragment.NewFragment;
import com.AFei.LightNews.utils.ToastUtil;
import com.Blinger.base.base.BaseActivity;
import com.Blinger.base.base.BasePresenter;
import com.Blinger.base.utils.SpUtils;
import com.Blinger.base.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Bind(R.id.toolBar)
    Toolbar mToolBar;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.navigationView)
    NavigationView mNavigationView;
    @Bind(R.id.drawer)
    DrawerLayout mDrawer;
    @Bind(R.id.main_tl)
    TabLayout mainTl;
    @Bind(R.id.fragment_vp)
    ViewPager fragmentVp;
//    String[] newsTypeTitle = {"头条","社会","国内","娱乐","体育","军事","科技","财经","时尚"};


//    private HashMap<String, NewFragment> fragments = new HashMap<>();
//    private List<Fragment> fragmentList = new ArrayList<>();
public static int index;



    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        StatusBarUtils.setColorNoTranslucentForDrawerLayout(this, mDrawer, Color.parseColor("#f54343"));
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationIcon(R.mipmap.icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        hideNavigationViewScrollBars();

        mainTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        //List<String> titles = Arrays.asList(newsTypeTitle);
//        mainTl.addTab(mainTl.newTab().setText("头条"));
//        mainTl.addTab(mainTl.newTab().setText("社会"));
//        mainTl.addTab(mainTl.newTab().setText("国内"));
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentVp.setAdapter(fragmentAdapter);
        mainTl.setupWithViewPager(fragmentVp);


        //ToolBar点击事件
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_opean:
                        mDrawer.openDrawer(Gravity.END);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        setNavigationItemSelectedListener();

    }

    /**
     * 隐藏NavigationView的滚动条
     */
    private void hideNavigationViewScrollBars() {
        if (mNavigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) mNavigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
                navigationMenuView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            }
        }
    }

    /**
     * NavagationView菜单点击事件
     */
    private void setNavigationItemSelectedListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //收藏:
                    case R.id.menu_collection:
                        startActivity(new Intent(MainActivity.this, CollectionActivity.class));
                        break;
                    // 足迹
                    case R.id.menu_history:
                        startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                        break;
                    //切换引擎
                    case R.id.menu_recommend:
                        showSingleChoiceDialog();
                        break;
                    //关于
                    case R.id.menu_about:
                        AboutFragment about = new AboutFragment();
                        about.show(getSupportFragmentManager(), AboutFragment.class.getSimpleName());
                        break;
                    default:
                        break;
                }
                mDrawer.closeDrawers();
                return true;
            }
        });
    }

    int yourChoice;

    private void showSingleChoiceDialog() {
        SharedPreferences sharedPreferences = getSharedPreferences("init", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putInt("recommend_type",0);
        index = sharedPreferences.getInt("recommend_type", 0);

        final String[] items = {"热点新闻模式", "智能个性化推荐模式", "自由推送模式"};
        yourChoice = -1;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(MainActivity.this);

        singleChoiceDialog.setTitle("请选择推荐引擎模式");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, index,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            editor.putInt("recommend_type", yourChoice);
                            editor.apply();
                            Toast toast = Toast.makeText(App.getContext(), null, Toast.LENGTH_SHORT);
                            toast.setText("你选择了" + items[yourChoice]);
                            toast.show();
                        }
                    }
                });
        singleChoiceDialog.show();
    }

//    private void showFragment(String s) {
//        if (s.equals(mTvTitle.getText().toString())) {
//            return;
//        }
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction ft = manager.beginTransaction();
//        NewFragment fragment = fragments.get(s);
//        hideFragment(ft);
//        if (fragment == null) {
//            NewTypeBean data = NewTypeBean.getNewTypeBean(s);
//            fragment = NewFragment.getFragment(data, s);
//            fragments.put(s, fragment);
//            ft.add(R.id.frame, fragment);
//        } else {
//            ft.show(fragment);
//        }
//        ft.commit();
//        SpUtils.getUtils(this).put("title", s);
//        mTvTitle.setText(s);
//    }

    /**
     * 隐藏添加过的Fragment，避免重复添加
     *
     * @param
     */
//    private void hideFragment(FragmentTransaction ft) {
//        for (Map.Entry<String, NewFragment> map : fragments.entrySet()) {
//            NewFragment value = map.getValue();
//            ft.hide(value);
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //        getMenuInflater().inflate(R.menu.menu_navigation,menu);

//        final MenuItem menuItem = menu.findItem(R.id.menu_theme);
//        FrameLayout frameLayout = (FrameLayout) menuItem.getActionView();
//
//        SwitchCompat switchCompat = (SwitchCompat) frameLayout.findViewById(R.id.view_switch);
//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    LogUtils.d(Constant.debugName+"MainActivity","mySwitch");
//                }else {
//                    LogUtils.d(Constant.debugName+"MainActivity","mySwitch");
//                }
//            }
//        });

//        return true;
//        MenuItem switchItem = menu.findItem(R.id.menu_theme);
//        mSwitch = (SwitchCompat) switchItem.getActionView().findViewById(R.id.view_switch);
////        if (mNfcAdapter != null) {
////            mSwitch.setChecked(mNfcAdapter.isEnabled());
////        }
//        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                ToastUtil.getInstance().showSuccess(App.getContext(),"点击成功");
//            }
//        });
        return true;
    }

}
