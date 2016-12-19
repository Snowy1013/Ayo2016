package com.snowy.demo.zsocket;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by zx on 16-12-16.
 */

public class SocketDemoActivity extends BaseActivity {
    private EditText et_socket_ip, et_socket_port;
    private String udid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_socket_demo);

        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        udid = tm.getDeviceId();

        et_socket_ip = findViewById(R.id.et_socket_ip);
        et_socket_port = findViewById(R.id.et_socket_port);

        et_socket_ip.setText("trustdata-push.chinacloudapp.cn");
        et_socket_port.setText("8089");

        Button bt_socket_open = findViewById(R.id.bt_socket_open);
        bt_socket_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = et_socket_ip.getText().toString().trim();
                String port = et_socket_port.getText().toString().trim();
                if(ip == null || ip.equals(""))
                    Toast.makeText(getActivity(), "IP不能为空", Toast.LENGTH_SHORT).show();
                if (port == null || port.equals(""))
                    Toast.makeText(getActivity(), "PORT不能为空", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "开始建立到" + ip + ":" + port + "的长连接", Toast.LENGTH_SHORT).show();
                new SocketClient(udid, ip, Integer.parseInt(port)).start();
            }
        });
    }
}
