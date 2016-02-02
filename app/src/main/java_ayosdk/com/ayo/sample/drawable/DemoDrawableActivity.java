package com.ayo.sample.drawable;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;
import com.cowthan.sample.Utils;
import com.cowthan.sample.menu.Leaf;
import com.cowthan.sample.menu.MenuItem;

import org.ayo.app.base.ActivityAttacher;

/**
 * Created by Administrator on 2016/1/29.
 */
public class DemoDrawableActivity extends BaseActivity{
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_tmpl_menu_list);

        menuItem = new MenuItem("drwable列表", 0, 0);
        {
            menuItem.addLeaf(new Leaf("BitmapDrawable", "", D_BitmapDrawable.class));
            menuItem.addLeaf(new Leaf("NinePatchDrawable", "", null));
            menuItem.addLeaf(new Leaf("LayerDrawable", "", D_LayerDrawable.class));
            menuItem.addLeaf(new Leaf("StateListDrawable", "", D_StateListDrawable.class));
            menuItem.addLeaf(new Leaf("LevelDrawable", "", D_LevelDrawable.class));
            menuItem.addLeaf(new Leaf("TransitionDrawable", "", D_TransitionDrawable.class));
            menuItem.addLeaf(new Leaf("InsetDrawable", "", D_InsetDrawable.class));
            menuItem.addLeaf(new Leaf("ClipDrawable", "", D_ClipDrawable.class));
            menuItem.addLeaf(new Leaf("ScaleDrawable", "", D_ScaleDrawable.class));
            menuItem.addLeaf(new Leaf("RotateDrawable", "", D_RotateDrawable.class));
            menuItem.addLeaf(new Leaf("GradientDrawable", "", D_GradientDrawable.class));
        }

        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        for(Leaf leaf: menuItem.subMenus){
            addButton(leaf);
        }
    }

    private LinearLayout ll_root;


    private void addButton(final Leaf leaf){
        Button btn = new Button(getActivity());
        btn.setText(leaf.name);
        btn.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        btn.setBackgroundResource(R.drawable.sel_menu2);
        btn.setTextSize(15);
        btn.setPadding(20, 0, 20, 0);
        btn.setTextColor(Color.WHITE);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(getActivity(), 40));
        lp.gravity = Gravity.CENTER;
        lp.topMargin = Utils.dip2px(getActivity(), 5);
        lp.bottomMargin = Utils.dip2px(getActivity(), 5);
        lp.leftMargin = Utils.dip2px(getActivity(), 5);
        lp.rightMargin = Utils.dip2px(getActivity(), 5);
        ll_root.addView(btn, lp);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(leaf.attacherClass == null){
                    Toast.makeText(getActivity(), "成功扣款10元，慢走不送", Toast.LENGTH_SHORT).show();
                }else{
                    ActivityAttacher.startActivity(getActivity(), leaf.attacherClass);
                }
            }
        });

    }
}
