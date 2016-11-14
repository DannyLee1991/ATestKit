package com.atestkit.atestkitcore.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.atestkit.atestkitcore.UIKit;

/**
 * Created by lijianan on 16/11/4.
 */

public class FWService extends Service {

    private static final String TAG = "FWService";

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
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
