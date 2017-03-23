package com.snowy.demo.zreader;

import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zx on 17-2-28.
 */

public class ReaderMainActivity extends BaseActivity {
    private ListView lv_reader_main;
    FileAdapter mAdapter;
    //历史页面
    private List<File> histroyFiles = new ArrayList<File>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_reader_main);
        lv_reader_main = findViewById(R.id.lv_reader_main);
        mAdapter = new FileAdapter(new ArrayList<File>());
        lv_reader_main.setAdapter(mAdapter);
        initData();
    }

    private void initData(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getActivity(), "SD卡不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        File root = Environment.getExternalStorageDirectory();
        listFiles(root);
//        List rootFiles = getAllFiles(root);

    }

    public void listFiles(final File file){
        Observable<List<File>> observable = Observable.create(new Observable.OnSubscribe<List<File>>() {
            @Override
            public void call(Subscriber<? super List<File>> subscriber) {
                subscriber.onNext(getAllFiles(file));
            }
        }) .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Subscriber<List<File>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<File> files) {
                if (files.size() == 0) {
                    Toast.makeText(getActivity(), "这是一个空的文件夹", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAdapter.setData(files);
                mAdapter.notifyDataSetChanged();
                histroyFiles.add(file);
            }
        });
    }

    private List<File> getAllFiles(File root){
        File[] files = root.listFiles();
        List<File> filesList = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                filesList.add(file);
            }
        }
        return filesList;
    }

    class FileAdapter extends BaseAdapter {
        private List<File> filesList;

        public FileAdapter (List<File> files){
            this.filesList = files;
        }

        public void setData(List<File> files){
            this.filesList = files;
        }

        @Override
        public int getCount() {
            return filesList.size();
        }

        @Override
        public Object getItem(int position) {
            return filesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (null == convertView){
                view = View.inflate(getActivity(), R.layout.item_list_reader, null);
                holder = new ViewHolder();
                holder.iv_item_reader = (ImageView) view.findViewById(R.id.iv_item_reader);
                holder.tv_item_reader = (TextView) view.findViewById(R.id.tv_item_reader);
                view.setTag(holder);
            }else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            holder.tv_item_reader.setText(filesList.get(position).getName());
            if (filesList.get(position).isDirectory()) {
                holder.iv_item_reader.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.snowy_directory));
            } else {
                holder.iv_item_reader.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.snowy_fileicon));
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (filesList.get(position).isDirectory()) {
                        listFiles(filesList.get(position));
                    } else {
                        Toast.makeText(getActivity(), "这是一个文件,尚未实现reader", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return view;
        }

        class ViewHolder{
            ImageView iv_item_reader;
            TextView tv_item_reader;
        }
    }

    @Override
    public Boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (histroyFiles.size() <= 1) {
                histroyFiles.clear();
                return super.onKeyDown(keyCode, event);
            } else {
                histroyFiles.remove(histroyFiles.size() - 1);
                listFiles(histroyFiles.get(histroyFiles.size() - 1));
                histroyFiles.remove(histroyFiles.size() - 1);
                return  false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
