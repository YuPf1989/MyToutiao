package com.rain.mytoutiao.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.rain.mytoutiao.R;

/**
 * Author:rain
 * Date:2018/5/18 15:58
 * Description:
 */
public class GeneralPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general_fragment);
    }

    public static GeneralPreferenceFragment newInstance() {
        return new GeneralPreferenceFragment();
    }
}
