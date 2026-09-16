package com.rawajsupermarket.service.Payment;

import com.rawajsupermarket.dto.request.PaymentRequest;
import com.rawajsupermarket.dto.response.PaymentResponse;
import com.rawajsupermarket.entity.Payment;
import com.rawajsupermarket.entity.enums.PaymentMethod;
import com.rawajsupermarket.repository.PaymentRepository;
import com.rawajsupermarket.common.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class CashPaymentGateway extends BasePaymentGateway {

    public CashPaymentGateway(PaymentRepository paymentRepository, StoreRepository storeRepository) {
        super(paymentRepository, storeRepository);
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.CASH;
    }

    @Override
    public boolean isSupported(PaymentMethod method) {
        return false;
    }

    @Override
    protected PaymentResponse callGatewayAPI(PaymentRequest request, String referenceNumber) {
        log.info("Processing CASH payment: {}", referenceNumber);

        // Cash payment is immediate
        return PaymentResponse.builder()
                .status("COMPLETED")
                .message("Cash payment completed successfully")
                .referenceNumber(referenceNumber)
                .paymentMethod(PaymentMethod.CASH.name())
                .amount(request.getAmount())
                .transactionId("CASH-" + System.currentTimeMillis())
                .build();
    }

    @Override
    protected PaymentResponse processGatewayRefund(Payment payment, BigDecimal amount, String reason) {
        return null;
    }
}