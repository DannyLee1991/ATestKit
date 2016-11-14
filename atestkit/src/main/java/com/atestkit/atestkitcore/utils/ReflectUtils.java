package com.atestkit.atestkitcore.utils;


import com.atestkit.atestkitcore.utils.reflect.FieldInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijianan on 16/11/9.
 */
public class ReflectUtils {

    public static List<FieldInfo> getFieldInfos(Object obj) throws IllegalAccessException {
        Field[] fs = obj.getClass().getDeclaredFields();

        List<FieldInfo> fieldInfos = new ArrayList<>();

        for (int i = 0; i < fs.length; i++) {

            Field f = fs[i];
            f.setAccessible(true); //设置些属性是可以访问的
            Object val = f.get(obj);//得到此属性的值
            String type = f.getType().toString();//得到此属性的类型

            fieldInfos.add(new FieldInfo(f.getName(), val == null ? "null" : val.toString(), type));
        }
        return fieldInfos;
    }
}
