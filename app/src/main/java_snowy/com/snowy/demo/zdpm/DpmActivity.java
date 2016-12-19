package com.snowy.demo.zdpm;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx on 16-12-15.
 */

public class DpmActivity extends BaseActivity implements View.OnClickListener{

    private ListView lv_dpm_contents;
    private List<String> contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_dpm_main);
        contents = new ArrayList<>();
        contents.add("录音");
        contents.add("USB调试");
        contents.add("USB传输");
        contents.add("隐藏QQ图标");
        contents.add("截屏");
        contents.add("调节音量");

        Button tv_dpm_clearowner = findViewById(R.id.bt_dpm_clearowner);
        tv_dpm_clearowner.setOnClickListener(this);
        lv_dpm_contents = findViewById(R.id.lv_dpm_contents);
        DpmMainAdapter adapter = new DpmMainAdapter();
        lv_dpm_contents.setAdapter(adapter);
    }

    class DpmMainAdapter extends BaseAdapter {
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return contents.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View view = convertView;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(getActivity(), R.layout.item_dpm_main, null);
                holder.bt_open = (Button) view.findViewById(R.id.bt_item_open);
                holder.bt_close = (Button) view.findViewById(R.id.bt_item_close);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.bt_open.setText("启用" + contents.get(position));
            holder.bt_close.setText("禁用" + contents.get(position));
            holder.bt_open.setOnClickListener(DpmActivity.this);
            holder.bt_close.setOnClickListener(DpmActivity.this);
            holder.bt_open.setTag(position);
            holder.bt_close.setTag(position);

            return view;
        }

        @Override
        public int getCount() {
            return contents.size();
        }

        class ViewHolder {
            Button bt_open;
            Button bt_close;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_dpm_clearowner:
                Toast.makeText(getActivity(), "清除owner权限", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_item_open:
                Toast.makeText(getActivity(), "position = " + v.getTag().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_item_close:
                Toast.makeText(getActivity(), "position = " + v.getTag().toString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
