package com.mdy.ccvideo.exception;

import com.mdy.ccvideo.dict.THQSErrorCode;

/**
 * 自定义的异常类，便于异常的集中处理
 *
 * @author MDY
 */
public class THQSException extends RuntimeException {

    /**
     * 异常代号
     */
    private String code;

    /**
     * 自定义异常
     *
     * @param message 异常信息
     * @param code    异常代号
     */
    public THQSException(String message,String code) {
        super(message);
        this.code = code;
    }

    /**
     * 自定义异常
     *
     * @param errorCode 异常枚举
     */
    public THQSException(THQSErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.name();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
