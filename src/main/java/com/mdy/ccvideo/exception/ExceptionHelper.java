package com.mdy.ccvideo.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * 异常处理工具类
 *
 * @author MDY
 */
public class ExceptionHelper {
    public static String getTrace(Exception e) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(stream));
        return stream.toString();
    }
}