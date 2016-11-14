package com.atestkit.atestkitcore.test.event;

import com.atestkit.atestkitcore.test.AbsTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijianan on 16/11/7.
 */

public class EventTest extends AbsTest {
    private List<EventInfo> infos = new ArrayList<>();

    private Object target;

    public EventTest(Object target) {
        this.target = target;
        genEventInfos();
    }

    private void genEventInfos() {
        Class clazz = target.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(TestMethod.class)) {
                EventInfo ei = new EventInfo(target);
                ei.setMethod(method);
                Annotation anno = method.getAnnotation(TestMethod.class);
                if (anno != null) {
                    ei.setName(((TestMethod) anno).name());
                    ei.setDescription(((TestMethod) anno).description());
                    ei.setArgs(((TestMethod) anno).args());
                }
                infos.add(ei);
            }
        }
    }

    public List<EventInfo> getInfos() {
        return infos;
    }

    public String getTargetClassName() {
        return target.getClass().getSimpleName();
    }
}
