package com.jzy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author jzy
 * @date 2020/6/24 11:17
 */
@SpringBootApplication
@EnableConfigServer
public class CloudConfigCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudConfigCenterApplication.class, args);
    }
}
