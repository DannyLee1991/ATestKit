package com.atestkit.atestkit;

import android.app.Application;

import com.atestkit.atestkitcore.TestKit;

/**
 * Created by lijianan on 2017/2/22.
 */

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 注册通用信息
        TestKit.registAllCommonTestType();
        // 设置后门activity代理
        TestKit.setBackDoorActivityProxy(SampleBackDoorActivityProxy.getInstance());
    }
}
