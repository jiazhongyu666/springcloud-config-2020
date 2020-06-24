package com.jzy.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jzy
 * @date 2020/6/19 20:58
 */
@Configuration
public class FeignConfig {
    //记录日志
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
