package com.atestkit.atestkitcore.test;

/**
 * Created by lijianan on 16/11/7.
 */

public abstract class AbsTest implements ITest {

    @Override
    public void regist() {
        TestPool.getInstance().add(this);
    }

    @Override
    public void unRegist() {
        TestPool.getInstance().remove(this);
    }

}
