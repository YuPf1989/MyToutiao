package com.rain.mytoutiao.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rain.mytoutiao.R;
import com.rain.mytoutiao.adapter.ChannelAdapter;
import com.rain.mytoutiao.adapter.MySimpleAdapter;
import com.rain.mytoutiao.base.AbsBaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:rain
 * Date:2018/5/15 16:44
 * Description:
 */
public class NewsChannelActivity extends AbsBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_my_channel)
    RecyclerView recycler_my_channel;
    @BindView(R.id.recycler_hide_channel)
    RecyclerView recycler_hide_channel;

    private static final List my_channel =  Arrays.asList("item1", "item2", "item3", "item4","item1", "item2", "item3", "item4");
    private static final List hide_channel =  Arrays.asList("item5", "item6", "item7", "item8","item5", "item6", "item7", "item8");
    private ChannelAdapter my_channel_adapter;
    private ChannelAdapter hide_channel_adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_channel;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initToolBar(toolbar, true, "拖拽排序");
        initRecycler();
    }

    private void initRecycler() {
        recycler_my_channel.setLayoutManager(new GridLayoutManager(this, 4));
        recycler_hide_channel.setLayoutManager(new GridLayoutManager(this, 4));
        my_channel_adapter = new ChannelAdapter(R.layout.layout_channel_item, my_channel);
        hide_channel_adapter = new ChannelAdapter(R.layout.layout_channel_item, hide_channel);

        recycler_my_channel.setAdapter(my_channel_adapter);
        recycler_hide_channel.setAdapter(hide_channel_adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
