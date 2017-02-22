package com.atestkit.atestkitcore;

import android.app.Activity;

import com.atestkit.atestkitcore.proxy.BackDoorActivityProxy;
import com.atestkit.atestkitcore.test.ITest;
import com.atestkit.atestkitcore.test.TestPool;
import com.atestkit.atestkitcore.test.activity.ActivityTest;
import com.atestkit.atestkitcore.test.common.CommonTest;
import com.atestkit.atestkitcore.test.common.CommonTestType;
import com.atestkit.atestkitcore.test.event.EventTest;
import com.atestkit.atestkitcore.test.log.LogTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by lijianan on 16/11/4.
 */

public class TestKit {

    static Map<Activity, ActivityTest> activityTestMap = new HashMap<>();
    static Map<Object, EventTest> eventTestMap = new HashMap<>();
    static Map<List<CommonTestType>, CommonTest> commonTestMap = new HashMap<>();
    static Map<String, LogTest> logTestMap = new ConcurrentHashMap<>();

    static BackDoorActivityProxy sBackDoorActivityProxy;

    public static void registActivity(Activity activity) {
        if (!activityTestMap.containsKey(activity)) {
            ActivityTest test = new ActivityTest(activity);
            activityTestMap.put(activity, test);
            test.regist();
        }
    }

    public static void unRegistActivity(Activity activity) {
        if (activityTestMap.containsKey(activity)) {
            ActivityTest test = activityTestMap.get(activity);
            if (test != null) {
                test.unRegist();
            }
            activityTestMap.remove(activity);
        }
    }

    public static void registEvent(Object target) {
        if (!eventTestMap.containsKey(target)) {
            EventTest test = new EventTest(target);
            eventTestMap.put(target, test);
            test.regist();
        }
    }

    public static void unRegistEvent(Object target) {
        if (eventTestMap.containsKey(target)) {
            EventTest test = eventTestMap.get(target);
            if (test != null) {
                test.unRegist();
            }
            eventTestMap.remove(target);
        }
    }

    public static void registCommonTest(List<CommonTestType> types) {
        if (!commonTestMap.containsKey(types)) {

            CommonTest test = new CommonTest(types);
            commonTestMap.put(types, test);
            test.regist();
        }
    }

    public static void unRegistCommonTest(List<CommonTestType> types) {
        if (commonTestMap.containsKey(types)) {
            CommonTest test = commonTestMap.get(types);
            if (test != null) {
                test.unRegist();
            }
            commonTestMap.remove(types);
        }
    }

    public static void registAllCommonTestType() {
        List<CommonTestType> types = new ArrayList<>();
        types.add(CommonTestType.TYPE_MEMORY);
        types.add(CommonTestType.TYPE_CPU);
        types.add(CommonTestType.TYPE_NETWORK_SPEED);
        registCommonTest(types);
    }

    public static void unRigistAllCommonTestType() {
        List<CommonTestType> types = new ArrayList<>();
        types.add(CommonTestType.TYPE_MEMORY);
        types.add(CommonTestType.TYPE_CPU);
        types.add(CommonTestType.TYPE_NETWORK_SPEED);
        unRegistCommonTest(types);
    }

    public static void executeCmd(String cmd) {
        clearTerminal();
        registLog(cmd);
    }

    public static void clearTerminal() {
        unRegistAllLog();
    }

    public static void registLog(String cmd) {
        if (!logTestMap.containsKey(cmd)) {
            LogTest test = new LogTest(cmd);
            logTestMap.put(cmd, test);
            test.regist();
        }
    }

    public static void unRegistLog(String cmd) {
        if (logTestMap.containsKey(cmd)) {
            LogTest test = logTestMap.get(cmd);
            if (test != null) {
                test.unRegist();
            }
            logTestMap.remove(cmd);
        }
    }

    public static void unRegistAllLog() {
        Iterator<Map.Entry<String, LogTest>> iterator = logTestMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, LogTest> map = iterator.next();
            String keyCmd = map.getKey();
            LogTest test = map.getValue();
            test.unRegist();
            logTestMap.remove(keyCmd);
        }
    }

    public static void setUpdateCallback(TestPool.IUpdateCallback callback) {
        TestPool.getInstance().setCallback(callback);
    }

    public static List<ITest> getAllTest() {
        return TestPool.getInstance().getTestList();
    }

    public static void getAllActivityTest(List<ActivityTest> tests) {
        List<ITest> allTests = TestPool.getInstance().getTestList();
        if (allTests != null && tests != null) {
            tests.clear();
            for (ITest t : allTests) {
                if (t instanceof ActivityTest) {
                    tests.add((ActivityTest) t);
                }
            }
        }
    }

    public static void getAllEventTest(List<EventTest> tests) {
        List<ITest> allTests = TestPool.getInstance().getTestList();
        if (allTests != null && tests != null) {
            tests.clear();
            for (ITest t : allTests) {
                if (t instanceof EventTest) {
                    tests.add((EventTest) t);
                }
            }
        }
    }

    public static void setBackDoorActivityProxy(BackDoorActivityProxy proxy) {
        sBackDoorActivityProxy = proxy;
    }

    public static BackDoorActivityProxy getBackDoorActivityProxy(){
        return sBackDoorActivityProxy;
    }

}
