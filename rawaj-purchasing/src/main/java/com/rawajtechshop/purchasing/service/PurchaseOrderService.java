package com.rawajtechshop.purchasing.service;

import com.rawajtechshop.purchasing.dto.request.PurchaseOrderRequest;
import com.rawajtechshop.purchasing.dto.response.PurchaseOrderResponse;
import com.rawajtechshop.purchasing.dto.response.SendEmailResponse;
import com.rawajtechshop.purchasing.dto.response.SendWhatsAppResponse;
import com.rawajtechshop.purchasing.dto.response.WhatsAppMessageResponse;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;

public interface PurchaseOrderService {
    Page<PurchaseOrderResponse> getAllOrders(Long storeId, int page, int size);
    Page<PurchaseOrderResponse> getOrdersByStatus(Long storeId, String status, int page, int size);
    PurchaseOrderResponse getOrder(Long id, Long storeId);
    PurchaseOrderResponse createOrder(PurchaseOrderRequest request, Long storeId, Long userId);
    PurchaseOrderResponse updateOrder(Long id, PurchaseOrderRequest request, Long storeId, Long userId);
    void deleteOrder(Long id, Long storeId, Long userId);
    PurchaseOrderResponse approveOrder(Long id, Long storeId, Long userId);
    PurchaseOrderResponse cancelOrder(Long id, Long storeId, Long userId);
    PurchaseOrderResponse receiveOrder(Long id, Long storeId, Long userId);
    Long countOrders(Long storeId);
    Long countOrdersByStatus(Long storeId, String status);
    List<PurchaseOrderResponse> getOrdersByDateRange(Long storeId, LocalDate startDate, LocalDate endDate);
    java.math.BigDecimal getTotalPurchasesAmount(Long storeId, LocalDate startDate, LocalDate endDate);
    WhatsAppMessageResponse generateWhatsAppMessage(Long orderId, Long storeId);
    SendWhatsAppResponse sendWhatsAppMessage(Long orderId, Long storeId);
    SendEmailResponse sendPurchaseOrderEmail(Long orderId, Long storeId);
}
