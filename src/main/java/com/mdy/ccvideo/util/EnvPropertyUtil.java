package com.mdy.ccvideo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnvPropertyUtil {

    private static final Logger log = LogManager.getLogger(EnvPropertyUtil.class);

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

    /**
     * SpringBoot启动的话，使用这个类把application里的内容设置进去
     */
    public static void set(String key, String value) {
        log.info("Set env property: {}={}", key, value);
        System.setProperty(key, value);
    }
}