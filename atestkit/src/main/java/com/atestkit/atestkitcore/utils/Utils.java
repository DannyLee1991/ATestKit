package com.atestkit.atestkitcore.utils;

/**
 * Created by lijianan on 16/11/11.
 */

public class Utils {

    public static final int K = 1024;

    public static String getMbByByte(long bytes) {
        float reslut = (float) bytes / (K * K);
        return String.format("%.2f", reslut);
    }
}
