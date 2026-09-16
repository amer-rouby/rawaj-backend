package com.rawajtechshop.einvoice.service.impl;

import com.rawajtechshop.einvoice.service.EInvoiceService;
import com.rawajtechshop.einvoice.entity.EInvoiceSubmission;
import com.rawajtechshop.einvoice.repository.EInvoiceSubmissionRepository;
import com.rawajtechshop.einvoice.dto.response.EInvoiceSubmissionResponse;
import com.rawajtechshop.einvoice.service.EtaIntegrationService;
import com.rawajtechshop.einvoice.service.EtaSubmissionResult;
import com.rawajtechshop.common.exception.FeatureDisabledException;
import com.rawajtechshop.settings.service.RawajFeatureSettingsService;
import com.rawajtechshop.sales.entity.SaleTransaction;
import com.rawajtechshop.sales.repository.SaleTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EInvoiceServiceImpl implements EInvoiceService {

    private final EInvoiceSubmissionRepository eInvoiceSubmissionRepository;
    private final SaleTransactionRepository saleTransactionRepository;
    private final EtaIntegrationService etaIntegrationService;
    private final RawajFeatureSettingsService rawajFeatureSettingsService;

    @Override
    @Transactional
    public EInvoiceSubmissionResponse submit(Long saleId, Long storeId) {
        checkEnabled(storeId);
        SaleTransaction sale = findSale(saleId, storeId);

        EInvoiceSubmission submission = eInvoiceSubmissionRepository.findBySaleTransactionId(saleId)
                .orElseGet(() -> EInvoiceSubmission.builder().saleTransaction(sale).build());

        attemptSubmission(submission);
        return EInvoiceSubmissionResponse.fromEntity(eInvoiceSubmissionRepository.save(submission));
    }

    @Override
    @Transactional
    public EInvoiceSubmissionResponse retry(Long saleId, Long storeId) {
        checkEnabled(storeId);
        findSale(saleId, storeId);

        EInvoiceSubmission submission = eInvoiceSubmissionRepository.findBySaleTransactionId(saleId)
                .orElseThrow(() -> new RuntimeException("No e-invoice submission exists yet for this sale"));

        submission.setRetryCount(submission.getRetryCount() + 1);
        attemptSubmission(submission);
        return EInvoiceSubmissionResponse.fromEntity(eInvoiceSubmissionRepository.save(submission));
    }

    @Override
    @Transactional(readOnly = true)
    public EInvoiceSubmissionResponse getForSale(Long saleId, Long storeId) {
        checkEnabled(storeId);
        findSale(saleId, storeId);
        return eInvoiceSubmissionRepository.findBySaleTransactionId(saleId)
                .map(EInvoiceSubmissionResponse::fromEntity)
                .orElse(null);
    }

    private SaleTransaction findSale(Long saleId, Long storeId) {
        return saleTransactionRepository.findByIdAndStoreId(saleId, storeId)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
    }

    private void attemptSubmission(EInvoiceSubmission submission) {
        EtaSubmissionResult result = etaIntegrationService.submit(submission.getSaleTransaction());
        submission.setSubmittedAt(LocalDateTime.now());
        if (result.success()) {
            submission.setStatus(EInvoiceSubmission.Status.SUBMITTED);
            submission.setEtaUuid(result.etaUuid());
            submission.setErrorMessage(null);
        } else {
            submission.setStatus(EInvoiceSubmission.Status.ERROR);
            submission.setErrorMessage(result.errorMessage());
        }
    }

    private void checkEnabled(Long storeId) {
        Boolean enabled = rawajFeatureSettingsService.getOrCreate(storeId).getEInvoiceEnabled();
        if (enabled != null && !enabled) {
            throw new FeatureDisabledException("FEATURE_DISABLED_EINVOICE", "E-invoice (ETA) feature is disabled for this store");
        }
    }
}
