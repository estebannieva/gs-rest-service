package com.example.restservice.pricing.application.query;

import java.time.LocalDateTime;
import java.util.Objects;

public record FindPriceQuery(
        Integer brandId,
        Integer productId,
        LocalDateTime date
) {
    public FindPriceQuery {
        Objects.requireNonNull(brandId, "Brand ID must not be null");
        Objects.requireNonNull(productId, "Product ID must not be null");
        Objects.requireNonNull(date, "Date must not be null");
    }
}
