package com.snowy.demo.zdpm;

import android.app.admin.DevicePolicyManager;
import android.content.Context;

/**
 * Created by zx on 16-12-16.
 */

public class DpmManager {

    private DevicePolicyManager dpm;

    public DpmManager(Context context){
        dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

    }

}
