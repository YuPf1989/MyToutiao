package com.rain.mytoutiao.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rain.mytoutiao.R;
import com.rain.mytoutiao.base.AbsBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:rain
 * Date:2018/5/21 10:59
 * Description:
 */
public class SearchActivity extends AbsBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SearchView searchView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initToolBar(toolbar,true,"搜索");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        // 关联检索配置与SearchActivity
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), SearchActivity.class));
        searchView.setSearchableInfo(searchableInfo);
        searchView.onActionViewExpanded();
        return super.onCreateOptionsMenu(menu);
    }

}
