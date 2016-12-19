package com.snowy.demo.zdesignpattern;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;
import com.snowy.demo.zdesignpattern.builderpattern.BuilderPatternActivity;

import org.ayo.app.base.ActivityAttacher;

/**
 * Created by zx on 16-10-9.
 */
public class DesignPatternActivity extends BaseActivity {

    ListView lv_design_pattern;
    String TAG = "DesignPatternActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_design_pattern_demo);
        lv_design_pattern = findViewById(R.id.lv_design_pattern);

        String[] itemTitles = new String[] {"建造模式"};
        final Class[] classes = new Class[] {BuilderPatternActivity.class};

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item_list_main, itemTitles);
        lv_design_pattern.setAdapter(adapter);
        lv_design_pattern.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ActivityAttacher.startActivity(getActivity(), classes[i]);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
