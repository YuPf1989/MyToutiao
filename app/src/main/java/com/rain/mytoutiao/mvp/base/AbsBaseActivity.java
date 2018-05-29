package com.rain.mytoutiao.mvp.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.rain.mytoutiao.mvp.MyApplication;
import com.rain.mytoutiao.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:rain
 * Time:2017/6/14 10:13
 * Description:This is AbsBaseActivity
 */

public abstract class AbsBaseActivity extends RxAppCompatActivity {

    private static final String TAG  = AbsBaseActivity.class.getSimpleName();
    private Unbinder unbinder;
    private ImmersionBar immersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设制竖屏

        setContentView(getLayoutId());

        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();

        unbinder = ButterKnife.bind(this);

        initViews(savedInstanceState);

    }

    private void initImmersionBar() {
        immersionBar = ImmersionBar.with(this);// 沉浸式状态栏设置
        immersionBar.fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
//                .navigationBarColor(R.color.colorPrimary)
                .keyboardEnable(true)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (immersionBar != null) {
            immersionBar.destroy();
        }
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }


    public void showToast(final String msg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

}
