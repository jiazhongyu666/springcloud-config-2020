package com.jzy.lb.impl;

import com.jzy.lb.LoadBalance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jzy
 * @date 2020/6/19 18:56
 */
@Component
public class LoadBalanceImpl implements LoadBalance {

    // 内存可见性 原子性
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    //自增操作 获取下标
    private int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("*******第几次访问,next:" + next);
        return next;
    }
    //获取服务
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
