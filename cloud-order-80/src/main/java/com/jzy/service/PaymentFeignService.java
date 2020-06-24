package com.jzy.service;

import com.jzy.entity.CommonResult;
import com.jzy.entity.Payment;
import com.jzy.service.impl.PaymentFeignServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author jzy
 * @date 2020/6/19 19:58
 */
@FeignClient(value = "cloud-payment-service",fallback = PaymentFeignServiceImpl.class)
//@HystrixCommand
public interface PaymentFeignService {

    @GetMapping("/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping("/payment/feign/timeout")
    String paymentFeignTimeout();

    @GetMapping("/payment/hystrix/timeout/{id}")
    String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}
