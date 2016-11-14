package com.atestkit.atestkitcore.ui.tabview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atestkit.atestkitcore.R;
import com.atestkit.atestkitcore.test.common.CommonTest;
import com.atestkit.atestkitcore.utils.CommonInfoUtil;
import com.atestkit.atestkitcore.utils.Utils;

import static com.atestkit.atestkitcore.test.common.CommonTestType.TYPE_CPU;
import static com.atestkit.atestkitcore.test.common.CommonTestType.TYPE_MEMORY;
import static com.atestkit.atestkitcore.test.common.CommonTestType.TYPE_NETWORK_SPEED;


/**
 * Created by lijianan on 16/11/9.
 */

public class CommonTabLayout extends TabLayout {

    private TextView tvTotalMem;
    private TextView tvAvailMem;
    private TextView tvLowMem;
    private ProgressBar pbMem;
    private View vGc;

    private ProgressBar pbCPU;
    private View vGetCpu;
    private TextView tvUserCpu;
    private TextView tvSysCpu;

    private TextView tvRNetSpeed;
    private TextView tvTNetSpeed;
    private TextView tvRNetTotal;
    private TextView tvTNetTotal;

    private ViewGroup memLayout;
    private ViewGroup cpuLayout;
    private ViewGroup netLayout;

    private CommonTest mTest;

    private int[] cpuRate = {0, 0}; // user / system
    private long[] netBytes = {0, 0}; //received / transmitted
    private long lastTime;
    private boolean needRefresh = true;

    public CommonTabLayout(Context context) {
        super(context);
    }

    public CommonTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh() {
        refreshMemInfo();
        refreshNetSpeedInfo();
    }

    private void initViews() {
        tvTotalMem = (TextView) findViewById(R.id.tv_total_mem);
        tvAvailMem = (TextView) findViewById(R.id.tv_avail_mem);
        tvLowMem = (TextView) findViewById(R.id.tv_is_low_mem);
        pbMem = (ProgressBar) findViewById(R.id.pb_mem);
        vGc = findViewById(R.id.tv_gc);

        pbCPU = (ProgressBar) findViewById(R.id.pb_cpu);
        vGetCpu = findViewById(R.id.tv_getCpu);
        tvUserCpu = (TextView) findViewById(R.id.tv_user_cpu);
        tvSysCpu = (TextView) findViewById(R.id.tv_system_cpu);

        tvRNetSpeed = (TextView) findViewById(R.id.tv_net_speed_r);
        tvTNetSpeed = (TextView) findViewById(R.id.tv_net_speed_t);
        tvRNetTotal = (TextView) findViewById(R.id.tv_net_total_r);
        tvTNetTotal = (TextView) findViewById(R.id.tv_net_total_t);

        memLayout = (ViewGroup) findViewById(R.id.ll_mem);
        cpuLayout = (ViewGroup) findViewById(R.id.ll_cpu);
        netLayout = (ViewGroup) findViewById(R.id.ll_network);
    }

    private void setListener() {
        vGc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
            }
        });
        vGetCpu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (needShowCPUInfo()) {
                    refreshCPUInfo();
                }
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initViews();

        setListener();
    }

    private void refreshNetSpeedInfo() {
        long lastReceivedBytes = netBytes[0];
        long lastTransmittedBytes = netBytes[1];
        CommonInfoUtil.getNetSpeed(getContext(), netBytes);

        long dr = netBytes[0] - lastReceivedBytes;
        long dt = netBytes[1] - lastTransmittedBytes;

        long recivedSpeed = dr / (System.currentTimeMillis() - lastTime) / 1024 * 1000;
        long transmittedSpeed = dt / (System.currentTimeMillis() - lastTime) / 1024 * 1000;

        if (recivedSpeed > 1000) {
            recivedSpeed /= 1000;
            tvRNetSpeed.setText(String.format("下载速度:%d MB/s", recivedSpeed));
        } else {
            tvRNetSpeed.setText(String.format("下载速度:%d KB/s", recivedSpeed));
        }

        if (transmittedSpeed > 1000) {
            transmittedSpeed /= 1000;
            tvTNetSpeed.setText(String.format("上传速度:%d MB/s", transmittedSpeed));
        } else {
            tvTNetSpeed.setText(String.format("上传速度:%d KB/s", transmittedSpeed));
        }

        long rTotal = netBytes[0] / 1024 / 1024;
        long tTotal = netBytes[1] / 1024 / 1024;

        tvRNetTotal.setText(String.format("下载总量%d MB", rTotal));
        tvTNetTotal.setText(String.format("上传总量%d MB", tTotal));

        lastTime = System.currentTimeMillis();
    }

    private void refreshCPUInfo() {
        pbCPU.setMax(100);
        CommonInfoUtil.getProcessCpuRate(cpuRate);
        pbCPU.setProgress(cpuRate[0]);
        pbCPU.setSecondaryProgress(cpuRate[0] + cpuRate[1]);

        tvUserCpu.setText(String.format("user:%d%%", cpuRate[0]));
        tvSysCpu.setText(String.format("system:%d%%", cpuRate[1]));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void refreshMemInfo() {
        String maxMemMb = Utils.getMbByByte(Runtime.getRuntime().maxMemory());
        String freeMemMb = Utils.getMbByByte(Runtime.getRuntime().freeMemory());
        String totalMemMb = Utils.getMbByByte(Runtime.getRuntime().totalMemory());

        tvTotalMem.setText("Max:" + maxMemMb + "MB");
        tvAvailMem.setText("Free:" + freeMemMb + "MB");
        tvLowMem.setText("Total:" + totalMemMb + "MB");

        pbMem.setMax((int) Runtime.getRuntime().maxMemory());
        pbMem.setProgress((int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
        pbMem.setSecondaryProgress((int) Runtime.getRuntime().totalMemory());
    }

    public void setData(CommonTest test) {
        if (test != null) {
            mTest = test;
            if (needShowMemInfo()) {
                memLayout.setVisibility(VISIBLE);
            } else {
                memLayout.setVisibility(GONE);
            }

            if (needShowCPUInfo()) {
                cpuLayout.setVisibility(VISIBLE);
            } else {
                cpuLayout.setVisibility(GONE);
            }

            if (needShowNetInfo()) {
                netLayout.setVisibility(VISIBLE);
            } else {
                netLayout.setVisibility(GONE);
            }

        }
    }

    private boolean needShowNetInfo() {
        return mTest != null && mTest.getTypes().contains(TYPE_NETWORK_SPEED);
    }

    private boolean needShowCPUInfo() {
        return mTest != null && mTest.getTypes().contains(TYPE_CPU);
    }

    private boolean needShowMemInfo() {
        return mTest != null && mTest.getTypes().contains(TYPE_MEMORY);
    }
}
