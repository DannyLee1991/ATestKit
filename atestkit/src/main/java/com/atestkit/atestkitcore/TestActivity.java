package com.atestkit.atestkitcore;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atestkit.atestkitcore.service.FWService;

import java.util.List;

/**
 * Created by lijianan on 16/11/3.
 */

public class TestActivity extends Activity {

    View onOffView;
    View activityTestView;
    View removeTestView;
    View btTest;

    boolean isOpen = false;
    Intent intent;

    int testCode = 0;

    public static void start(ContextWrapper context) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        onOffView = findViewById(R.id.bt_onoff);
        activityTestView = findViewById(R.id.bt_add_activity_test);
        removeTestView = findViewById(R.id.bt_remove_activity_test);
        btTest = findViewById(R.id.bt_test);

        intent = new Intent(TestActivity.this, FWService.class);

        onOffView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    stopFWServer();
                } else {
                    startFWServer();
                }

                isOpen = !isOpen;
            }
        });

        activityTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestKit.registAllCommonTestType();
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo p : processes) {
                    if ("com.atestkit.atestkitcore".equals(p.processName)) {
                        TestKit.registLog("logcat -T 500 | grep --color=auto " + p.pid);
                    }
                }
            }
        });

        removeTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestKit.unRigistAllCommonTestType();
                TestKit.unRegistLog("logcat");
            }
        });

        btTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testCode++;
            }
        });

        /******** test code ********/
        startFWServer();
        isOpen = true;

        /******** test code ********/
    }

    @Override
    protected void onStart() {
        super.onStart();
        TestKit.registActivity(TestActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        TestKit.unRegistActivity(TestActivity.this);
    }

    private void stopFWServer() {
        stopService(intent);
    }


    private void startFWServer() {
        startService(intent);
    }
}
