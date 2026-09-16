package com.rawajtechshop.einvoice.service;

import com.rawajtechshop.einvoice.controller.EInvoiceController;
import com.rawajtechshop.einvoice.service.impl.NoopEtaProvider;
import com.rawajtechshop.sales.entity.SaleTransaction;

// Pluggable ETA (Egyptian Tax Authority) submission backend. NoopEtaProvider
// is the only implementation today - no real ETA credentials/API call is
// wired up. A future implementation with the real ETA SDK/API can be
// swapped in (see docs/eta-integration.md) without touching
// EInvoiceService/EInvoiceController.
public interface EtaIntegrationService {
    EtaSubmissionResult submit(SaleTransaction sale);
}
