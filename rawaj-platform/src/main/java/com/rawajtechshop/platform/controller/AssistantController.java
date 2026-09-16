package com.rawajtechshop.platform.controller;

import com.rawajtechshop.common.dto.ApiResponse;
import com.rawajtechshop.platform.dto.response.AssistantAnswer;
import com.rawajtechshop.platform.dto.request.AssistantQuestionRequest;
import com.rawajtechshop.platform.service.AssistantService;
import com.rawajtechshop.common.exception.FeatureDisabledException;
import com.rawajtechshop.common.util.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assistant")
@RequiredArgsConstructor
@Slf4j
public class AssistantController {

    private final AssistantService assistantService;

    @PostMapping("/ask")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<AssistantAnswer>> ask(@Valid @RequestBody AssistantQuestionRequest request) {
        Long storeId = SecurityUtils.getCurrentStoreId();
        try {
            AssistantAnswer answer = assistantService.ask(request.getQuery(), storeId);
            return ResponseEntity.ok(ApiResponse.success(answer));
        } catch (FeatureDisabledException e) {
            return ResponseEntity.status(403).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Error answering assistant question", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Failed to answer question: " + e.getMessage()));
        }
    }
}
