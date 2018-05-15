package com.rain.mytoutiao.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rain.mytoutiao.R;

import java.util.List;

/**
 * Author:rain
 * Date:2018/5/11 17:28
 * Description:
 */
public class MySimpleAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public MySimpleAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv, item);
    }
}
