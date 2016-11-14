package com.atestkit.atestkitcore.test.common;

import com.atestkit.atestkitcore.test.AbsTest;

import java.util.List;

/**
 * Created by lijianan on 16/11/7.
 */

public class CommonTest extends AbsTest {
    List<CommonTestType> types;

    public CommonTest(List<CommonTestType> types) {
        this.types = types;
    }

    public List<CommonTestType> getTypes() {
        return types;
    }
}
