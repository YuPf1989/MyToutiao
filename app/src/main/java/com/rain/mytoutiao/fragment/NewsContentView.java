package com.rain.mytoutiao.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rain.mytoutiao.R;
import com.rain.mytoutiao.adapter.MySimpleAdapter;
import com.rain.mytoutiao.base.LazyLoadFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:rain
 * Date:2018/5/15 15:19
 * Description:
 */
public class NewsContentView extends LazyLoadFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recycler;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe;
    Unbinder unbinder;

    private ArrayList data = new ArrayList();
    private MySimpleAdapter adapter;


    // 模拟联网请求
    @Override
    public void fetchData() {
        refresh();
    }

    private void refresh() {
        adapter.setNewData(null);
        if (swipe.isRefreshing()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipe.setRefreshing(false);
                    adapter.setNewData(data);
                }
            }, 2000);
            return;
        }
        swipe.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(false);
                adapter.setNewData(data);
            }
        }, 2000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initData();
        initRecycler();
    }

    private void initRecycler() {
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setHasFixedSize(true);
        adapter = new MySimpleAdapter(R.layout.list_item, null);
        recycler.setAdapter(adapter);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    // 这是模拟数据
    private void initData() {
        data.clear();
        for (int i = 0; i < 20; i++) {
            data.add("第" + i + "个条目");
        }
    }

    public static NewsContentView newInstance() {
        return new NewsContentView();
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
    }
}
