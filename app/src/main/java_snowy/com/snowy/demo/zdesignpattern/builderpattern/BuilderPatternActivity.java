package com.snowy.demo.zdesignpattern.builderpattern;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;
import com.snowy.demo.zeventbus.EventBean;
import com.snowy.demo.zutils.DateUtils;

import org.ayo.eventbus.EventBus;

import java.text.ParseException;

/**
 * Created by zx on 16-10-9.
 */
public class BuilderPatternActivity extends BaseActivity implements View.OnClickListener {
    Button bt_builder;
    TextView tv_builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_builder_pattern);

        EventBus.getDefault().register(this);

        bt_builder = findViewById(R.id.bt_builder);
        tv_builder = findViewById(R.id.tv_builder);

        bt_builder.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_builder:
                long beginDate = 0;
                long endDate = 0;
                try {
                    beginDate = DateUtils.stringToLong("2016-10-09", "yyyy-MM-dd");
                    endDate = DateUtils.stringToLong("2036-10-09", "yyyy-MM-dd");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //创建构建器对象
                InsuranceContract.ConcreteBuilder builder = new InsuranceContract.ConcreteBuilder("100011020161009", beginDate, endDate);
                //设置需要的数据，然后构建保险合同对象
                InsuranceContract insuranceContract = builder.setPersonName("乔晓明").setOtherData("this is just a test").build();
                //操作保险合同对象的方法
                insuranceContract.someOperation();
                break;
        }
    }

    public void onEventMainThread(EventBean event) {
        tv_builder.setText(event.getMsg());
    }
}
