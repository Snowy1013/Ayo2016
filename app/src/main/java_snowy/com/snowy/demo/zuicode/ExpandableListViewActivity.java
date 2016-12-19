package com.snowy.demo.zuicode;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by zx on 16-9-2.
 */
public class ExpandableListViewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_uicode_expandablelistview);

        //创建一个BaseExpandableListAdapter的对象
        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

            int[] logos = new int[]{
                    R.drawable.gold,
                    R.drawable.chong,
                    R.drawable.man
            };
            String[] armTypes = new String[] {
                    "神族兵种", "虫族兵种", "人族兵种"
            };
            String[][] arms = new String[][] {
                    {"狂战士", "龙骑士", "黑暗圣堂", "电兵"},
                    {"小狗", "刺蛇", "飞龙", "自爆飞机"},
                    {"机枪兵", "护士MM", "幽灵"}
            };
            @Override
            public int getGroupCount() {
                return armTypes.length;
            }

            @Override
            public int getChildrenCount(int i) {
                return arms[i].length;
            }

            //获取指定组位置处的组数据
            @Override
            public Object getGroup(int i) {
                return armTypes[i];
            }

            //获取指定组位置，指定子列表项数据
            @Override
            public Object getChild(int i, int i1) {
                return arms[i][i1];
            }

            @Override
            public long getGroupId(int i) {
                return i;
            }

            @Override
            public long getChildId(int i, int i1) {
                return i1;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            private TextView getTextView(int left, int top, int right, int bottom){
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                textView.setPadding(left, top, right, bottom);
                textView.setTextSize(20);
                return textView;
            }

            //该方法决定每个组选项的外观
            @Override
            public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(300, 300);
                LinearLayout ll = new LinearLayout(getActivity());
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ImageView logo = new ImageView(getActivity());
                logo.setLayoutParams(lp);
                logo.setImageResource(logos[i]);
                ll.addView(logo);
                TextView textView = getTextView(36, 0, 0, 0);
                textView.setText(getGroup(i).toString());
                ll.addView(textView);
                return ll;
            }

            //该方法决定每个子选项的外观
            @Override
            public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 150);
                LinearLayout ll = new LinearLayout(getActivity());
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ImageView logo = new ImageView(getActivity());
                logo.setLayoutParams(lp);
                logo.setImageResource(logos[i]);
                ll.addView(logo);
                TextView textView = getTextView(36, 0, 0, 0);
                textView.setText(getChild(i, i1).toString());
                ll.addView(textView);
                return ll;
            }

            @Override
            public boolean isChildSelectable(int i, int i1) {
                return true;
            }
        };

        ExpandableListView elv_list = (ExpandableListView) findViewById(R.id.elv_list);
        elv_list.setAdapter(adapter);
    }
}
