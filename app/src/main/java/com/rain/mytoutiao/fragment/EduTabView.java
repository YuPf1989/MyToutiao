package com.rain.mytoutiao.fragment;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rain.mytoutiao.R;
import com.rain.mytoutiao.base.BaseListFragment;
import com.rain.mytoutiao.base.LazyLoadFragment;

/**
 * Author:rain
 * Date:2018/5/15 9:33
 * Description:
 */
public class EduTabView extends BaseListFragment {
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

    public static EduTabView newInstance() {
        return new EduTabView();
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        return null;
    }
}
