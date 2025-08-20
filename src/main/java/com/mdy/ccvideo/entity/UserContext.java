package com.mdy.ccvideo.entity;

import com.mdy.ccvideo.util.EnvPropertyUtil;
import com.mdy.ccvideo.util.IStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.mdy.ccvideo.dict.THQSConstants.CC_VIDEO_SWITCH;
import static com.mdy.ccvideo.dict.THQSConstants.KEY_PROP;
import static com.mdy.ccvideo.dict.THQSConstants.ROOM_KEY_PROP;
import static com.mdy.ccvideo.dict.THQSConstants.USERID_PROP;

public class UserContext {

    private static final Logger log = LogManager.getLogger(UserContext.class);

    private String key;
    private String room_key;
    private String userid;

    public UserContext() {
        // 初始化注入用户参数
        if (Boolean.parseBoolean(EnvPropertyUtil.get(CC_VIDEO_SWITCH))) {

            key = EnvPropertyUtil.get(KEY_PROP);
            room_key = EnvPropertyUtil.get(ROOM_KEY_PROP);
            userid = EnvPropertyUtil.get(USERID_PROP);
            if (IStringUtils.isBlank(key)) {
                throw new IllegalArgumentException("CC视频点播的key未设置");
            }
            if (IStringUtils.isBlank(userid)) {
                throw new IllegalArgumentException("CC视频点播的用户ID未设置");
            }
            if (IStringUtils.isBlank(room_key)) {
                throw new IllegalArgumentException("CC视频直播的key未设置");
            }
            log.info("CC VIDEO SOK IS ENABLE");
        } else {
            log.info("CC VIDEO SOK IS DISABLE");
        }
    }

    public String getKey() {
        return key;
    }

    public String getRoom_key() {
        return room_key;
    }

    public String getUserid() {
        return userid;
    }
}
