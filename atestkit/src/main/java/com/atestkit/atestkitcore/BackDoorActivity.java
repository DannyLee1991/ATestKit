package com.atestkit.atestkitcore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.atestkit.atestkitcore.proxy.BackDoorActivityProxy;

/**
 * Created by lijianan on 2017/2/14.
 */

public class BackDoorActivity extends AppCompatActivity {
    private BackDoorActivityProxy mProxy = TestKit.getBackDoorActivityProxy();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mProxy != null) {
            mProxy.setActivity(this);
            mProxy.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mProxy != null) {
            mProxy.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mProxy != null) {
            mProxy.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mProxy != null) {
            mProxy.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mProxy != null) {
            mProxy.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProxy != null) {
            mProxy.onDestroy();
        }
    }
}
