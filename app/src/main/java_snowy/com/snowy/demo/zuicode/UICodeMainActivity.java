package com.snowy.demo.zuicode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.app.base.ActivityAttacher;

/**
 * Created by zx on 16-9-5.
 */
public class UICodeMainActivity extends BaseActivity {

    ListView lv_main;
    String TAG = "UICodeMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_uicode_main);
        String[] itemTitles = {"跟随手指的小球", "霓虹灯效果", "时钟", "图片浏览器",
                "瀑布流", "带预览的图片浏览器", "可展开的列表组件", "幻灯片式图片浏览器", "Android 学习",
                "Android 学习", "Android 学习", "Android 学习", "Android 学习", "Android 学习",
                "Android 学习", "Android 学习", "Android 学习", "Android 学习", "Android 学习"};
        final Class[] classes = {DrawViewActivity.class, FrameLayoutActivity.class,
                ClockActivity.class, ImageViewActivity.class, StreamLayoutActivity.class,
                GridViewActivity.class, ExpandableListViewActivity.class
        };

        lv_main = findViewById(R.id.lv_main);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item_list_main, itemTitles);
        lv_main.setAdapter(adapter);
        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    ActivityAttacher.startActivity(getActivity(), classes[position]);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
