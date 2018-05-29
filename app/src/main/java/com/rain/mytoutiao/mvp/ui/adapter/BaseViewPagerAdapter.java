package com.rain.mytoutiao.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Author:rain
 * Date:2018/5/15 15:32
 * Description:
 */
public class BaseViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<String> tabtitles;
    public BaseViewPagerAdapter(FragmentManager fm, List<Fragment> fragments,List<String> tabtitles) {
        super(fm);
        this.fragments = fragments;
        this.tabtitles = tabtitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles.get(position);
    }

    // 重新填充数据
    public void recreatItems(List<Fragment> fragments,List<String> tabtitles) {
        this.fragments = fragments;
        this.tabtitles = tabtitles;
        notifyDataSetChanged();
    }
}
