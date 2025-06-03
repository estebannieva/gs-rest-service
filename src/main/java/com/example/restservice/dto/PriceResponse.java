package com.example.restservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PriceResponse(
        UUID id,
        Integer productId,
        Integer brandId,
        Integer priceList,
        Integer priority,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal amount,
        String currency
) {}
