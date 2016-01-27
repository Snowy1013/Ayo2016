package com.snowy.demo.zfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cowthan.sample.R;

/**
 * Created by Snowy on 16/1/20.
 */
public class CommunThreeFragment extends Fragment {

    private Button btn_frag_commun_three;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_commun_three, container, false);
        btn_frag_commun_three = (Button) view.findViewById(R.id.btn_frag_commun_three);
        btn_frag_commun_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), " i am a btn in Fragment viewpager_three",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
