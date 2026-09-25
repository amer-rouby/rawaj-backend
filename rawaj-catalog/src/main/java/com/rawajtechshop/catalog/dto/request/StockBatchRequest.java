package com.rawajtechshop.catalog.dto.request;
import com.rawajtechshop.catalog.service.impl.ProductServiceImpl;
import com.rawajtechshop.catalog.service.impl.StockBatchServiceImpl;

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

    private LocalDate productionDate;

    private BigDecimal buyPrice;
    private BigDecimal sellPrice;

    private String location;
    private String shelf;
    private String warehouse;

    private String notes;
    private String status;
}
