package com.snowy.demo.zfragment;

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
public class CommunTwoFragment extends Fragment implements View.OnClickListener{

    private Button btn_frag_commun_two;

    /**
     * 设置按钮点击的回调
     */
    public interface FTwoBtnClickListener{
        void onFTwoBtnClick();
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_commun_two, container, false);
        btn_frag_commun_two = (Button) view.findViewById(R.id.btn_frag_commun_two);
        btn_frag_commun_two.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        //交给宿主activity去处理，如果它想处理的话
        if(getActivity() instanceof FTwoBtnClickListener){
            ((FTwoBtnClickListener) getActivity()).onFTwoBtnClick();
        }


    }
}
