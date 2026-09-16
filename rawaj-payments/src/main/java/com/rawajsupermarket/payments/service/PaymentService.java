package com.rawajsupermarket.payments.service;

import com.rawajsupermarket.payments.service.impl.PaymentGateway;
import com.rawajsupermarket.payments.dto.request.PaymentRequest;
import com.rawajsupermarket.payments.dto.response.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest request);

    PaymentResponse refundPayment(String reference, Long storeId, BigDecimal amount, String reason);

    PaymentResponse cancelPayment(String reference, Long storeId);

    PaymentResponse getPaymentByReference(String reference, Long storeId);

    Page<PaymentResponse> getPaymentsByStore(Long storeId, String status, String paymentMethod, String search, Pageable pageable);

    Map<String, Object> getPaymentStats(Long storeId);

    void registerGateway(PaymentGateway gateway);

    Map<String, String> getRegisteredGateways();
}
