package com.rain.mytoutiao.fragment;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rain.mytoutiao.R;
import com.rain.mytoutiao.base.BaseListFragment;
import com.rain.mytoutiao.base.LazyLoadFragment;

/**
 * Author:rain
 * Date:2018/5/15 15:19
 * Description:
 */
public class JokeContentView extends BaseListFragment {
    @Override
    public void fetchData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    public static JokeContentView newInstance() {
        return new JokeContentView();
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        return null;

    }
}
