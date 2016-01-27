package com.ayo.opensource.zpartial_explode;

import android.os.Bundle;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;


public class PartialExplodeMainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partial_explode_ac_main_az);

        ExplosionField explosionField = new ExplosionField(getActivity());

        explosionField.addListener(findViewById(R.id.root));
    }


}
