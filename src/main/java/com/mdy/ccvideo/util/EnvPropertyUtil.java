package com.mdy.ccvideo.util;

public class EnvPropertyUtil {

    /**
     * 依次从 JVM 启动参数和环境变量获取值
     *
     * @param key 属性名
     * @return 属性值，若都不存在则返回 null
     */
    public static String get(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = System.getenv(key);
        }
        return value;
    }
}