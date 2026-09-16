package com.rawajsupermarket.service;

import com.rawajsupermarket.dto.request.CustomerPaymentRequest;
import com.rawajsupermarket.dto.request.CustomerRequest;
import com.rawajsupermarket.dto.response.CustomerResponse;
import com.rawajsupermarket.dto.response.CustomerStatementResponse;
import com.rawajsupermarket.entity.Customer;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAllCustomers(Long storeId);
    Page<CustomerResponse> getCustomersPaginated(Long storeId, int page, int size);
    CustomerResponse getCustomer(Long id, Long storeId);
    CustomerResponse createCustomer(CustomerRequest request, Long storeId);
    CustomerResponse updateCustomer(Long id, CustomerRequest request, Long storeId);
    void deleteCustomer(Long id, Long storeId);
    List<CustomerResponse> searchCustomers(Long storeId, String query);

    CustomerStatementResponse getStatement(Long id, Long storeId);
    CustomerResponse recordPayment(Long id, CustomerPaymentRequest request, Long storeId, Long userId);

    // Used by SaleTransactionServiceImpl for credit sales - not exposed directly
    // via the controller. Validates the credit limit, writes the ledger row,
    // and updates the cached balance; throws if the sale would exceed the limit.
    Customer recordCreditSale(Long customerId, Long storeId, BigDecimal amount, Long saleId, Long userId);
}
