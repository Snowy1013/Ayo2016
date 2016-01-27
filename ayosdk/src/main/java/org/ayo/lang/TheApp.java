package org.ayo.lang;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import org.ayo.Ayo;

import java.util.List;

/**
 * access informaions of this app
 *
 * Created by cowthan on 2016/1/24.
 */
public class TheApp {
    public static String getAppVersionName() {
        String versionName = "";
        try {
            PackageManager pm = Ayo.context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(Ayo.context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static int getAppVersionCode() {
        int versionCode = 0;
        try {
            PackageManager pm = Ayo.context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(Ayo.context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }

    /**
     *
     * @param cls
     * @return
     */
    public static boolean isServiceRunning(Class<?> cls) {
        ActivityManager am = (ActivityManager) Ayo.context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : infos) {
            String service = info.service.getClassName();
            if (service.equals(cls.getName())) {
                return true;
            }
        }
        return false;
    }
}
