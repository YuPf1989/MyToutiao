package com.rain.mytoutiao.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.rain.mytoutiao.R;
import com.rain.mytoutiao.mvp.ui.activity.SearchActivity;
import com.rain.mytoutiao.mvp.ui.activity.SettingActivity;
import com.rain.mytoutiao.mvp.ui.activity.SettingsActivity;
import com.rain.mytoutiao.mvp.base.AbsBaseActivity;
import com.rain.mytoutiao.mvp.ui.fragment.EduTabView;
import com.rain.mytoutiao.mvp.ui.fragment.HomeTabView;
import com.rain.mytoutiao.mvp.ui.fragment.MyTabView;
import com.rain.mytoutiao.mvp.util.ToastUtil;

import butterknife.BindView;

public class MainActivity extends AbsBaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationViewEx bottomNavigation;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private static final int HOME_TAB = 0;
    private static final int EDU_TAB = 1;
    private static final int MY_TAB = 2;
    private int position = 0;
    private static final String POSITION = "position";
    private static final String SELECT_ITEM = "navigation_select_item";

    private HomeTabView homeTabView;
    private EduTabView eduTabView;
    private MyTabView myTabView;
    private long firstClickTime = 0;
    private long existTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 不使用沉浸式
    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initToolBar(toolbar, false, getResources().getString(R.string.app_name));
        initBottomNavigation();
        initListener();
        if (savedInstanceState != null) {
            homeTabView = (HomeTabView) getSupportFragmentManager().findFragmentByTag(HomeTabView.class.getSimpleName());
            eduTabView = (EduTabView) getSupportFragmentManager().findFragmentByTag(EduTabView.class.getSimpleName());
            myTabView = (MyTabView) getSupportFragmentManager().findFragmentByTag(MyTabView.class.getSimpleName());
            showFragment(savedInstanceState.getInt(POSITION));
            bottomNavigation.setSelectedItemId(savedInstanceState.getInt(SELECT_ITEM));
        } else {
            showFragment(HOME_TAB);
        }
    }

    // 主要是为了处理屏幕旋转或者程序异常恢复时候用
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(POSITION, position);
        outState.putInt(SELECT_ITEM, bottomNavigation.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    private void showFragment(int index) {
        position = index;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        switch (index) {
            case HOME_TAB:
                toolbar.setTitle("新闻");
                if (homeTabView == null) {
                    homeTabView = HomeTabView.newInstance();
                    ft.add(R.id.container, homeTabView, HomeTabView.class.getSimpleName());
                } else {
                    ft.show(homeTabView);
                }
                break;

            case EDU_TAB:
                toolbar.setTitle("图片");
                if (eduTabView == null) {
                    eduTabView = EduTabView.newInstance();
                    ft.add(R.id.container, eduTabView, EduTabView.class.getSimpleName());
                } else {
                    ft.show(eduTabView);
                }
                break;

            case MY_TAB:
                toolbar.setTitle("视频");
                if (myTabView == null) {
                    myTabView = MyTabView.newInstance();
                    ft.add(R.id.container, myTabView, MyTabView.class.getSimpleName());
                } else {
                    ft.show(myTabView);
                }
                break;
        }
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        if (homeTabView != null) {
            ft.hide(homeTabView);
        }

        if (eduTabView != null) {
            ft.hide(eduTabView);
        }

        if (myTabView != null) {
            ft.hide(myTabView);
        }
    }

    private void initBottomNavigation() {
        bottomNavigation.enableShiftingMode(false);
    }

    private void initListener() {
        navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_build:
                        showFragment(HOME_TAB);
                        doubleClick(HOME_TAB);
                        break;
                    case R.id.action_check:
                        showFragment(EDU_TAB);
                        doubleClick(EDU_TAB);
                        break;
                    case R.id.action_my:
                        showFragment(MY_TAB);
                        doubleClick(MY_TAB);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - existTime > 2000) {
            ToastUtil.showToast("再次点击退出程序！");
            existTime = currentTime;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            startActivity(new Intent(this,SearchActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 返回值true、false区别在于当前的条目是否有选中的标识
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            // 设置
            startActivity(new Intent(this, SettingActivity.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void doubleClick(int index) {
        long secondClickTime = System.currentTimeMillis();
        if ((secondClickTime - firstClickTime < 500)) {
            switch (index) {
                case HOME_TAB:
                    homeTabView.onDoubleClick();
                    break;
                case EDU_TAB:
                    homeTabView.onDoubleClick();
                    break;
                case MY_TAB:
                    homeTabView.onDoubleClick();
                    break;
            }
        } else {
            firstClickTime = secondClickTime;
        }
    }


}
