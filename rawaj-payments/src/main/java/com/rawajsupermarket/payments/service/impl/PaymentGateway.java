package com.rawajsupermarket.payments.service.impl;

import com.rawajsupermarket.payments.dto.request.PaymentRequest;
import com.rawajsupermarket.payments.dto.response.PaymentResponse;
import com.rawajsupermarket.payments.entity.enums.PaymentMethod;

import java.math.BigDecimal;

public interface PaymentGateway {

    PaymentResponse processPayment(PaymentRequest request);

    PaymentResponse refundPayment(String paymentReference, BigDecimal amount, String reason);

    PaymentResponse cancelPayment(String paymentReference);

    PaymentResponse verifyPayment(String paymentReference);

    PaymentMethod getPaymentMethod();

    boolean isSupported(PaymentMethod method);
}