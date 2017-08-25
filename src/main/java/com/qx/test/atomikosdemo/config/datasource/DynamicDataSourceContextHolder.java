package com.qx.test.atomikosdemo.config.datasource;

/**
 * Created by qinxue on 2017/8/24.
 */
public class DynamicDataSourceContextHolder {
    private static final String DS1 = "ds1";
    private static final String DS2 = "ds2";
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static String get() {
        return contextHolder.get();
    }

    public static void set(String ds) {
        contextHolder.set(ds);
    }

    public static void remove() {
        contextHolder.remove();
    }
}
