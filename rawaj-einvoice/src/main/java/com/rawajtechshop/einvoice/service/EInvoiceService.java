package com.rawajtechshop.einvoice.service;

import com.rawajtechshop.einvoice.dto.response.EInvoiceSubmissionResponse;

public interface EInvoiceService {
    EInvoiceSubmissionResponse submit(Long saleId, Long storeId);

    EInvoiceSubmissionResponse retry(Long saleId, Long storeId);

    EInvoiceSubmissionResponse getForSale(Long saleId, Long storeId);
}
