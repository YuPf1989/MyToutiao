package com.rain.mytoutiao.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:rain
 * Time:2017/6/15 17:44
 * Description:This is BaseFragment
 */

public abstract class BaseFragment extends RxFragment implements IBaseView{

    protected View rootView;
    protected Context mContext;
    private Unbinder unbinder;
    // 初始化是否完成
    protected boolean isViewInitiated;
    // 用户是否可见
    protected boolean isVisibleToUser;
    // 是否失去焦点
    protected boolean isOnPause;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
    }

    @Override
    public void onPause() {
        super.onPause();
        isOnPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isOnPause = false;
    }

    /**
     * 当前fragment是否对用户可见
     * 不考虑数据是否加载过
     *
     * @return
     */
    public boolean isVisibleToUser() {
        if (!isOnPause && isVisibleToUser && isViewInitiated) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(getLayoutId(), container, false);

        mContext = getContext();

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
    }

    @Override
    public LifecycleTransformer bindToLife() {
        return bindUntilEvent(FragmentEvent.DESTROY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        isVisibleToUser = false;
        isViewInitiated = false;
    }

    /**
     * 初始化 Toolbar
     * 注意这里的toolbar在activity中
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((AbsBaseActivity) getActivity()).initToolBar(toolbar, homeAsUpEnabled, title);
    }

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

}
