package com.mdy.ccvideo.autoconfig;

import com.mdy.ccvideo.dict.THQSConstants;
import com.mdy.ccvideo.entity.UserContext;
import com.mdy.ccvideo.util.EnvPropertyUtil;
import com.mdy.ccvideo.util.UserContextHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ConditionalOnProperty(prefix = "ccvideo", name = "switch", havingValue = "true")
public class UserContextAutoConfiguration {
    private static final Logger log = LogManager.getLogger(UserContextAutoConfiguration.class);

    @Autowired
    Environment environment;

    @Bean
    public UserContext userContext() {
        log.info("init CCVIDEO UserContext by spring boot");
        EnvPropertyUtil.set(THQSConstants.CC_VIDEO_SWITCH, environment.getProperty(THQSConstants.CC_VIDEO_SWITCH));
        EnvPropertyUtil.set(THQSConstants.KEY_PROP, environment.getProperty(THQSConstants.KEY_PROP));
        EnvPropertyUtil.set(THQSConstants.ROOM_KEY_PROP, environment.getProperty(THQSConstants.ROOM_KEY_PROP));
        EnvPropertyUtil.set(THQSConstants.USERID_PROP, environment.getProperty(THQSConstants.USERID_PROP));
        return UserContextHelper.getInstance();
    }
}