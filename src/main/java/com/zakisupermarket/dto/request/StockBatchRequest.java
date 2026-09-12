package com.zakisupermarket.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StockBatchRequest {

    @NotNull
    private Long productId;

    @NotNull
    private String batchNumber;

    @NotNull
    private Integer quantityInitial;

    private Integer quantityCurrent;

    // Optional in this variant - StockBatchServiceImpl fills a far-future
    // default when omitted, matching how ProductServiceImpl already handles
    // the initial-stock-at-creation-time case.
    private LocalDate expiryDate;

    private LocalDate productionDate;

    private BigDecimal buyPrice;
    private BigDecimal sellPrice;

    private String location;
    private String shelf;
    private String warehouse;

    private String notes;
    private String status;
}