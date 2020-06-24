package com.jzy.contoller;

import com.jzy.entity.CommonResult;
import com.jzy.entity.Payment;
import com.jzy.lb.LoadBalance;
import com.jzy.service.PaymentFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author jzy
 * @date 2020/6/18 15:34
 */
//默认defaultFallback  返回值类型要一致
@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderController {

    private static final String PAYMENT_URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalance loadBalance;

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private PaymentFeignService paymentFeignService;

    @PostMapping("/order/payment/add")
    public CommonResult add(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL + "/payment/add", payment, CommonResult.class);
    }

//    @GetMapping("/order/payment/get/{id}")
//    public CommonResult getPayment(@PathVariable("id") Long id){
//        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/"+id,  CommonResult.class);
//    }

    @GetMapping("/order/payment/lb")
    public String getPaymentLb(){
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance serviceInstance = loadBalance.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }

    @GetMapping("/order/payment/get/{id}")
    @HystrixCommand
    public String getPayment(@PathVariable("id") Long id){
//        int i = 10 / 0;
//        return paymentFeignService.getPaymentById(id);
        return "dddd";
    }

//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "10000")
//    })
    @HystrixCommand
        @GetMapping("/order/payment/feign/timeout")
    public String paymentFeignTimeout() {
        //openfeign - ribbon 客户端默认等待1秒钟
        return paymentFeignService.paymentFeignTimeout();
    }

    @GetMapping("/order/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "order_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
    })
//    @HystrixCommand
    public String order_TimeOut(@PathVariable("id") Integer id) {
        return paymentFeignService.paymentInfo_TimeOut(id);
    }

    public String order_TimeOutHandler(Integer id) {
        return "线程池: 80 对方繁忙! "+Thread.currentThread().getName()+" order_TimeOutHandler, id: "+id+"\t"+"/(ㄒoㄒ)/~~";

    }

    //下面时全局fallback
    public String payment_Global_FallbackMethod(){
        return "异常处理信息,请稍后重试";
    }

}
