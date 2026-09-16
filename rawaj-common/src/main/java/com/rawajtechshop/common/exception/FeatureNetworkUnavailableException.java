package com.rawajtechshop.common.exception;

import org.springframework.http.HttpStatus;

// Thrown when a Rawaj feature IS enabled for the store but the outbound network
// call it depends on (SMTP, ETA, WhatsApp, a payment gateway, ...) could not be
// reached - distinct from FeatureDisabledException, which means the store never
// wanted this feature at all. 503 rather than FeatureDisabledException's 403:
// the request was allowed, the dependency just wasn't reachable. The frontend
// recognizes the FEATURE_NETWORK_UNAVAILABLE_* error-code family and shows a
// fast, non-blocking warning instead of the generic connection-error message.
public class FeatureNetworkUnavailableException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public FeatureNetworkUnavailableException(String errorCode, String message, Throwable cause) {
        super(HttpStatus.SERVICE_UNAVAILABLE, errorCode, message, cause);
    }
}
