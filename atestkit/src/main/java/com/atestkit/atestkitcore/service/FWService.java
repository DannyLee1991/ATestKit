package com.atestkit.atestkitcore.service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.atestkit.atestkitcore.ATestKitMainActivity;
import com.atestkit.atestkitcore.UIKit;

/**
 * Created by lijianan on 16/11/4.
 */

public class FWService extends Service {

    private static final String TAG = "FWService";
    public static boolean sIsStart = false;

    public static void start(Activity activity) {
        if (activity != null) {
            Intent intent = new Intent(activity, FWService.class);
            activity.startService(intent);
            sIsStart = true;
        }
    }

    public static void stop(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, FWService.class);
            context.stopService(intent);
            sIsStart = false;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "oncreat");
        UIKit.init(getApplication());

        createFloatView();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createFloatView() {
        UIKit.show(getApplication(),this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UIKit.hide();
    }

}
