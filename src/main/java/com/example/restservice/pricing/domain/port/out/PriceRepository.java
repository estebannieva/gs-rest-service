package com.example.restservice.pricing.domain.port.out;

import com.example.restservice.pricing.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {

    Optional<Price> findPrice(
            Integer brandId,
            Integer productId,
            LocalDateTime date
    );
}
