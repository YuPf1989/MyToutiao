package com.rain.mytoutiao.fragment;

import android.os.Bundle;

import com.rain.mytoutiao.R;
import com.rain.mytoutiao.base.LazyLoadFragment;

/**
 * Author:rain
 * Date:2018/5/15 9:33
 * Description:
 */
public class MyTabView extends LazyLoadFragment {
    @Override
    public void fetchData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_tab;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    public static MyTabView newInstance() {
        return new MyTabView();
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onShowNetError() {

    }
}
