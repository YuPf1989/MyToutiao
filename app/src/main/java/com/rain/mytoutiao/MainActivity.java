package com.rain.mytoutiao;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.rain.mytoutiao.base.AbsBaseActivity;
import com.rain.mytoutiao.fragment.EduTabView;
import com.rain.mytoutiao.fragment.HomeTabView;
import com.rain.mytoutiao.fragment.MyTabView;
import com.rain.mytoutiao.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private HomeTabView homeTabView;
    private EduTabView eduTabView;
    private MyTabView myTabView;
    private long firstClickTime = 0;

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
        initToolBar(toolbar,false,getResources().getString(R.string.app_name));
        initBottomNavigation();
        initListener();
        showFragment(HOME_TAB);
    }

    private void showFragment(int index) {
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
        bottomNavigation.enableAnimation(true);
        bottomNavigation.enableShiftingMode(false);
        bottomNavigation.enableItemShiftingMode(false);
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        ToastUtil.showToast("item.id:" + item.getItemId());

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
