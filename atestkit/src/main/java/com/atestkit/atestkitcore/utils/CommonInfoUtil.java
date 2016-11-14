package com.atestkit.atestkitcore.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.net.TrafficStats;
import android.os.Debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by lijianan on 16/11/10.
 */

public class CommonInfoUtil {

    public static ActivityManager.MemoryInfo getMemInfo(Context context) {
        if (context == null) {
            throw new NullPointerException("context must not be null");
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    private void getRunningAppProcessInfo(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得系统里正在运行的所有进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : runningAppProcessesList) {          // 进程ID号
            int pid = info.pid;
            // 用户ID          i
            int uid = info.uid;

            // 进程名
            String processName = info.processName;

            // 占用的内存
            int[] pids = new int[]{pid};
            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(pids);
            int memorySize = memoryInfo[0].dalvikPrivateDirty;

            System.out.println("processName=" + processName + ",pid=" + pid + ",uid=" + uid + ",memorySize=" + memorySize + "kb");
        }
        Runtime.getRuntime().maxMemory();
    }

    /**
     * get CPU rate
     *
     * @return
     */
    public static void getProcessCpuRate(int[] rateArr) {
        if (rateArr == null || rateArr.length != 2) {
            throw new IllegalArgumentException("rateArr must not be null and length must == 2");
        }

        try {
            String result;
            Process p = Runtime.getRuntime().exec("top -n 1");

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((result = br.readLine()) != null) {
                if (result.trim().length() < 1) {
                    continue;
                } else {
                    String[] CPUusr = result.split("%");
                    String[] CPUusage = CPUusr[0].split("User");
                    String[] SYSusage = CPUusr[1].split("System");

                    rateArr[0] = Integer.parseInt(CPUusage[1].trim());
                    rateArr[1] = Integer.parseInt(SYSusage[1].trim());
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getNetSpeed(Context context, long[] netArr) {
        int uid = PackageInfoUtils.getCrtUid(context);
        long receivedBytes = TrafficStats.getUidRxBytes(uid);
        long transmittedBytes = TrafficStats.getUidTxBytes(uid);
        netArr[0] = receivedBytes;
        netArr[1] = transmittedBytes;
    }
}
