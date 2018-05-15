package com.rain.mytoutiao.fragment;

import android.os.Bundle;

import com.rain.mytoutiao.R;
import com.rain.mytoutiao.base.LazyLoadFragment;

/**
 * Author:rain
 * Date:2018/5/15 15:19
 * Description:
 */
public class JokeContentView extends LazyLoadFragment {
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
}
