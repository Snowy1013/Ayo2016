package com.cowthan.sample.zfragment;

import android.os.Bundle;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.app.base.ActivityAttacher;


/**
 * Created by Snowy on 16/1/20.
 */
public class FragmentStaticActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ac_fragment_static);
    }
}
