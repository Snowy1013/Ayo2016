
package com.snowy.demo.zsocket;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClientUtils {

    public static String PUSH_TAG = "push";

    private static final byte REGISTER = 0x7d;
    private static final byte LOGIN = 0x7f;
    private static final byte HEARTBEAT = 0x7e;

    private static final byte ACK = 0x77;

    public static final byte COMMAND = 0x55;
    public static final byte POLICY = 0x75;
    public static final byte DELUSER = 0x44;
    public static final byte DELDEVICE = 0x64;
    public static final byte DELOWNS = 0x65;
    public static final byte DOWNLOAD_APPS = 0x61;
    public static final byte ACKHEARTBEAT = 0x41;

    // private static fianl byte
    private static final byte VERSION2_CODE0 = 0x01;
    private static final byte VERSION2_CODE1 = 0x02;

    public static final byte PUSH_MSG_TYPE_CMD = 0x01;
    public static final byte PUSH_MSG_TYPE_MSG = 0x02;
    public static final byte HEARTBEAT_ACK = 0x41;

    private static final String appKey = "6189bd92bd2339b6dd0bb946190e3955";

    /*
     * Changes: compatablie encrypt and unencrypt code;
     */
    public static boolean sendHeartBeat(OutputStream os, String uuid) {

        boolean flag = false;
        if (os == null) {
            return false;
        }
        if (uuid == null) {
            return false;
        }
        byte[] heartbeat = uuid.getBytes();
        byte[] message = new byte[heartbeat.length + 2];
        message[0] = HEARTBEAT;
        message[1] = (byte) heartbeat.length;

        for (int i = 0; i < heartbeat.length; i++) {
            message[i + 2] = heartbeat[i];
        }
        /*
         * changes:encrypt code and unencrypt code ;
         */
        byte[] code = DES.getInstance().encryptBasedDes(message);
        byte[] code2 = new byte[code.length + 2];
        code2[0] = VERSION2_CODE0;
        code2[1] = VERSION2_CODE1;
        for (int i = 0; i < code.length; i++) {
            code2[i + 2] = code[i];
        }
        Log.i("snowy", "HeartBeat length: " + code2.length);
        try {
            os.write(code2);
            os.flush();
            flag = true;
        } catch (Exception e) {
            flag = false;
            try {
                os.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        return flag;
    }

    public static boolean sendLoginMessage(Socket socket, String uuid) {

        // boolean flag = sendLoginMessage(channel, uuid.getBytes());
        if (uuid == null) {
            return false;
        }
        byte[] loginMessge = getLoginMessage(uuid);
        byte[] registerMessage = getRegisterMessage(appKey);

        byte[] source = new byte[loginMessge.length + registerMessage.length];
        int i = 0;
        while (i < source.length) {
            for (int j = 0; j < registerMessage.length; j++) {
                source[i] = registerMessage[j];
                i++;
            }
            for (int k = 0; k < loginMessge.length; k++) {
                source[i] = loginMessge[k];
                i++;
            }
        }
        boolean flag = sendRegLoginMessageEx(socket, source);

        return flag;
    }

    /*
     * version 2.0 Changes:compatible the encrypt code with the unencrypt code
     */
    private static boolean sendRegLoginMessageEx(Socket socket, byte[] source) {
        if (!socket.isConnected())
            return false;
        if (source == null) {
            return false;
        }
        /*
         * compatible:changes
         */
        byte[] code = DES.getInstance().encryptBasedDes(source);
        byte[] code2 = new byte[code.length + 2];
        code2[0] = VERSION2_CODE0;
        code2[1] = VERSION2_CODE1;
        for (int i = 0; i < code.length; i++) {
            code2[i + 2] = code[i];
        }
        try {
            OutputStream os = socket.getOutputStream();
            os.write(code2);
            os.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static byte[] getLoginMessage(String uuid) {
        if (uuid == null) {
            return null;
        }
        byte[] uuidByte = uuid.getBytes();
        byte[] loginMessage = new byte[uuidByte.length + 2];
        loginMessage[0] = 0x7f;
        loginMessage[1] = (byte) uuidByte.length;
        for (int i = 0; i < uuidByte.length; i++) {
            loginMessage[i + 2] = uuidByte[i];
        }

        return loginMessage;
    }

    private static byte[] getRegisterMessage(String register) {
        if (register == null) {
            return null;
        }
        byte[] bytes = register.getBytes();
        byte[] registerByte = new byte[bytes.length + 2];
        registerByte[0] = 0x7d;
        registerByte[1] = (byte) bytes.length;
        for (int i = 0; i < bytes.length; i++) {

            registerByte[i + 2] = bytes[i];
        }
        return registerByte;
    }
}
