package com.mdy.ccvideo.util;

import com.mdy.ccvideo.entity.UserContext;

public class UserContextHelper {

    private static volatile UserContext INSTANCE = null;

    public static String getKey() {
        return getInstance().getKey();
    }

    public static String getRoomKey() {
        return getInstance().getRoom_key();
    }

    public static String getUserId() {
        return getInstance().getUserid();
    }

    private static UserContext getInstance() {
        if (null == INSTANCE) {
            synchronized (UserContext.class) {
                INSTANCE = new UserContext();
            }
        }
        return INSTANCE;
    }

}
