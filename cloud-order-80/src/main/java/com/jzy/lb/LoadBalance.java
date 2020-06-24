package com.jzy.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author jzy
 * @date 2020/6/19 18:53
 */
public interface LoadBalance {

    //手动实现 负载均衡轮询
    ServiceInstance instances(List<ServiceInstance> serviceInstances);

}
