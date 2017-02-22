package com.atestkit.atestkitcore.proxy;

import android.os.Bundle;

import com.atestkit.atestkitcore.BackDoorActivity;

/**
 * Created by lijianan on 2017/2/14.
 */

public class BackDoorActivityProxy implements IActivityProxy {

    private BackDoorActivity activity;

    public void setActivity(BackDoorActivity activity) {
        this.activity = activity;
    }

    public BackDoorActivity getActivity() {
        return activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
