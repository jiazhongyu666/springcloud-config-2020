package com.jzy.service.impl;

import com.jzy.entity.CommonResult;
import com.jzy.entity.Payment;
import com.jzy.service.PaymentFeignService;
import org.springframework.stereotype.Component;

/**
 * @author jzy
 * @date 2020/6/22 8:56
 */
@Component
public class PaymentFeignServiceImpl implements PaymentFeignService {
    @Override
    public CommonResult<Payment> getPaymentById(Long id) {
        return new CommonResult<>(500, "处理失败", null);
    }

    @Override
    public String paymentFeignTimeout() {
        return "------PaymentFeignServiceImpl,fallback";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "------paymentInfo_TimeOut,fallback";
    }
}
