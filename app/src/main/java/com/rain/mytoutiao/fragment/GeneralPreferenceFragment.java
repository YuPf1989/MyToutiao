package com.rain.mytoutiao.fragment;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.rain.mytoutiao.R;
import com.rain.mytoutiao.util.CacheDataManager;

/**
 * Author:rain
 * Date:2018/5/18 15:58
 * Description:
 * 专门用于设置界面
 * 与sp是相互关联的
 */
public class GeneralPreferenceFragment extends PreferenceFragment {

    private Activity context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        addPreferencesFromResource(R.xml.pref_general_fragment);
        getAppCacheSize();
        setVersion();
        initListener();
        initData();
    }

    private void initData() {
        bindPreferenceSummaryToValue(findPreference("slidable"));
        bindPreferenceSummaryToValue(findPreference("account"));
    }

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                preference.setSummary(
                        index >= 0 ? listPreference.getEntries()[index] : null);
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    private void initListener() {
        findPreference("clearCache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                CacheDataManager.clearAllCache(context);
                Snackbar.make(getView(), "清除成功", Snackbar.LENGTH_SHORT).show();
                getAppCacheSize();
                return false;
            }
        });
    }

    private void setVersion() {
        try {
            String version = "version:" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            findPreference("version").setSummary(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getAppCacheSize() {
        try {
            findPreference("clearCache").setSummary(CacheDataManager.getTotalCacheSize(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GeneralPreferenceFragment newInstance() {
        return new GeneralPreferenceFragment();
    }

    // 设置监听，并立即触发显示当前的summary
    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
        // 立即触发回调，显示summary
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }
}
