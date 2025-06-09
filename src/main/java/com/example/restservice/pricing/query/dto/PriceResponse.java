package com.example.restservice.pricing.query.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public record PriceResponse(
        UUID id,
        Integer brandId,
        Integer productId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer priceList,
        Integer priority,
        BigDecimal amount,
        String currency
) {
    public PriceResponse {
        Objects.requireNonNull(id, "Price ID must not be null");
        Objects.requireNonNull(brandId, "Brand ID must not be null");
        Objects.requireNonNull(productId, "Product ID must not be null");
        Objects.requireNonNull(startDate, "Start date must not be null");
        Objects.requireNonNull(endDate, "End date must not be null");
        Objects.requireNonNull(priceList, "Price list must not be null");
        Objects.requireNonNull(priority, "Priority must not be null");
        Objects.requireNonNull(amount, "Amount must not be null");
        Objects.requireNonNull(currency, "Currency must not be null");
    }
}

