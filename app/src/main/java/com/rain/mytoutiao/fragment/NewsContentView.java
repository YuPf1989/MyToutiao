package com.rain.mytoutiao.fragment;

import android.os.Handler;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rain.mytoutiao.R;
import com.rain.mytoutiao.adapter.MySimpleAdapter;
import com.rain.mytoutiao.base.BaseListFragment;

import java.util.ArrayList;

/**
 * Author:rain
 * Date:2018/5/15 15:19
 * Description:
 */
public class NewsContentView extends BaseListFragment {
    private ArrayList data = new ArrayList();

    // 模拟联网请求
    @Override
    public void fetchData() {
        super.fetchData();
        refresh();
    }

    private void refresh() {
        initData();
        onShowLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onHideLoading();
                onSetAdapterData(data);
            }
        }, 2000);
    }

    // 这是模拟数据
    private void initData() {
        data.clear();
        for (int i = 0; i < 20; i++) {
            data.add("第" + i + "个条目");
        }
    }

    public static NewsContentView newInstance() {
        return new NewsContentView();
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        return new MySimpleAdapter(R.layout.list_item, null);
    }
}
