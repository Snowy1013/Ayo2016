package com.snowy.demo.zanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.cowthan.sample.R;

/**
 * Created by snowy on 16/1/27.
 */
public class TweenAnimateActivity extends Activity implements View.OnClickListener{
    private Button btn_alpha_animate;
    private Button btn_scale_animate;
    private Button btn_translate_animate;
    private Button btn_rotate_animate;
    private Button btn_tweenmix_animate;
    private ImageView img_animat_robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_tween_animate);
        setTitle("补间动画（Tween）");

        initView();
    }

    private void initView() {
        btn_alpha_animate = (Button) findViewById(R.id.btn_alpha_animate);
        btn_scale_animate = (Button) findViewById(R.id.btn_scale_animate);
        btn_translate_animate = (Button) findViewById(R.id.btn_translate_animate);
        btn_rotate_animate = (Button) findViewById(R.id.btn_rotate_animate);
        btn_tweenmix_animate = (Button) findViewById(R.id.btn_tweenmix_animate);

        img_animat_robot = (ImageView) findViewById(R.id.img_animat_robot);
        //为按钮设置点击事件
        btn_alpha_animate.setOnClickListener(this);
        btn_translate_animate.setOnClickListener(this);
        btn_rotate_animate.setOnClickListener(this);
        btn_scale_animate.setOnClickListener(this);
        btn_tweenmix_animate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_alpha_animate: //透明度变化
                //方法一通过代码的方式定义透明度变化
                /*Animation alphaAnimation1 = new AlphaAnimation(1, (float)0.1);
                alphaAnimation1.setDuration(3000);//设置动画持续时间为3秒
                alphaAnimation1.setFillAfter(true);//设置动画结束后保持当前的位置
                img_animat_robot.startAnimation(alphaAnimation1);*/

                //方法二t通过在xml文件中定义透明度动画，代码中加载xml文件b并将其设定到指定的view上
                Animation alphaAnimation2 = AnimationUtils.loadAnimation(this, R.anim.alpha_animate);
                img_animat_robot.startAnimation(alphaAnimation2);
                break;
            case R.id.btn_scale_animate://尺寸缩放
                //方法一通过代码的方式定义缩放动画
                /**
                 * @param 四个参数分别为
                 * fromXScale（浮点型）属性为动画起始时X坐标上的缩放尺寸
                 * fromYScale（浮点型）属性为动画起始时Y坐标上的缩放尺寸
                 * toXScale（浮点型） 属性为动画结束时X坐标上的缩放尺寸
                 * toYScale（浮点型） 属性为动画结束时Y坐标上的缩放尺寸
                 */
                /*Animation scaleAnimation1 = new ScaleAnimation(0.4f, 1.0f, 0.4f, 1.0f);
                scaleAnimation1.setDuration(3000);
                scaleAnimation1.setFillAfter(true);
                scaleAnimation1.setRepeatCount(2);
                img_animat_robot.startAnimation(scaleAnimation1);*/

                //方法二在xml中定义
                Animation scaleAnimation2 = AnimationUtils.loadAnimation(this, R.anim.scale_animate);
                img_animat_robot.startAnimation(scaleAnimation2);

                break;
            case R.id.btn_translate_animate://位置移动
                //方法一通过代码的方式定义位置移动
                /*Animation translateAnimation1 = new TranslateAnimation(0,100,0,0);
                translateAnimation1.setDuration(3000);
                //translateAnimation1.setInterpolator(this, R.anim.); // 设置动画插入器，暂时没有
                translateAnimation1.setFillAfter(true);
                img_animat_robot.startAnimation(translateAnimation1);*/

                //方法二在xml中定义
                Animation tranlateAnimation2 = AnimationUtils.loadAnimation(this, R.anim.translate_animate);
                img_animat_robot.startAnimation(tranlateAnimation2);

                break;
            case R.id.btn_rotate_animate://旋转
                //方法一通过代码方式定义旋转动画
                /*Animation rotateAnimation1 = new RotateAnimation(0,45);
                rotateAnimation1.setDuration(3000);
                rotateAnimation1.setFillAfter(true);
                img_animat_robot.startAnimation(rotateAnimation1);*/

                //方法二在xml中定义
                Animation rotateAnimation2 = AnimationUtils.loadAnimation(this, R.anim.rotate_animate);
                img_animat_robot.startAnimation(rotateAnimation2);

                break;
            case R.id.btn_tweenmix_animate://混合效果
                //方式一通过代码的方式定义动画集，定义动画集主要用到了AnimationSet类，该类可以添加多个补间动画
                AnimationSet animationSet = new AnimationSet(true);//定义一个动画集，并设定所有的动画使用一个动画插入其中
                Animation alphaAnimation3 = AnimationUtils.loadAnimation(this, R.anim.alpha_animate);
                animationSet.addAnimation(alphaAnimation3);
                Animation scaleAnimation3 = AnimationUtils.loadAnimation(this, R.anim.scale_animate);
                animationSet.addAnimation(scaleAnimation3);
                Animation tranlateAnimation3 = AnimationUtils.loadAnimation(this, R.anim.translate_animate);
                animationSet.addAnimation(tranlateAnimation3);
                Animation rotateAnimation3 = AnimationUtils.loadAnimation(this, R.anim.rotate_animate);
                animationSet.addAnimation(rotateAnimation3);
                img_animat_robot.startAnimation(animationSet);

                break;
        }
    }
}
