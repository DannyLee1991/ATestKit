package com.atestkit.atestkitcore.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijianan on 16/11/7.
 */

public class TestPool {

    private List<ITest> testList = new ArrayList<>();
    private IUpdateCallback callback;

    private static TestPool testPool = new TestPool();

    private TestPool() {
    }

    public static TestPool getInstance() {
        return testPool;
    }

    public void setCallback(IUpdateCallback callback) {
        this.callback = callback;
    }

    public synchronized void add(ITest test) {
        testList.add(test);
        nodifyData();
    }

    public synchronized void remove(ITest test) {
        testList.remove(test);
        nodifyData();
    }

    public synchronized List<ITest> getTestList() {
        return testList;
    }

    private void nodifyData() {
        if (callback != null) {
            callback.onUpdate(testList);
        }
    }

    public interface IUpdateCallback{
        void onUpdate(List<ITest> tests);
    }

}
