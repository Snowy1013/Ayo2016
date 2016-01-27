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
public class StaticTitleFragment extends Fragment {
    private Button btn_fragment_simple_left;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_static_title, container, false);
        btn_fragment_simple_left = (Button)view.findViewById(R.id.btn_fragment_static_left);
        btn_fragment_simple_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "I am an ImageButton in TitleFragment ! ", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
