package com.snowy.demo.zrxjava;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zx on 16-9-26.
 */
public class RxJavaDemoActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "snowy";
    private final String[] names = {"张三", "李四", "王五", "赵六"};
    private int[] imageIds = new int[]{
            R.drawable.snowy_cute1, R.drawable.snowy_cute2, R.drawable.snowy_cute3, R.drawable.snowy_cute4,
            R.drawable.snowy_cute5, R.drawable.snowy_cute6, R.drawable.snowy_cute7, R.drawable.snowy_cute8,
            R.drawable.snowy_cute9, R.drawable.snowy_cute10, R.drawable.snowy_cute11, R.drawable.snowy_cute12,
            R.drawable.snowy_cute13, R.drawable.snowy_cute14, R.drawable.snowy_cute15, R.drawable.snowy_cute16
    };

    private int imageId = R.drawable.snowy_cute1;
    private Student[] students = new Student[3];

    private ImageView iv_rxjava_imgshow;
    private Button mThreadButton, mAsyncButton, mRxButton;
    private LinearLayout mRootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_rxjava_demo);

        mRootView = findViewById(R.id.rxjava_rootview);
        findViewById(R.id.bt_rxjava_printstr).setOnClickListener(this);
        findViewById(R.id.bt_rxjava_showimg).setOnClickListener(this);
        findViewById(R.id.bt_rxjava_change).setOnClickListener(this);
        mThreadButton = findViewById(R.id.bt_rxjava_mainthread);
        mThreadButton.setOnClickListener(this);
        mAsyncButton = findViewById(R.id.bt_rxjava_asyncthread);
        mAsyncButton.setOnClickListener(this);
        mRxButton = findViewById(R.id.bt_rxjava_rx);
        mRxButton.setOnClickListener(this);

        iv_rxjava_imgshow = findViewById(R.id.iv_rxjava_imgshow);

        Course courseCN = new Course("语文");
        Course courseMATH = new Course("数学");
        Course courseEN = new Course("英语");
        Course courseDR = new Course("美术");
        Course coursePO = new Course("政治");
        Course courseHI = new Course("历史");
        Student stu1 = new Student("张三");
        List<Course> courses1 = new ArrayList<Course>();
        courses1.add(courseCN);
        courses1.add(courseDR);
        stu1.setCourses(courses1);
        Student stu2 = new Student("李四");
        List<Course> courses2 = new ArrayList<Course>();
        courses2.add(courseMATH);
        courses2.add(coursePO);
        stu2.setCourses(courses2);
        Student stu3 = new Student("王五");
        List<Course> courses3 = new ArrayList<Course>();
        courses3.add(courseEN);
        courses3.add(coursePO);
        stu3.setCourses(courses3);
        students[0] = stu1;
        students[1] = stu2;
        students[2] = stu3;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_rxjava_printstr:
                Observable.from(names).subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {    //onNext()
                        Log.i(TAG, name);
                    }
                });
                break;
            case R.id.bt_rxjava_showimg:
                showImages();
                break;
            case R.id.bt_rxjava_change:
                mapAndFlatMap();
                break;
            case R.id.bt_rxjava_mainthread:
                runInMainThread();
                break;
            case R.id.bt_rxjava_asyncthread:
                runInAsyncThread();
                break;
            case R.id.bt_rxjava_rx:
                runWithRx();
                break;
        }
    }

    @TargetApi(21)
    private void showImages(){
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Log.i(TAG, "当前线程：" + Thread.currentThread());
                Drawable drawable = getActivity().getResources().getDrawable(imageId);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())   //指定subcribe()发生在I/O线程
                .observeOn(AndroidSchedulers.mainThread())  //指定Subcriber回调发生在主线程
                .subscribe(new Subscriber<Drawable>() {
            @Override
            public void onNext(Drawable drawable) {
                Log.i(TAG, "当前线程：" + Thread.currentThread());
                iv_rxjava_imgshow.setImageDrawable(drawable);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onCompleted() {
                Log.i(TAG, "加载完成！");
            }
        });
    }

    private void mapAndFlatMap(){
        /*Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        };
        Observable.from(students).map(new Func1<Student, String>() {
            @Override
            public String call(Student student) {
                return student.getName();
            }
        }).subscribe(subscriber);*/
        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                Log.i(TAG, course.getCourseName());
            }
        };
        Observable.from(students).flatMap(new Func1<Student, Observable<Course>>() {
            @Override
            public Observable<Course> call(Student student) {
                Log.i(TAG, student.getName());
                return Observable.from(student.getCourses());
            }
        }).subscribe(subscriber);
    }

    // 长时间运行的任务
    private String longRunningOperation() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            Log.e("DEBUG", e.toString());
        }

        return "Complete!";
    }

    private void runInMainThread(){
        mThreadButton.setEnabled(false);
        longRunningOperation();
        Snackbar.make(mRootView, longRunningOperation(), Snackbar.LENGTH_LONG).show();
        mThreadButton.setEnabled(true);
    }

    private void runInAsyncThread(){
        mAsyncButton.setEnabled(false);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return longRunningOperation();
            }

            @Override
            protected void onPostExecute(String s) {
                Snackbar.make(mRootView, s, Snackbar.LENGTH_LONG).show();
                mAsyncButton.setEnabled(true);
            }
        }.execute();
    }

    private void runWithRx(){
        mRxButton.setEnabled(false);
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(longRunningOperation());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mRxButton.setEnabled(true);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Snackbar.make(mRootView, s, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
