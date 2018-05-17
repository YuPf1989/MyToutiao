package com.rain.mytoutiao.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rain.mytoutiao.R;
import com.rain.mytoutiao.db.ChannelDao;

import java.util.List;

/**
 * Author:rain
 * Date:2018/5/16 9:42
 * Description:
 * channel adapter
 */
public class ChannelAdapter extends BaseItemDraggableAdapter<ChannelDao,BaseViewHolder> {
    public ChannelAdapter(int layoutResId, @Nullable List<ChannelDao> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelDao item) {
        View view = helper.getView(R.id.iv_close);
        // 是否处于我的频道
        if (item.is_enable) {
            // 是否处于编辑状态
            if (item.enableEdit) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
        helper.setText(R.id.tv_channel, item.name);
    }
}
