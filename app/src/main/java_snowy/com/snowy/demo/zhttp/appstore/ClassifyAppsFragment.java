package com.snowy.demo.zhttp.appstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cowthan.sample.R;

/**
 * Created by zx on 16-12-22.
 */

public class ClassifyAppsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_commun_one, container, false);
        return view;
    }
}
