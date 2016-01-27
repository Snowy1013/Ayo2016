package com.snowy.demo.zfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cowthan.sample.R;

/**
 * Created by Snowy on 16/1/20.
 */
public class CommunOneFragment extends Fragment implements View.OnClickListener{

    private Button btn_frag_commun_one;
    private FOneBtnClickListener fOneBtnClickListener;

    public void setFOneBtnClickListener(FOneBtnClickListener fOneBtnClickListener) {
        this.fOneBtnClickListener = fOneBtnClickListener;
    }

    /**
     * 设置按钮点击的回调
     */
    public interface FOneBtnClickListener{
        void onFOneBtnClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //生命周期测试
        System.out.println("onCreatView--------");

        View view = inflater.inflate(R.layout.frag_commun_one, container, false);
        btn_frag_commun_one = (Button) view.findViewById(R.id.btn_frag_commun_one);
        btn_frag_commun_one.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_frag_commun_one:
                if(fOneBtnClickListener != null){
                    fOneBtnClickListener.onFOneBtnClick();
                }

                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //生命周期测试
        System.out.println("onCreat--------");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //生命周期测试
        System.out.println("onAttatch--------");

    }

    @Override
    public void onStart() {
        super.onStart();

        //生命周期测试
        System.out.println("onSart--------");

    }

    @Override
    public void onResume() {
        super.onResume();

        //生命周期测试
        System.out.println("onResume--------");

    }

    @Override
    public void onPause() {
        super.onPause();

        //生命周期测试
        System.out.println("onPause--------");

    }

    @Override
    public void onStop() {
        super.onStop();

        //生命周期测试
        System.out.println("onStop--------");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //生命周期测试
        System.out.println("onDestroyView--------");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //生命周期测试
        System.out.println("onDestroy--------");

    }

    @Override
    public void onDetach() {
        super.onDetach();

        //生命周期测试
        System.out.println("onDetach--------");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //生命周期测试
        System.out.println("onActivityCreated--------");

    }
}
