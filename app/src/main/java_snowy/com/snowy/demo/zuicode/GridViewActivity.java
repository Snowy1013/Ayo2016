package com.snowy.demo.zuicode;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zx on 16-8-30.
 */
public class GridViewActivity extends BaseActivity {

    private GridView gv_small_images;
    private ImageView iv_large_image;
    private int[] imageIds = new int[]{
            R.drawable.snowy_cute1, R.drawable.snowy_cute2, R.drawable.snowy_cute3, R.drawable.snowy_cute4,
            R.drawable.snowy_cute5, R.drawable.snowy_cute6, R.drawable.snowy_cute7, R.drawable.snowy_cute8,
            R.drawable.snowy_cute9, R.drawable.snowy_cute10, R.drawable.snowy_cute11, R.drawable.snowy_cute12,
            R.drawable.snowy_cute13, R.drawable.snowy_cute14, R.drawable.snowy_cute15, R.drawable.snowy_cute16
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_uicode_gridview);

        gv_small_images = findViewById(R.id.gv_small_images);
        iv_large_image = findViewById(R.id.iv_large_image);

        List<Map<String, Object>> listItems = new ArrayList<>();
        for(int i=0; i<imageIds.length; i++){
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("image", imageIds[i]);
            listItems.add(listItem);
        }

        //创建一个SimpleAdater
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), listItems,
                R.layout.item_gridview, new String[] {"image"}, new int[] {R.id.item_image});
        gv_small_images.setAdapter(adapter);

        gv_small_images.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //显示当前被选中的图片
                iv_large_image.setImageResource(imageIds[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gv_small_images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //显示被单击的图片
                iv_large_image.setImageResource(imageIds[i]);
            }
        });
    }
}
