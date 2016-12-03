package com.atestkit.atestkitcore.test.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lijianan on 16/11/13.
 */

public class EventInfo {
    private Object target;
    private Method method;
    private String name;
    private String description;
    private String[] args;

    public EventInfo(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void invoke() throws InvocationTargetException {
        if (method != null) {
            Class[] paramTypes = method.getParameterTypes();
            Object[] newArgs = new Object[paramTypes.length];

            int i = 0;

            for (Class<?> c : paramTypes) {
                basicCast(newArgs, i, c);
                i++;
            }

            try {
                method.setAccessible(true);
                method.invoke(target, newArgs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void basicCast(Object[] newArgs, int i, Class<?> c) {
        if (c.getSimpleName().equals("long")) {
            newArgs[i] = Long.valueOf(args[i]);
        } else if (c.getSimpleName().equals("int")) {
            newArgs[i] = Integer.valueOf(args[i]);
        } else if (c.getSimpleName().equals("short")) {
            newArgs[i] = Short.valueOf(args[i]);
        } else if (c.getSimpleName().equals("byte")) {
            newArgs[i] = Byte.valueOf(args[i]);
        } else if (c.getSimpleName().equals("float")) {
            newArgs[i] = Float.valueOf(args[i]);
        } else if (c.getSimpleName().equals("double")) {
            newArgs[i] = Double.valueOf(args[i]);
        } else if (c.getSimpleName().equals("boolean")) {
            newArgs[i] = Boolean.valueOf(args[i]);
        } else {
            newArgs[i] = c.cast(args[i]);
        }
    }
}
