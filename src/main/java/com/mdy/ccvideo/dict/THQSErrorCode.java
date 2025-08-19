package com.mdy.ccvideo.dict;

/**
 * CC视频接口的错误码
 *
 * @author MDY
 */
public enum THQSErrorCode {

    INVALID_REQUEST("用户输入参数错误"),
    SPACE_NOT_ENOUGH("用户剩余空间不足"),
    SERVICE_EXPIRED("用户服务已经过期"),
    PROCESS_FAIL("服务器处理失败"),
    TOO_MANY_REQUEST("访问过于频繁"),
    PERMISSION_DENY("用户服务无权限"),

    UNKNOWN("CC视频API未知报错"),
    NETWORK_ERROR("CC视频API请求访问报错"),
    ;

    private String msg;

    THQSErrorCode(String msg) {
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }
}
