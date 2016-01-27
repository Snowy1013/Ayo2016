package com.snowy.demo.zfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.app.base.ActivityAttacher;


/**
 * Created by Snowy on 16/1/20.
 */
public class FragmentDemoActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_fragment_demo);
        Button btn_fragment_static = findViewById(R.id.btn_fragment_static);
        Button btn_fragment_dynamic = findViewById(R.id.btn_fragment_dynamic);
        Button btn_fragment_communicate = findViewById(R.id.btn_fragment_communicate);
        btn_fragment_static.setOnClickListener(this);
        btn_fragment_dynamic.setOnClickListener(this);
        btn_fragment_communicate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fragment_static:
                ActivityAttacher.startActivity(getActivity(), FragmentStaticActivity.class);
                break;
            case R.id.btn_fragment_dynamic:
                ActivityAttacher.startActivity(getActivity(), FragmentDynamicActivity.class);
                break;
            case R.id.btn_fragment_communicate:
//              ActivityAttacher.startActivity(getActivity(), FragmentCommunActivity.class);
                Intent intent = new Intent(getActivity(), FragmentCommunActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
