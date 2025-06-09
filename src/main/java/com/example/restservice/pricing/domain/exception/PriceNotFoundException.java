package com.example.restservice.pricing.domain.exception;

import com.example.restservice.pricing.application.query.FindPriceQuery;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(FindPriceQuery query) {
        super(String.format(
                "No price found for brandId=%d, productId=%d, date=%s",
                query.brandId(),
                query.productId(),
                query.date()
        ));
    }
}
