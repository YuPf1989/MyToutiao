package com.rain.mytoutiao.mvp.base;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by Meiji on 2017/7/5.
 */

public interface IBaseListView extends IBaseView {

    /**
     * 显示加载动画
     */
    void onShowLoading();

    /**
     * 隐藏加载
     */
    void onHideLoading();

    /**
     * 显示网络错误
     */
    void onShowNetError();

    /**
     * 绑定生命周期
     */
    LifecycleTransformer bindToLife();

    /**
     * 设置适配器数据
     */
    void onSetAdapterData(List<?> list);

    /**
     * 设置适配器
     */
    BaseQuickAdapter setAdapter();

    /**
     * 加载完毕
     */
    void onShowNoMore();
}
