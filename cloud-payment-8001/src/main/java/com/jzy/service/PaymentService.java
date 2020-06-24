package com.jzy.service;

import com.jzy.entity.Payment;

/**
 * @author jzy
 * @date 2020/6/18 13:39
 */
public interface PaymentService {

    int add(Payment payment);

    Payment getPaymentById(Long id);

    String paymentInfo_OK(Integer id);

    String paymentInfo_TimeOut(Integer id);

    String paymentCircuitBreaker(Integer id);
}
