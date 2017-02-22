package com.atestkit.atestkit;

import android.app.Activity;
import android.os.Bundle;

import com.atestkit.atestkitcore.proxy.BackDoorActivityProxy;

/**
 * Created by lijianan on 2017/2/14.
 */

public class SampleBackDoorActivityProxy extends BackDoorActivityProxy {

    private static SampleBackDoorActivityProxy mInstance = new SampleBackDoorActivityProxy();

    public static SampleBackDoorActivityProxy getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            String title = activity.getString(R.string.app_name) + " back door";
            activity.setTitle(title);
            activity.setContentView(R.layout.activity_backdoor);
        }
    }
}
