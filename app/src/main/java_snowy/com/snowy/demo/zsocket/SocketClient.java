package com.snowy.demo.zsocket;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by zx on 16-12-16.
 */

public class SocketClient extends Thread{
    private String udid;
    private String ip;
    private int port;
    private Socket socket;
    private boolean running = false;
    private long lastSendTime;
    private OutputStream os;
    private InputStream is;
    private long checkDelay = 10;
    private long keepAliveDelay = 4 * 1000;

    public SocketClient(String udid, String ip, int port) {
        this.udid = udid;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open() throws IOException {
        Log.i("snowy", "start to create the socket long connect");
        if (running)
            return;
        socket = new Socket(ip, port);
        lastSendTime = System.currentTimeMillis();
        running = true;
        boolean flag = SocketClientUtils.sendLoginMessage(socket, udid);
        Log.i("snowy", "发送登录信息是否成功：" + flag);
        while (running) {
            if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
                try {
                    if (os == null)
                        os = socket.getOutputStream();
                    SocketClientUtils.sendHeartBeat(os, udid);
                    Log.i("snowy", "发送心跳包：" + udid);
                } catch (Exception e) {
                    e.printStackTrace();
                    SocketClient.this.close();
                }

                lastSendTime = System.currentTimeMillis();
            } else {
                try {
                    Thread.sleep(checkDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    SocketClient.this.close();
                }
            }
        }

        while (running) {
            try {
                InputStream is = socket.getInputStream();
                if (is.available() > 0) {
                    String receiveWorld = inputStream2String(is);
                    Log.i("snowy", "接收到了：" + receiveWorld);
                } else {
                    Thread.sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
                SocketClient.this.close();
            }
        }
//        new Thread(new KeepAliveWatchDog()).start();
//        new Thread(new ReceiveWatchDog()).start();
    }

    public void close() {
        if (running)
            running = false;
    }

    public String inputStream2String (InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for(int n; (n=in.read(b)) != -1;){
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    class KeepAliveWatchDog implements Runnable {
        long checkDelay = 10;
        long keepAliveDelay = 4 * 1000;

        @Override
        public void run() {
            while (running) {
                if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
                    try {
                        if (os == null)
                            os = socket.getOutputStream();
                        SocketClientUtils.sendHeartBeat(os, udid);
                        Log.i("snowy", "发送心跳包：" + udid);
                    } catch (Exception e) {
                        e.printStackTrace();
                        SocketClient.this.close();
                    }

                    lastSendTime = System.currentTimeMillis();
                } else {
                    try {
                        Thread.sleep(checkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        SocketClient.this.close();
                    }
                }
            }
        }
    }

    class ReceiveWatchDog implements Runnable {
        @Override
        public void run() {
            while (running) {
                try {
                    InputStream is = socket.getInputStream();
                    if (is.available() > 0) {
                        String receiveWorld = inputStream2String(is);
                        Log.i("snowy", "接收到了：" + receiveWorld);
                    } else {
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SocketClient.this.close();
                }
            }
        }
    }
}
