package com.mdy.ccvideo.autoconfig;

import com.mdy.ccvideo.entity.UserContext;
import com.mdy.ccvideo.util.UserContextHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "ccvideo", name = "switch", havingValue = "true")
public class UserContextAutoConfiguration {
    private static final Logger log = LogManager.getLogger(UserContextAutoConfiguration.class);

    @Bean
    public UserContext userContext() {
        return UserContextHelper.getInstance();
    }
}