package com.rawajtechshop.payments.service.impl;

import com.rawajtechshop.payments.entity.enums.PaymentMethod;
import com.rawajtechshop.payments.dto.request.PaymentRequest;
import com.rawajtechshop.payments.dto.response.PaymentResponse;

import java.math.BigDecimal;

public interface PaymentGateway {

    PaymentResponse processPayment(PaymentRequest request);

    PaymentResponse refundPayment(String paymentReference, BigDecimal amount, String reason);

    PaymentResponse cancelPayment(String paymentReference);

    PaymentResponse verifyPayment(String paymentReference);

    PaymentMethod getPaymentMethod();

    boolean isSupported(PaymentMethod method);
}
