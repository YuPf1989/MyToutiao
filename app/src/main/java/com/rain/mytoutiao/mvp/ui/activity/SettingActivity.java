package com.rain.mytoutiao.mvp.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.rain.mytoutiao.R;
import com.rain.mytoutiao.mvp.base.AbsBaseActivity;
import com.rain.mytoutiao.mvp.ui.fragment.GeneralPreferenceFragment;

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

    public static final String EXTRA_SHOW_FRAGMENT = "show_fragment";
    public static final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = "show_fragment_args";
    public static final String EXTRA_SHOW_FRAGMENT_TITLE = "show_fragment_title";

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        String initFragment = getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT);
        Bundle initArguments = getIntent().getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS);
        String initTitle = getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT_TITLE);

        if (TextUtils.isEmpty(initFragment)) {
            setupFragment(GeneralPreferenceFragment.class.getName(), initArguments);
        } else {
            setupFragment(initFragment, initArguments);
        }
        initToolBar(toolbar, true, TextUtils.isEmpty(initTitle) ? "设置" : initTitle);
    }

    private void setupFragment(String fragmentName, Bundle args) {
        Fragment fragment = Fragment.instantiate(this, fragmentName, args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.content, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public Intent onBuildStartFragmentIntent(String fragmentName, Bundle args, String title) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(this, getClass());
        intent.putExtra(EXTRA_SHOW_FRAGMENT, fragmentName);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, args);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, title);
        return intent;
    }

    public void startWithFragment(String fragmentName, Bundle args,
                                  Fragment resultTo, int resultRequestCode, String title) {
        Intent intent = onBuildStartFragmentIntent(fragmentName, args, title);
        if (resultTo == null) {
            startActivity(intent);
        } else {
            resultTo.startActivityForResult(intent, resultRequestCode);
        }
    }

}
