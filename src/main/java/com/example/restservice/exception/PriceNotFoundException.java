package com.example.restservice.exception;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(Integer brandId, Integer productId, LocalDateTime date) {
        super("No price found for productId=%d, brandId=%d, date=%s".formatted(productId, brandId, date));
    }
}
