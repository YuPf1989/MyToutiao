package com.rain.mytoutiao.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.rain.mytoutiao.R;
import com.rain.mytoutiao.base.AbsBaseActivity;
import com.rain.mytoutiao.fragment.GeneralPreferenceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:rain
 * Date:2018/5/18 11:44
 * Description:
 */
public class SettingActivity extends AbsBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initToolBar(toolbar,true,"设置");
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, GeneralPreferenceFragment.newInstance()).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
