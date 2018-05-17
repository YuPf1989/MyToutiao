package com.rain.mytoutiao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rain.mytoutiao.R;
import com.rain.mytoutiao.activity.NewsChannelActivity;
import com.rain.mytoutiao.adapter.BaseViewPagerAdapter;
import com.rain.mytoutiao.base.LazyLoadFragment;
import com.rain.mytoutiao.db.ChannelDao;
import com.rain.mytoutiao.db.ChannelDao_Table;
import com.rain.mytoutiao.eventbus.IsRefreshTab;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author:rain
 * Date:2018/5/15 9:33
 * Description:
 */
public class HomeTabView extends LazyLoadFragment {
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Unbinder unbinder;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tabTitles;
    private Map<String, Fragment> map = new HashMap<>();
    private static final String TAG  = "HomeTabView";
    private BaseViewPagerAdapter pagerAdapter;

    // 是否重新刷新tab，重建fragment
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recreatTab(IsRefreshTab isRefreshTab) {
        boolean isRefresh = isRefreshTab.isRefresh;
        if (isRefresh) {
            initTabs();
            pagerAdapter.recreatItems(fragments,tabTitles);
        }
    }

    @Override
    public void fetchData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_tab;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        initTabs();
        // 初始化viewpager
        initViewPager();
        EventBus.getDefault().register(this);
    }

    private void initViewPager() {
        tab.setupWithViewPager(viewPager);
        pagerAdapter = new BaseViewPagerAdapter(getChildFragmentManager(), fragments, tabTitles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(15);
    }

    private void initTabs() {
        fragments = new ArrayList<>();
        tabTitles = new ArrayList<>();
        List<ChannelDao> channelDaos = SQLite.select().from(ChannelDao.class).where(ChannelDao_Table.is_enable.eq(true)).queryList();
        if (channelDaos.size() == 0) {
            ChannelDao.addInitData();
            channelDaos = SQLite.select().from(ChannelDao.class).where(ChannelDao_Table.is_enable.eq(true)).queryList();
            Log.e(TAG, "initTabs: "+channelDaos.size());
        }

        for (ChannelDao dao :
                channelDaos) {
            Fragment fragment = null;
            String id = dao.id;
            String name = dao.name;
            tabTitles.add(name);
            switch (id) {
                case "essay_joke":
                    if (map.containsKey(id)) {
                        fragments.add(map.get(id));
                    } else {
//                        fragment = JokeContentView.newInstance();
                        fragment = NewsContentView.newInstance();
                        fragments.add(fragment);
                    }
                    break;

                case "question_and_answer":
                    if (map.containsKey(id)) {
                        fragments.add(map.get(id));
                    } else {
//                        fragment = WendaContentView.newInstance();
                        fragment = NewsContentView.newInstance();

                        fragments.add(fragment);
                    }
                    break;

                    // 其他fragment的条目样式是一致的
                default:
                    if (map.containsKey(id)) {
                        fragments.add(map.get(id));
                    } else {
                        fragment = NewsContentView.newInstance();
                        fragments.add(fragment);
                    }
            }

            if (fragment != null) {
                map.put(id, fragment);
            }

        }

    }

    public static HomeTabView newInstance() {
        return new HomeTabView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.add)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), NewsChannelActivity.class));
    }
}
