package com.atestkit.atestkitcore.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lijianan on 16/11/8.
 */

public class ShellExecutor {

    public static String exec(String cmds) {
        // 定义shell返回值
        String result = null;

        try {
            // 执行shell脚本
            Process pcs = Runtime.getRuntime().exec(cmds);

            // 获取shell返回流
            BufferedInputStream in = new BufferedInputStream(pcs.getInputStream());
            // 字符流转换字节流
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            // 这里也可以输出文本日志
            String lineStr;

            while ((lineStr = br.readLine()) != null) {
                result += lineStr + "\r\n";
            }

            // 关闭输入流
            br.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
