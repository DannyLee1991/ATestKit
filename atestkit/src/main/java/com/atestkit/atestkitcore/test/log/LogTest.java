package com.atestkit.atestkitcore.test.log;

import com.atestkit.atestkitcore.test.AbsTest;

/**
 * Created by lijianan on 16/11/13.
 */

public class LogTest extends AbsTest {
    private String cmd;

    public LogTest(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }
}
