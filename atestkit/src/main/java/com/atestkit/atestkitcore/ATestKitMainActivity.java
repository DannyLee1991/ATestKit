package com.atestkit.atestkitcore;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.atestkit.atestkitcore.service.FWService;

/**
 * Created by lijianan on 16/11/3.
 */

public class ATestKitMainActivity extends AppCompatActivity implements View.OnClickListener {

    private View mOnOffView;
    private View mStartInfoActivityButton;

    public static void start(ContextWrapper context) {
        Intent intent = new Intent(context, ATestKitMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atestkit_activity_test);
        initView();
        setListener();
    }

    private void setListener() {
        mOnOffView.setOnClickListener(this);
        mStartInfoActivityButton.setOnClickListener(this);
    }

    private void initView() {
        setTitle(R.string.app_name);
        mOnOffView = findViewById(R.id.bt_onoff);
        mStartInfoActivityButton = findViewById(R.id.bt_start_backdoor_activity);
        if (TestKit.getBackDoorActivityProxy() == null) {
            mStartInfoActivityButton.setVisibility(View.GONE);
        } else {
            mStartInfoActivityButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        TestKit.registActivity(ATestKitMainActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        TestKit.unRegistActivity(ATestKitMainActivity.this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_onoff) {
            if (FWService.sIsStart) {
                FWService.stop(ATestKitMainActivity.this);
            } else {
                FWService.start(ATestKitMainActivity.this);
            }
        } else if (v.getId() == R.id.bt_start_backdoor_activity) {
            Intent intent = new Intent(ATestKitMainActivity.this, BackDoorActivity.class);
            startActivity(intent);
            Toast.makeText(ATestKitMainActivity.this, "start info activity", Toast.LENGTH_SHORT).show();
        }

    }
}
