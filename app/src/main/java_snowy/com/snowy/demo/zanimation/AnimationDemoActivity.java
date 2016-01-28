package com.snowy.demo.zanimation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by snowy on 16/1/27.
 */
public class AnimationDemoActivity extends BaseActivity implements View.OnClickListener{

    private Button btn_animat_tween;
    private Button btn_animat_frame;
    private Button btn_animat_property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_animation_demo);

        btn_animat_frame = findViewById(R.id.btn_animat_frame);
        btn_animat_tween = findViewById(R.id.btn_animat_tween);
        btn_animat_property = findViewById(R.id.btn_animat_property);

        btn_animat_property.setOnClickListener(this);
        btn_animat_tween.setOnClickListener(this);
        btn_animat_frame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        Intent intent = null;
        switch (viewId) {
            case R.id.btn_animat_frame: //帧动画
                intent = new Intent(getActivity(), FrameAnimateActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.btn_animat_tween: //补间动画
                intent = new Intent(getActivity(), TweenAnimateActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.btn_animat_property: //属性动画
                intent = new Intent(getActivity(), PropertyAnimateActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
