package com.atestkit.atestkitcore.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by lijianan on 16/11/12.
 */

public class PackageInfoUtils {
    public static PackageInfo getCrtPackageInfo(Context context) {
        if (context == null) {
            return null;
        }
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static ApplicationInfo getCrtAppInfo(Context context) {
        if (context == null || getCrtPackageInfo(context) == null) {
            return null;
        }
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(getCrtPackageInfo(context).packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appInfo;
    }

    public static int getCrtUid(Context context) {
        ApplicationInfo info = getCrtAppInfo(context);
        if (info == null) {
            return -1;
        } else {
            return info.uid;
        }
    }
}
