package com.rain.mytoutiao.mvp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rain.mytoutiao.R;

/**
 * Author:rain
 * Date:2018/5/21 17:08
 * Description:
 */
public class TextSizeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_text_size, null);
        return inflate;
    }
}
