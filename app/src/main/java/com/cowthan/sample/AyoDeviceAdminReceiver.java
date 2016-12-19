package com.cowthan.sample;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zx on 16-9-28.
 */
public class AyoDeviceAdminReceiver extends DeviceAdminReceiver {
    public static ComponentName getCn(Context context) {
        return new ComponentName(context, AyoDeviceAdminReceiver.class);
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {

        return null;
    }

    @Override
    public DevicePolicyManager getManager(Context context) {
        System.out.println("getManager");
        // TODO Auto-generated method stub
        return super.getManager(context);
    }

    @Override
    public ComponentName getWho(Context context) {
        System.out.println("getWho");
        // TODO Auto-generated method stub
        return super.getWho(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
    }
}
