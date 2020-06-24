package com.jzy.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author jzy
 * @date 2020/6/18 15:37
 */
@Configuration
public class RestTemplateConfig {
    //RestTemplate+ribbon实现负载均衡
    @Bean
//    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
