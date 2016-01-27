package com.ayoview.sample.zbutton;

import android.os.Bundle;
import android.util.Log;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by Administrator on 2016/1/25.
 */
public class DemoCheckBox1Activity extends BaseActivity implements AnimCheckBox.OnCheckedChangeListener {
    private AnimCheckBox mAnimCheckBox1, mAnimCheckBox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_anim_checkbox);
        mAnimCheckBox1 = (AnimCheckBox) findViewById(R.id.checkbox_1);
        mAnimCheckBox1.setChecked(false, false);
        mAnimCheckBox2 = (AnimCheckBox) findViewById(R.id.checkbox_2);
        mAnimCheckBox2.setChecked(false, false);
        mAnimCheckBox1.setOnCheckedChangeListener(this);
        mAnimCheckBox2.setOnCheckedChangeListener(this);
    }

    @Override
    public void onChange(boolean checked) {
        Log.d("MainActivity", "checked-->" + checked);
    }
}
