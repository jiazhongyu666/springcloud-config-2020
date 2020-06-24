package com.jzy;

import com.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jzy
 * @date 2020/6/18 15:31
 */

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
//@RibbonClient(name = "cloud-payment-service", configuration = MySelfRule.class)
public class CloudOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudOrderApplication.class, args);
    }
}
