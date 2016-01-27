package com.cowthan.sample;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cowthan.sample.menu.Leaf;
import com.cowthan.sample.menu.MenuItem;

import org.ayo.app.base.ActivityAttacher;

public class MenuItemFragment extends SBFragment {

	private MenuItem menuItem;

	public void setMenu(MenuItem menuItem){
		this.menuItem = menuItem;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.frag_menu_item;
	}

	@Override
	public void onCreateView(View root) {
		ll_root = (LinearLayout) findViewById(R.id.ll_root);
		for(Leaf leaf: menuItem.subMenus){
			addButton(leaf);
		}
	}

	private LinearLayout ll_root;


	private void addButton(final Leaf leaf){
		Button btn = new Button(agent.getActivity());
		btn.setText(leaf.name);
		btn.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		btn.setBackgroundResource(R.drawable.sel_menu2);
		btn.setTextSize(15);
		btn.setPadding(20, 0, 20, 0);
		btn.setTextColor(Color.WHITE);


		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(agent.getActivity(), 40));
		lp.gravity = Gravity.CENTER;
		lp.topMargin = Utils.dip2px(agent.getActivity(), 5);
		lp.bottomMargin = Utils.dip2px(agent.getActivity(), 5);
		lp.leftMargin = Utils.dip2px(agent.getActivity(), 5);
		lp.rightMargin = Utils.dip2px(agent.getActivity(), 5);
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
