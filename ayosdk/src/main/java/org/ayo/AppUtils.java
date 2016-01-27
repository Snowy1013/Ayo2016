package org.ayo;

import java.util.List;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * AppUtils：app，manifest
 *
 * 
 */
public class AppUtils {

	public static String getChannelCode(Context context) {
		String code = getMetaData(context, "CHANNEL");
		if (code != null) {
			return code;
		}
		return "C_000";
	}

	 /**
     *
     */
	public static String getMetaData(Context context, String key) {
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			Object value = ai.metaData.get(key);
			if (value != null) {
				return value.toString();
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	public static boolean checkPermission(Context context, String permission){
		PackageManager localPackageManager = context.getPackageManager();
        if (localPackageManager.checkPermission(permission,  //"android.permission.ACCESS_NETWORK_STATE"
        		context.getPackageName()) != 0) {
            return true;
        }else{
        	return false;
        }
	}
	
	public static String getVersion(Context appContext) {
        String version = "1.0.0";
        PackageManager packageManager = appContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    appContext.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    
    public static int getVersionCode(Context appContext) {
        int version = 1000;
        PackageManager packageManager = appContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    appContext.getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getPackageName(Context appContext) {
        String packageName = "";
        PackageManager packageManager = appContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    appContext.getPackageName(), 0);
            packageName = packageInfo.packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

    /*
     * require    <uses-permission android:name="android.permission.GET_TASKS" />
     */
    public static boolean isMyPackageRunningOnTop(Context appContext) {
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        if(am == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> infos = am.getRunningTasks(1);
        if (infos != null && !infos.isEmpty()) {
            ActivityManager.RunningTaskInfo info = infos.get(0);
            ComponentName componentName = info.topActivity;
            if (componentName != null
                    && componentName.getPackageName().equals(appContext.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
