package com.jzy.controller;

import com.jzy.entity.CommonResult;
import com.jzy.entity.Payment;
import com.jzy.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author jzy
 * @date 2020/6/18 13:41
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private PaymentService paymentService;

    @PostMapping("/payment/add")
    public CommonResult<Integer> add(@RequestBody Payment payment){
        int result = paymentService.add(payment);
        log.info("插入结果" + result);
        if (result > 0) {
            return new CommonResult<>(200, "插入数据库成功"+serverPort, result);
        } else {
            return new CommonResult<>(444, "插入数据库失败"+serverPort, null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment paymentById = paymentService.getPaymentById(id);
        log.info("查询结果" + paymentById);
        if (paymentById != null) {
            return new CommonResult<>(200, "查询成功"+serverPort, paymentById);
        } else {
            return new CommonResult<>(444, "查询失败"+serverPort, null);
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLb(){
        return serverPort;
    }

    //测试 修改调用方等待的时间
    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
//            int i = 10 / 0;
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    //测试熔断器
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_OK(id);
        log.info("****result"+result);
        return result;
    }

    //测试熔断器
    @GetMapping("/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "fall")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("****result"+result);
        return result;
    }
    public String fall(){
        return "falldown";
    }
    //服务熔断
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("****result:"+result);
        return result;
    }

}
