package com.atestkit.atestkitcore.utils.reflect;

/**
 * Created by lijianan on 16/11/9.
 */

public class FieldInfo {
    public String value;
    public String name;
    public String type;

    public FieldInfo(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return type + " " + name + " = " + value;
    }

    public String toHtmlString() {
        return "<font color='#ffffff'>" + type + "</font> " +
                "<font color='#0099ff'>" + name + "</font>" + " = " +
                "<font color='#ffff00'>" + value + "</font>";
    }
}
